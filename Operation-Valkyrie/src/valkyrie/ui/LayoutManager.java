package valkyrie.ui;

import java.util.ArrayList;

import valkyrie.filter.IFilter;
import valkyrie.main.R;
import android.app.Activity;
import android.graphics.Path.Direction;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
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
	public void notifyUI(IFilter filterObject) {

		// get all childs
		View mainView = mainActivity.findViewById(R.layout.main);
		RelativeLayout optionsPanel = (RelativeLayout) mainView.findViewById(R.id.optionsContent);
		
		ArrayList<View> newUIElements = new ArrayList<View>();
		Button btn = new Button(mainActivity);
		btn.setText("Lol");
		newUIElements.add(btn);
		optionsPanel.addView(btn);
		
		// refresh
		mainView.invalidate();
	}

	/**
	 * Registers the main layout.
	 * 
	 * @param mainView
	 *            the main layout view object
	 */
	public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
	}

}
