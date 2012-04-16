package valkyrie.ui;


import java.io.IOException;

import valkyrie.filter.FilterCamera;
import valkyrie.filter.ascii.Ascii;
import valkyrie.filter.nofilter.NoFilter;
import valkyrie.main.R;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// register UI components, all at once

<<<<<<< HEAD
//		LayoutManager.getInstance().setMainActivity(this);
//		CameraPreview cameraPre = new CameraPreview(this) {
//			
//			public void run() {
//				// TODO Auto-generated method stub
//				// whatever
//			}
//		};


=======
>>>>>>> 19d7d698041e2726e71dea89fe9acbae6c69dec9
		LayoutManager.getInstance().setMainActivity(this);
		NoFilter filter = new NoFilter();
		
		
		// Ritzys filter test
		//Ascii asciiFilter = new Ascii();
		
		
		LayoutManager.getInstance().notifyUI(filter);

	}

}