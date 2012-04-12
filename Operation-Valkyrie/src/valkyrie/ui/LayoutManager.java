package valkyrie.ui;

import valkyrie.main.R;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class LayoutManager {

	/**
	 * Instance Variable. Used for Singleton.
	 */
	private static LayoutManager instance = null;

	/**
	 * Default-Konstruktor, der nicht außerhalb dieser Klasse aufgerufen werden
	 * kann.
	 */
	private LayoutManager() {
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
	public void notifyUI(Object filterObject) {
		
	}
	
	/**
	 * Registers the main layout.
	 * @param mainView the main layout viewobject
	 */
	public void setMainActivity(Activity mainActivity) {
		
		TextView test = (TextView) mainActivity.findViewById(R.id.optionsTest);
	}
}
