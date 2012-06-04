package valkyrie.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;

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
		int subChildCount = 0;
		int subSubChildCount = 0;

		Log.d("FasuDebug", "Childs found: " + childCount);

		for (int i = 0; i < childCount; i++) {
			TableRow child = (TableRow) uiElements.getChildAt(i);

			if (child == null)
				Log.d("FasuDebug", "Child: is null...");

			Log.d("FasuDebug", "Child: " + i + " Tag: " + child.getTag().toString());

			if (child.getTag().toString().equals("parent")) {

				subChildCount = child.getChildCount();

				for (int j = 0; j < subChildCount; j++) {
					Log.d("FasuDebug", "SubChild: " + j + " ID: " + child.getChildAt(j).getId());
					Log.d("FasuDebug", "SubChild: " + j + " Tag: " + child.getChildAt(j).getTag().toString());

					subSubChildCount = child.getChildCount();
					for (int k = 0; k < subSubChildCount; k++) {
						initializeSupportedComponent(child.getChildAt(k));
					}
				}
			}
		}
	}

	/**
	 * Checks the type of given Element and whether it is supported or not.
	 * If it is supportet, initialize it.
	 * 
	 * @param uiElement
	 *            View the component from xml File, eg. a SeekBar
	 */
	private boolean initializeSupportedComponent(View uiElement) {

		if (uiElement instanceof SeekBar) {
			Log.d("FasuDebug", "UI-Element init: SeekBar");
			
			OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
				
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					Log.d("FasuDebug","SeekBar Value: " + progress);					
				}
			};
			
			((SeekBar) uiElement).setOnSeekBarChangeListener(seekBarListener);
		}/*
		 * else if (uiElement instanceof Button)
		 * Log.d("FasuDebug", "UI-Element init: SeekBar");
		 */

		return false;
	}

}
