package valkyrie.ui.gallery;

import valkyrie.file.DecodeBitmaps;
import valkyrie.main.R;
import valkyrie.widget.TouchImageView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.ImageView;

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

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		
		// Start
		
		Intent intent = getIntent();
		
		TouchImageView imageView = (TouchImageView) this.findViewById(R.id.full_image_view);
		
		int position = intent.getExtras().getInt("id");
		
		imageView.setImageBitmap(BitmapFactory.decodeFile(DecodeBitmaps.fullImgPosition.get(position)));
	}
}
