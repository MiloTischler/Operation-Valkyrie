package valkyrie.ui;

import java.io.File;

import valkyrie.colorpicker.ColorPickerApplication;
import valkyrie.colorpicker.ColorPickerDialog;
import valkyrie.filter.FilterManager;
import valkyrie.filter.grayscale.Grayscale;
import valkyrie.main.R;
import valkyrie.ui.gallery.GalleryActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;
import valkyrie.widget.MultiDirectionSlidingDrawer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private FilterManager filterManager = null;
	// private CameraDispatcher cameraDispatcher = null;

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

		// initialize CameraPreviewView with OpenCV
		this.cameraPreview = (CameraPreviewViewCV) this.findViewById(R.id.camera_preview_view);

		// initialize FilterManager
		this.filterManager = new FilterManager(this.getApplicationContext(), R.array.filters, this.cameraPreview);
		this.filterManager.setActiveFilter(new Grayscale());
	}

	public void takePicture(View view) {
		Log.d(TAG, "clicked: takePicture");

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Take Picture Clicked", Toast.LENGTH_SHORT).show();

		// Play take photo sound effect
		AudioManager meng = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		int volume = meng.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

		if (volume != 0) {
			MediaPlayer shootSpound = MediaPlayer.create(this.getApplicationContext(),
					Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));

			if (shootSpound != null) {
				shootSpound.start();
			} else {
				view.playSoundEffect(SoundEffectConstants.CLICK);
			}
		}
		
		Bitmap bitmap = this.cameraPreview.takePicture();
		
		if(bitmap == null) {
			Log.e(TAG, "takePicture got null bitmap");
		}
		
		// TODO: do something with picture..
		
		bitmap.recycle();
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

		// TODO: Reset or delete or reorganize Shared Prefs (options)
	}
	
	public void colorPicker(View view) {
		Log.d("Tag", "clicked: showGallery");

		// Just a dummy text to appear.. 
		 
		Toast.makeText(this.getApplicationContext(), "You Launch the Color Picker now", Toast.LENGTH_SHORT).show();
		view.playSoundEffect(SoundEffectConstants.CLICK);
		
		Intent myIntent = new Intent(MainActivity.this, ColorPickerExampleActivity.class);
 
		try {
			MainActivity.this.startActivity(myIntent);
		} catch (Exception e) {
			Log.e(TAG, "failed to start the colorPicker Activity");
			// TODO: handle exception
		}
		// TODO: Implementation of colorPicker ... in progress
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

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (multiDirectionSlidingDrawer.isOpened()) {
				multiDirectionSlidingDrawer.animateClose();
			} else {
				multiDirectionSlidingDrawer.animateOpen();
			}
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