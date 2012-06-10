package valkyrie.ui.gallery;

import valkyrie.file.DecodeBitmaps;
import valkyrie.main.R;
import valkyrie.widget.TouchImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
/**
 * ShowPicActivity is started when we click on a Item on the Gridview in the
 * GalleryActivity Creates then a imageview casted from TouchImageView which
 * handles the displaying in the new Activity
 * 
 */
public class ShowPicActivity extends Activity {

	private static final String TAG = "ShowPicActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		Intent intent = getIntent();
		TouchImageView imageView = (TouchImageView) this
				.findViewById(R.id.full_image_view);
		int position = intent.getExtras().getInt("id");

		BitmapFactory.Options scaleBitmapOpt = new BitmapFactory.Options();
		scaleBitmapOpt.inSampleSize = 2;

		try {
			Log.d(TAG, "trying displaying image without compressing");
			imageView.setImageBitmap(BitmapFactory
					.decodeFile(DecodeBitmaps.fullImgPosition.get(position)));

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			scaleBitmapOpt.inSampleSize = 4;
			imageView
					.setImageBitmap(BitmapFactory.decodeFile(
							DecodeBitmaps.fullImgPosition.get(position),
							scaleBitmapOpt));
			Toast.makeText(
					this.getApplicationContext(),
					"Something went wrong while loading the Pic, please try again",
					Toast.LENGTH_LONG).show();
		}
	}
}
