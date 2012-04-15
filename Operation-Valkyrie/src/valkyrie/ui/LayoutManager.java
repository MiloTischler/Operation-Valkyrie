package valkyrie.ui;

import java.util.ArrayList;

import valkyrie.filter.IFilter;
import android.app.Activity;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler 
 * © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
/**
 * Singleton Class. Used to manage interactions with UI elements.
 */
public class LayoutManager {

	/**
	 * Instance Variable. Used for Singleton.
	 */
	private static LayoutManager instance = null;

	/**
	 * Contains the mainActivity of the Program.
	 */
	private Activity mainActivity = null;

	private ArrayList<UpdateableRelativeLayout> registeredComponents = null;

	/**
	 * Default-Konstruktor, der nicht außerhalb dieser Klasse aufgerufen werden
	 * kann.
	 */
	private LayoutManager() {
		this.registeredComponents = new ArrayList<UpdateableRelativeLayout>();
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
	 * Force all registered (updateable Components) to update.
	 * 
	 * @param filterObject
	 *            A FilterObject which is passed by the FilterManager
	 */
	public void notifyUI(IFilter filterObject) {

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
	 * All Updateable Components used in the main XML File, will register this way when they are created.
	 * 
	 * @param component
	 *            An UpdateableRelativeLayout element
	 */
	public void registerUpdateableComponent(UpdateableRelativeLayout component) {

		Log.d("FasuDebug", "Registered " + component.getId());
		this.registeredComponents.add(component);

	}

}
