package valkyrie.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler 
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

/**
 * Implementation of the UpdateableUI Interface.
 * Every Component that should be updated when a Filter is switched is of this type.
 */
public class UpdateableRelativeLayout extends RelativeLayout implements IUpdateableUI {

	/**
	 * When a component of this type is created it adds itself to the LayoutManagers list of updateable components.
	 * 
	 * @param context
	 *            android standard
	 * @param attrs
	 *            android standard
	 */
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

		if (uiElements == null)
			return;

		// remove all child elements from this layout
		this.removeView(uiElements);

		if (uiElements.getParent() == null) {
			// add possibly new elements to panel
			this.addView(uiElements);
		}
	}
}
