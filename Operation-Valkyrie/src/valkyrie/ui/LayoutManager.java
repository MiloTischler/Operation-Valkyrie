package valkyrie.ui;

import android.view.View;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class LayoutManager {
	/**
	 * @todo Jakob
	 */
	private static LayoutManager instance = null;

	/**
	 * Default-Konstruktor, der nicht au�erhalb dieser Klasse aufgerufen werden
	 * kann.
	 */
	private LayoutManager() {
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zur�ck
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
