package valkyrie.ui;

import valkyrie.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity"; 
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Disable window title bar, for full screen camera preview
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Set activity layout
		this.setContentView(R.layout.main);
		
		//Start capturing the camera for the preview
		CameraPreviewView cameraPreviewView = (CameraPreviewView) this.findViewById(R.id.cameraPreviewView);
		
		CameraPreviewDispatcher cameraPreviewDispatcher = (CameraPreviewDispatcher) this.findViewById(R.id.cameraPreviewDispatcher);
		cameraPreviewDispatcher.setCameraPreviewView(cameraPreviewView);
	}
	

}