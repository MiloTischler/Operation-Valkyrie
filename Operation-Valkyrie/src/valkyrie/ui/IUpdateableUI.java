package valkyrie.ui;

import android.widget.TableLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */

/**
 * Interface for UI Components which shall be updated when a Filter is switched or in anyway set.
 */
public interface IUpdateableUI {

	/**
	 * Forces the UI to update, according to the currently set active Filter.
	 * 
	 * @param uiElements
	 *            TableLayout containing all UI Elements of the active Filter.
	 */
	public void redrawUI(TableLayout uiElements);

}
