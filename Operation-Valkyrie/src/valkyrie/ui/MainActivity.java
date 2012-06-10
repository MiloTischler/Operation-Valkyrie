package valkyrie.ui;

import java.io.File;

import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.filter.FilterManager;
import valkyrie.filter.ascii.Ascii;
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
import android.os.AsyncTask;
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
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private FilterManager filterManager = null;
	private MediaPlayer shootSound = null;
	private AudioManager audioManager = null;
	private int volume = 0;
	private FileManager fileManager = null;

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
		this.filterManager.setActiveFilter(new Ascii());
	}

	public void takePicture(View view) {
		Log.d(TAG, "clicked: takePicture");

		if (this.cameraPreview.isLocked()) {
			Log.e(TAG, "cam locked");
			return;
		}

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Take Picture Clicked", Toast.LENGTH_SHORT).show();

		// Play take photo sound effect
		if (this.audioManager == null || this.volume == 0) {
			this.audioManager = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
			this.volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
		}

		if (this.shootSound == null) {
			this.shootSound = MediaPlayer.create(this.getApplicationContext(),
					Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
		}

		if (volume != 0) {
			this.shootSound.start();
		} else {
			view.playSoundEffect(SoundEffectConstants.CLICK);
		}

		Bitmap bitmap = this.cameraPreview.takePicture();

		if (bitmap == null) {
			Log.e(TAG, "takePicture got null bitmap");
		}

		// TODO: do something with picture..
		if(this.fileManager == null) {
			this.fileManager = new FileManager();
		}

		this.fileManager.saveImageToGallery(bitmap);


		DecodeBitmaps.done = false;

		if(bitmap != null) {
			bitmap.recycle();
		}

		this.cameraPreview.resume();
	}

	public void showGallery(View view) {
		
		view.playSoundEffect(SoundEffectConstants.CLICK);

		Intent myIntent = new Intent(MainActivity.this, GalleryActivity.class);

		boolean work = false;
		File galleryFiles = new File(Environment.getExternalStorageDirectory() + "/Valkyrie/Gallery/");
		File thumbFiles = new File(Environment.getExternalStorageDirectory() + "/Valkyrie/Thumbnls/");

		Log.d("TAG", "clicked: showGallery");

		if (galleryFiles.listFiles() != null) {

			if ((galleryFiles.listFiles().length == 0)) {
				Toast.makeText(this.getApplicationContext(),
						"There are no Files taken yet, make some to open the Gallery", Toast.LENGTH_SHORT).show();
				DecodeBitmaps.done = false;
				DecodeBitmaps decodeBitmaps = new DecodeBitmaps(0);
			}
		} else if ((galleryFiles.listFiles() == null)) {
			Toast.makeText(this.getApplicationContext(),
					"There are no Pictures taken yet, make some to open the Gallery", Toast.LENGTH_SHORT).show();
		}

		if ((galleryFiles.listFiles() != null)) {
			if ((galleryFiles.listFiles().length != 0)) {
				// Just a dummy text to appear..
				DecodeBitmaps.done = false;
				DecodeBitmaps makeThumbs = new DecodeBitmaps(0);
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

	public void toggleFilterEffect(View view) {
		Log.d("Tag", "clicked: toggleFilterEffect");
		
		view.playSoundEffect(SoundEffectConstants.CLICK);

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Toggle Filter Clicked", Toast.LENGTH_SHORT).show();

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