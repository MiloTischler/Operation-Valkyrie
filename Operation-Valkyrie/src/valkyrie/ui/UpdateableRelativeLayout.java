package valkyrie.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class UpdateableRelativeLayout extends RelativeLayout implements IUpdateableUI {


	public UpdateableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("FasuDebug","I'm created");
		LayoutManager.getInstance().registerUpdateableComponent(this);
	}

	public void redrawUI() {
		// TODO Auto-generated method stub
		
	}

}
