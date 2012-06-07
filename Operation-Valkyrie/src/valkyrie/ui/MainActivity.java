package valkyrie.ui;

import java.io.File;

import valkyrie.filter.FilterManager;
import valkyrie.filter.grayscale.Grayscale;
import valkyrie.main.R;
import valkyrie.ui.gallery.GalleryActivity;
import valkyrie.ui.preview.CameraDispatcher;
import valkyrie.ui.preview.CameraDispatcher.PictureProcessingCallback;
import valkyrie.ui.preview.CameraPreviewView;
import valkyrie.ui.preview.CameraPreviewViewCV;
import valkyrie.widget.MultiDirectionSlidingDrawer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private FilterManager filterManager = null;
//	private CameraDispatcher cameraDispatcher = null;
	
	private CameraPreviewViewCV cameraPreview = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Disable window title bar, for full screen camera preview
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		final Window window = this.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Set activity layout
		this.setContentView(R.layout.main);

		// initialize LayoutManager
		LayoutManager.getInstance().setMainActivity(this);

		// initialize CameraDispatcher and CameraPreviewView
//		this.cameraDispatcher = (CameraDispatcherCV) this.findViewById(R.id.camera_preview_dispatcher);
//		this.cameraDispatcher.setPreview((CameraPreviewView) this.findViewById(R.id.camera_preview_view));
		this.cameraPreview = (CameraPreviewViewCV) this.findViewById(R.id.camera_preview_view);

		// initialize FilterManager
		this.filterManager = new FilterManager(this.getApplicationContext(), R.array.filters, this.cameraPreview);
		this.filterManager.setActiveFilter(new Grayscale());
	}

	public void takePicture(View view) {
		Log.d(TAG, "clicked: takePicture");

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Take Picture Clicked", Toast.LENGTH_SHORT).show();
	}

	public void showGallery(View view) {
		Log.d("Tag", "clicked: showGallery");

		// Just a dummy text to appear..

		Toast.makeText(this.getApplicationContext(), "You Launch the Gallery now", Toast.LENGTH_SHORT).show();
		view.playSoundEffect(SoundEffectConstants.CLICK);
		Intent myIntent = new Intent(MainActivity.this, GalleryActivity.class);

		try {
			MainActivity.this.startActivity(myIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// TODO: Implementation of showGallery ... in progress
	}

	public void toggleFilterEffect(View view) {
		Log.d("Tag", "clicked: toggleFilterEffect");

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Toggle Filter Clicked", Toast.LENGTH_SHORT).show();

		view.playSoundEffect(SoundEffectConstants.CLICK);

		if (this.cameraPreview.isFilterDisplayed()) {
			this.cameraPreview.toggleFilter(false);
		} else {
			this.cameraPreview.toggleFilter(true);
		}

		// TODO: Implementation of toggleFilterEffect
		// TODO: Reset or delete or reorganize Shared Prefs (options)
	}

	@Override
	public void onBackPressed() {
		MultiDirectionSlidingDrawer multiDirectionSlidingDrawer = (MultiDirectionSlidingDrawer) this
				.findViewById(R.id.filter_options_panel);

		if (multiDirectionSlidingDrawer.isOpened()) {
			multiDirectionSlidingDrawer.animateClose();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		MultiDirectionSlidingDrawer multiDirectionSlidingDrawer = (MultiDirectionSlidingDrawer) this
				.findViewById(R.id.filter_options_panel);

		if (keyCode == KeyEvent.KEYCODE_MENU && !multiDirectionSlidingDrawer.isOpened()) {
			multiDirectionSlidingDrawer.animateOpen();
		}

		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause called");

		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume called");

		super.onResume();
	}
}