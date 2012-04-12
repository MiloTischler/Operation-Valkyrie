package valkyrie.ui;

import android.view.View;

public class LayoutManager {
	/**
	 * @todo Jakob
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
	 */
	public void setContentView(View mainView) {
		
	}
}
