package valkyrie.ui;

import valkyrie.file.DecodeBitmaps;
import valkyrie.main.R;
import valkyrie.widget.TouchImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */


/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class ShowPicActivity extends Activity {
	private static final String TAG = "ShowPicActivity";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Drehdich+++ 1?");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		
		// Start
		
		Intent intent = getIntent();
		TouchImageView imageView = (TouchImageView) this.findViewById(R.id.full_image_view);
		int position = intent.getExtras().getInt("id");

		Log.d(TAG, "Drehdich+++ 5?");
		BitmapFactory.Options  scaleBitmapOpt = new BitmapFactory.Options();
		scaleBitmapOpt.inSampleSize = 2;

		imageView.setImageBitmap(BitmapFactory.decodeFile(DecodeBitmaps.fullImgPosition.get(position),scaleBitmapOpt));
		
	

	}
}
