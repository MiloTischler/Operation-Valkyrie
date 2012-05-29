package valkyrie.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import valkyrie.filter.IFilter;
import android.util.Log;
import android.widget.RelativeLayout;

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

	/**
	 * Instance Variable. Used for Singleton.
	 */
	private static LayoutManager instance = null;

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
	 * Force all registered (updateable Components) to update.
	 * 
	 * @param filterObject
	 *            A FilterObject which is passed by the FilterManager
	 */
	public void notifyUI(IFilter filterObject) {
		HashMap<Integer, Vector<RelativeLayout>> uiElements = filterObject.getUIElements();

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

}
