package valkyrie.ui;

import valkyrie.main.R;

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
import android.widget.Toast;

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

		// Disable window title bar, for full screen camera preview
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Set activity layout
		this.setContentView(R.layout.main);

		// Start capturing the camera for the preview
		CameraPreviewView cameraPreviewView = (CameraPreviewView) this.findViewById(R.id.camera_preview_view);

		CameraPreviewDispatcher cameraPreviewDispatcher = (CameraPreviewDispatcher) this
				.findViewById(R.id.camera_preview_dispatcher);

		cameraPreviewDispatcher.setCameraPreviewView(cameraPreviewView);
	}

	public void takePhoto(View view) {
		Log.d("Tag", "clicked: takePhoto");
		
		// Just a dummy text to appear..
		Toast.makeText(this.getApplicationContext(), "Take Photo Clicked", Toast.LENGTH_SHORT).show();

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
}