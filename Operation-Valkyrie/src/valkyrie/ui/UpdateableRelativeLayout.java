package valkyrie.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class UpdateableRelativeLayout extends RelativeLayout implements IUpdateableUI {
	private static final String TAG = "UpdateableRelativeLayout";

	public UpdateableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutManager.getInstance().registerUpdateableComponent(this);
	}

	/**
	 * Redraws this UI Component.
	 * 
	 * @param uiElements
	 *            RelativeLayout, containing all UI-Elements belonging to the Filter.
	 */
	public void redrawUI(TableLayout uiElements) {

		// remove all child elements from this layout
		this.removeAllViews();

		this.buildUI(uiElements);
		
		// add possibly new elements to panel
		this.addView(uiElements);

	}
	
	private void buildUI(TableLayout uiElements) {
		int childCount = uiElements.getChildCount();
		for(int i = 0; i < childCount; i++) {
			View child = uiElements.getChildAt(i);
			Log.d("FasuDebug", "Child: " + i + " id: " + child.getId());
		}
	}

}
