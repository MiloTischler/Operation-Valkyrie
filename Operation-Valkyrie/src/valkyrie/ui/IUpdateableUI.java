package valkyrie.ui;

import java.util.HashMap;
import java.util.Vector;

import android.widget.RelativeLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public interface IUpdateableUI {
	
	/**
	 * @todo Jakob
	 */
	public void redrawUI(HashMap<Integer, Vector<RelativeLayout>> uiElements);
	
}
