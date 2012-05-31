package valkyrie.ui;

import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import valkyrie.filter.FilterCamera;
import valkyrie.main.R;
import valkyrie.widget.MultiDirectionSlidingDrawer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private FilterCamera filterCamera = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Disable window title bar, for full screen camera preview
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Set activity layout
		this.setContentView(R.layout.main);

		// Initialise filter camera and start preview
		this.filterCamera = new FilterCamera(this.getApplicationContext(), R.array.filters);
		this.filterCamera.startPreview((CameraPreviewView) this.findViewById(R.id.camera_preview_view));
	}

	public void takePicture(View view) {
		Log.d("Tag", "clicked: takePicture");

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

		// TODO: Implementation of takePhoto
		// byte[] picture = this.filterCamera.takePicture(); -> atm not possible because camera for preview in use..
	}

	public void showGallery(View view) {
		Log.d("Tag", "clicked: showGallery");

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Show Gallery Clicked", Toast.LENGTH_SHORT).show();

		view.playSoundEffect(SoundEffectConstants.CLICK);

		// TODO: Implementation of showGallery
	}

	public void toggleFilterEffect(View view) {
		Log.d("Tag", "clicked: toggleFilterEffect");

		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Toggle Filter Clicked", Toast.LENGTH_SHORT).show();

		view.playSoundEffect(SoundEffectConstants.CLICK);

		// TODO: Implementation of toggleFilterEffect
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
	protected void onDestroy() {
		this.filterCamera.release();

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		this.filterCamera.release();

		super.onPause();
	}

	@Override
	protected void onResume() {
		this.filterCamera = new FilterCamera(this.getApplicationContext(), R.array.filters);
		this.filterCamera.startPreview((CameraPreviewView) this.findViewById(R.id.camera_preview_view));

		super.onResume();
	}
}