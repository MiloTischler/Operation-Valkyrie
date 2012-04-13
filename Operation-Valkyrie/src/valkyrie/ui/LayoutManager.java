package valkyrie.ui;

import java.util.ArrayList;

import valkyrie.filter.IFilter;
import valkyrie.main.R;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

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
	 * @todo Jakob
	 */
	public void notifyUI(IFilter filterObject) {

		// get all childs
		RelativeLayout optionsPanel = (RelativeLayout) mainActivity
				.findViewById(R.id.optionsContent);

		ArrayList<View> newUIElements = new ArrayList<View>();

		Button btn = new Button(mainActivity);
		btn.setText("Lol");
		newUIElements.add(btn);
		optionsPanel.addView(btn);

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
	 * @todo Jakob
	 */
	public void registerUpdateableComponent(UpdateableRelativeLayout component) {

		Log.d("FasuDebug", "Registered " + component.getId());
		this.registeredComponents.add(component);

	}

}
