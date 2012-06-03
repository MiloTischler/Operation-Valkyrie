package valkyrie.ui;

import java.util.logging.Logger;


import valkyrie.filter.ascii.Ascii;
import valkyrie.filter.nofilter.NoFilter;
import valkyrie.main.R;
import valkyrie.widget.MultiDirectionSlidingDrawer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Ritzys filter test
		Ascii asciiFilter = new Ascii();

		// Disable window title bar, for full screen camera preview
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Set activity layout
		this.setContentView(R.layout.main);

		// initialize LayoutManager
		LayoutManager.getInstance().setMainActivity(this);
		
		// schweigis Filter Test
		NoFilter nofilt0r = new NoFilter();
		LayoutManager.getInstance().notifyUI(nofilt0r);
		// schweigis Test end

		CameraPreviewDispatcher dispatcher = (CameraPreviewDispatcher) this
				.findViewById(R.id.camera_preview_dispatcher);
		CameraPreviewView view = (CameraPreviewView) this.findViewById(R.id.camera_preview_view);

		dispatcher.setPreview(view);
	}

	public void takePicture(View view) {
		Log.d(TAG, "clicked: takePicture - S1");

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
}