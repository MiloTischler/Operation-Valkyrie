package valkyrie.ui;

import java.util.ArrayList;

import valkyrie.filter.IFilter;
import valkyrie.main.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TableLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

/**
 * Singleton Class. Used to manage interactions with UI elements.
 */
public class LayoutManager {
	private static final String TAG = "LayoutManager";
	private IFilter activeFilter = null;

	/**
	 * Instance Variable. Used for Singleton.
	 */
	private static LayoutManager instance = null;

	/**
	 * Contains the mainActivity of the Program.
	 */
	private Activity mainActivity = null;

	private ArrayList<IUpdateableUI> registeredComponents = null;

	/**
	 * Default-Konstruktor, der nicht außerhalb dieser Klasse aufgerufen werden
	 * kann.
	 */
	private LayoutManager() {
		this.registeredComponents = new ArrayList<IUpdateableUI>();
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zurück
	 */
	public static LayoutManager getInstance() {

		if (instance == null) {
			instance = new LayoutManager();
		}

		return instance;
	}

	/**
	 * Registers the main layout.
	 * 
	 * @param mainView
	 *            the main Activity object
	 */
	public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
	}

	/**
	 * Force all registered (updateable Components) to update.
	 * 
	 * @param filterObject
	 *            A FilterObject which is passed by the FilterManager
	 */
	public void notifyUI(IFilter filterObject) {
		this.activeFilter = filterObject;
		TableLayout uiElements = filterObject.getUIElements(mainActivity);
		
		for (IUpdateableUI registeredComponent : this.registeredComponents) {
			registeredComponent.redrawUI(uiElements);
		}
	}

	/**
	 * All Updateable Components used in the main XML File, will register this way when they are created.
	 * 
	 * @param component
	 *            An UpdateableRelativeLayout element
	 */
	public void registerUpdateableComponent(UpdateableRelativeLayout component) {
		this.registeredComponents.add(component);
	}

	/**
	 * Removes a registered Updateable Component
	 * 
	 * @param component
	 *            An UpdateableRelativeLayout element
	 */
	public void removeUpdateableComponent(UpdateableRelativeLayout component) {
		this.registeredComponents.remove(component);
	}

	public SharedPreferences getSharedPreferencesOfCurrentFilter() {
		return this.mainActivity.getSharedPreferences(this.activeFilter.getName(), 0);
	}
	
	public SharedPreferences getSharedPreferencesOfFilter(String filterName) {
		return this.mainActivity.getSharedPreferences(filterName, 0);
	}

}
