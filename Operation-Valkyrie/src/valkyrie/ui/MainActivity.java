package valkyrie.ui;

import valkyrie.filter.nofilter.NoFilter;
import valkyrie.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity"; 
	
	private ImageView handle = null;
	private SurfaceView camera = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// register UI components, all at once


		NoFilter filter = new NoFilter();
		
		// Ritzys filter test
		//Ascii asciiFilter = new Ascii();
		
		
		LayoutManager.getInstance().notifyUI(filter);
		

		this.handle = (ImageView) this.findViewById(R.id.filterOptionsHandle);
		this.camera = (SurfaceView) this.findViewById(R.id.cameraPreview);
		
		this.handle.setOnLongClickListener (new View.OnLongClickListener()
		{
		    public boolean onLongClick (View v)
		    {
		    	camera.setVisibility(View.GONE);
		    	
		    	Log.d(TAG, "OMFG OMFG OMFG");
		    			
		        return false;
		    }
		});
		
		this.handle.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				camera.setVisibility(View.GONE);
				
				Log.d(TAG, "LOL OMFG OMFG OMFG");
			}
		});
		

	}
	

}