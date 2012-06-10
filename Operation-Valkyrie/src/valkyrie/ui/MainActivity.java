package valkyrie.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.filter.FilterManager;
import valkyrie.filter.ascii.Ascii;
import valkyrie.main.R;
import valkyrie.ui.gallery.GalleryActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;
import valkyrie.widget.MultiDirectionSlidingDrawer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

/**
 * The application main activity, holds the camera preview, the camera trigger and filter options
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private FilterManager filterManager = null;
	private MediaPlayer shootSound = null;
	private AudioManager audioManager = null;
	private int volume = 0;
	private FileManager fileManager = null;

	private CameraPreviewViewCV cameraPreview = null;

	/**
	 * Initializes the layout, the camera preview, the filter manager, sounds and the layout manager.
	 * Sets the activity to fullscreen and warns if the sd card is mounted.
	 * 
	 * @param Bundle savedInstanceState
	 */
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
		this.filterManager.setActiveFilter(new Ascii());

		// initialize FileManager
		this.fileManager = new FileManager(this.getApplicationContext());

		// initialize sound management
		this.shootSound = MediaPlayer.create(this.getApplicationContext(),
				Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));

		this.audioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		this.volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

		// check if SD Card is mounted
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			Toast.makeText(this.getApplicationContext(), "WARNING: SD Card not mounted!", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Takes a picture with the camera and plays the trigger sound / animation. Saves the bitmap to the gallery.
	 * 
	 * @param View view
	 */
	public void takePicture(View view) {
		Log.i(TAG, "clicked: takePicture");

		if (this.cameraPreview.isLocked()) {
			Log.e(TAG, "camera is locked");
			return;
		}

		// Play animation
		ImageButton triggerAnimationSpace = (ImageButton) this.findViewById(R.id.trigger_animation);
		triggerAnimationSpace.setBackgroundResource(R.drawable.trigger_animation);
		AnimationDrawable triggerAnimation = (AnimationDrawable) triggerAnimationSpace.getBackground();
		triggerAnimation.setVisible(false, true);
		triggerAnimation.start();

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Take Picture Clicked", Toast.LENGTH_SHORT).show();

		// Play take picture sound effect
		if (volume != 0) {
			this.shootSound.start();
		} else {
			view.playSoundEffect(SoundEffectConstants.CLICK);
		}

		// Actually take picture
		Bitmap bitmap = this.cameraPreview.takePicture();

		if (bitmap == null) {
			Log.e(TAG, "takePicture returned null bitmap");
			this.cameraPreview.resume();
			return;
		}

		// Save image to gallery
		this.fileManager.saveImageToGallery(bitmap);
		DecodeBitmaps.done = false;

		if (bitmap != null) {
			bitmap.recycle();
		}

		this.cameraPreview.resume();
	}

	/**
	 * Intent the gallery activity for displaying the already shot pictures if the folder is not empty. 
	 * Checks if the picture folder exists and displays the message if the folder is empty. 
	 * 
	 * @param View view
	 */
	public void showGallery(View view) {
		Log.i("TAG", "clicked: showGallery");

		view.playSoundEffect(SoundEffectConstants.CLICK);

		Intent myIntent = new Intent(MainActivity.this, GalleryActivity.class);

		File galleryFiles = new File(Environment.getExternalStorageDirectory() + "/Valkyrie/Gallery/");

		if (galleryFiles.listFiles() != null) {

			if ((galleryFiles.listFiles().length == 0)) {
				Toast.makeText(this.getApplicationContext(), "There are no Pictures to display", Toast.LENGTH_SHORT)
						.show();
				DecodeBitmaps.done = false;
				new DecodeBitmaps(0);
			}
		} else if ((galleryFiles.listFiles() == null)) {
			Toast.makeText(this.getApplicationContext(), "There are no Pictures to display", Toast.LENGTH_SHORT).show();
		}

		if ((galleryFiles.listFiles() != null)) {
			if ((galleryFiles.listFiles().length != 0)) {
				// Just a dummy text to appear..
				DecodeBitmaps.done = false;
				new DecodeBitmaps(0);
				Log.d(TAG, "filelist length :" + galleryFiles.listFiles().length);
				Toast.makeText(this.getApplicationContext(), "Welcome to the Gallery", Toast.LENGTH_SHORT).show();
				view.playSoundEffect(SoundEffectConstants.CLICK);

				try {
					MainActivity.this.startActivity(myIntent);
				} catch (Exception e) {

				}
			}
		}

	}

	/**
	 * Toggles the live preview of the filter effect
	 * 
	 * @param View view
	 */
	public void toggleFilterEffect(View view) {
		Log.i("Tag", "clicked: toggleFilterEffect");

		view.playSoundEffect(SoundEffectConstants.CLICK);

		if (this.cameraPreview.isFilterDisplayed()) {

			ImageButton toggle = (ImageButton) this.findViewById(R.id.filter_effect_toggle);
			toggle.setImageResource(R.drawable.preview_on);

			this.cameraPreview.toggleFilter(false);
		} else {

			ImageButton toggle = (ImageButton) this.findViewById(R.id.filter_effect_toggle);
			toggle.setImageResource(R.drawable.preview_off);

			this.cameraPreview.toggleFilter(true);
		}

		// TODO: Reset or delete or reorganize Shared Prefs (options)
	}

	/**
	 * If open, closes the filter options
	 */
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

	/**
	 * If open, closes the filter options, else opens the filter options
	 * 
	 * @param int keyCode
	 * @param KeyEvent event
	 */
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

	/**
	 * Not implemented
	 */
	@Override
	protected void onPause() {
		Log.i(TAG, "onPause called");

		super.onPause();
	}

	/**
	 * Not implemented
	 */
	@Override
	protected void onResume() {
		Log.i(TAG, "onResume called");

		super.onResume();

	}

}