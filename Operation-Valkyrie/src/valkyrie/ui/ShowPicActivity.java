package valkyrie.ui;

import valkyrie.file.DecodeBitmaps;
import valkyrie.main.R;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

public class ShowPicActivity extends Activity implements OnTouchListener {

	private String TAG = "ShowPicActivity";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	Matrix fullMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	private float xDown = 0;
	private float yDown = 0;
	private PointF mid = new PointF();
	private float oldDist;
	private int OPTIMUM_WIDTH = 0;
	private int OPTIMUM_HEIGHT = 1;
    private Float res[] = new Float[2];
	private Bitmap bitmapG;
    


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("setResolution", "Drehdich+++ 1?");
		super.onCreate(savedInstanceState);
		Log.d("setResolution", "Drehdich+++ 2?");
		setContentView(R.layout.showpic);
		Intent intent = getIntent();

		int position = intent.getExtras().getInt("id");

		ImageView imageview = (ImageView) findViewById(R.id.full_image_view);
		imageview.setOnTouchListener(this);
		imageview.setScaleType(ScaleType.CENTER);
		BitmapFactory.Options fullOpt = new BitmapFactory.Options();
		fullOpt.inSampleSize = 2;
		Log.d("setResolution", "Drehdich+++ 2.2?");
		setResolutions(BitmapFactory.decodeFile(
				DecodeBitmaps.fullImgPosition.get(position)),imageview);
		 bitmapG = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(
						DecodeBitmaps.fullImgPosition.get(position),fullOpt),
				this.res[OPTIMUM_WIDTH].intValue(), this.res[OPTIMUM_HEIGHT].intValue(), false);
		
		Log.d("setResolution", "Drehdich+++ 3?");

		Log.d("setResolution", "Drehdich+++ 4?");
		
	
//		imageview.setImageBitmap(Bitmap.createScaledBitmap(
//				BitmapFactory.decodeFile(
//						DecodeBitmaps.fullImgPosition.get(position),fullOpt),
//				this.res[OPTIMUM_WIDTH].intValue(), this.res[OPTIMUM_HEIGHT].intValue(), false));
		imageview.setImageBitmap(bitmapG);
		Log.d("setResolution", "Drehdich+++ 5?");
		Log.d(TAG, DecodeBitmaps.fullImgPosition.get(position));

		// imageview.setImageBitmap(DecodeBitmaps.fullImg.get(position));
		// imageview.setScaleType(ScaleType.MATRIX);
		

	}
    
    public Float[] getResolutions(){
    	return this.res;
    }
    
    public void setResolutions(Bitmap bitmap, ImageView imageView){
    	Log.d("setResolution", "u omfg ?");
    	Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	Float disWidth = (float) d.getWidth();
    	Float disHeight = (float)  d.getHeight();
    	Float bitWidth =  (float) bitmap.getWidth();
    	Float bitHeight =  (float) bitmap.getHeight();
    	Log.d("setResolution", "u omfg ?");
    	Float optimumScalWidth = 0f;
    	Float optimumScalHeight = 0f;
    	Float width, height;
    	width = 0f;
    	height = 0f;
    	
    	if ((disWidth >= disHeight)){
    		
    		Log.d("setResolution", "==1==");
    	
    	
    		optimumScalWidth = disWidth / bitWidth;
    		optimumScalHeight = disHeight / bitHeight;
    		this.res[OPTIMUM_WIDTH] =  (bitWidth * optimumScalHeight);
    		this.res[OPTIMUM_HEIGHT] =  (bitHeight * optimumScalHeight);
    	} else if (disWidth <= disHeight){
    		optimumScalWidth = disWidth / bitWidth;
    		optimumScalHeight = disHeight / bitHeight;
    		this.res[OPTIMUM_WIDTH] =  (bitWidth * optimumScalWidth);
    		this.res[OPTIMUM_HEIGHT] =  (bitHeight * optimumScalWidth);
    	} else {	
    		imageView.setScaleType(ScaleType.CENTER_INSIDE);
    		}

    	Log.d("setResolution", "Display Width : " + disWidth);
    	Log.d("setResolution", "Display Height: " + disHeight);
    	Log.d("setResolution", "Bitmap Width  : " + bitWidth);
    	Log.d("setResolution", "Bitmap Height : " + bitHeight);
    	Log.d("setResolution", "OPTIMUM_WIDTH : " + res[OPTIMUM_WIDTH]);
    	Log.d("setResolution", "OPTIMUM_HEIGHT: " + res[OPTIMUM_HEIGHT]);
    	Log.d("setResolution", "OPTIMUM_SCALEH: " + optimumScalHeight);
    	Log.d("setResolution", "OPTIMUM_SCALEW: " + optimumScalWidth);
    	Log.d("setResolution", "width		  : " + width);
    	Log.d("setResolution", "height		  : " + height);
    	bitmap.recycle();
    }

    @Override
    public void onBackPressed(){
    	Log.d("setResolution", "Drehdich+++ onBack?");
    	bitmapG.recycle();
    	super.onBackPressed();
    }
    
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		ImageView view = (ImageView) v;
		dumpEvent(event);
		view.setScaleType(ScaleType.MATRIX);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "ACTIONDOWN");
			savedMatrix.set(matrix);
			// view.setImageBitmap(imageAdapter.bitFullVec.get(i));
			xDown = event.getX();
			yDown = event.getY();

			mode = DRAG;
			break;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "ACTIONUP");
			break;
		case MotionEvent.ACTION_POINTER_UP:
			Log.d(TAG, "ACTIONPOINTERUP");
			mode = NONE;
			Log.d(TAG, "mode=NONE");
			// xActionUp = event.getX(1);
			// yActionUp = event.getY(1);
			break;
		case MotionEvent.ACTION_MOVE:

			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - xDown, event.getY() - yDown);
			} else if (mode == ZOOM) {

				float newDist = spacing(event);
				Log.d(TAG, "newDist=" + newDist);
				if (newDist > 10f) {
					Log.d(TAG, "!!!!1");
					matrix.set(savedMatrix);
					Log.d(TAG, "!!!!2");
					float scale = newDist / oldDist;
					Log.d(TAG, "!!!!3");
					matrix.postScale(scale, scale, mid.x, mid.y);
					Log.d(TAG, "!!!!4");
				}
			}
			break;
		// if (Math.abs(xActionUp - xActionDown) < 150) {
		// matrix.set(savedMatrix);
		// matrix.postScale((float) 0.8, (float) 0.8, 600, 370);
		// } else if (Math.abs(xActionUp - xActionDown) > 150) {
		// matrix.set(savedMatrix);
		// matrix.postScale((float) 1.2, (float) 1.2, 600, 370);
		// }
		// }

		case MotionEvent.ACTION_POINTER_DOWN:
			// Log.d(TAG, "ACTIONPOINTERDOWN");
			// xActionDown = event.getX(1);
			// yActionDown = event.getY(1);
			// mode = ZOOM;
			oldDist = spacing(event);
			Log.d(TAG, "oldDist=" + oldDist);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				Log.d(TAG, "krachboom");
				midPoint(mid, event);
				mode = ZOOM;
				Log.d(TAG, "mode=ZOOM");
			}
			break;

		}

		view.setImageMatrix(matrix);

		return true;
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d(TAG, sb.toString());
	}
}
