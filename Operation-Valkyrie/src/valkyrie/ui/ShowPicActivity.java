package valkyrie.ui;

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

public class ShowPicActivity extends Activity implements OnTouchListener {

	private String TAG = "ShowPicActivity";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	private float xDown = 0;
	private float yDown = 0;
	private PointF mid = new PointF();
	private float oldDist;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		Intent intent = getIntent();

		int position = intent.getExtras().getInt("id");

		ImageView imageview = (ImageView) findViewById(R.id.full_image_view);
		imageview.setOnTouchListener(this);
		// imageview.setScaleType(ScaleType.CENTER_CROP);
		BitmapFactory.Options fullOpt = new BitmapFactory.Options();
		fullOpt.inSampleSize = 2;
		
		Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();


//		imageview.setImageBitmap(Bitmap.createScaledBitmap(
//				BitmapFactory.decodeFile(
//						DecodeBitmaps.fullImgPosition.get(position),fullOpt),
//				d.getWidth(), d.getHeight(), false));
		imageview.setImageBitmap(BitmapFactory.decodeFile(DecodeBitmaps.fullImgPosition.get(position)));
		Log.d(TAG, DecodeBitmaps.fullImgPosition.get(position));

		// imageview.setImageBitmap(DecodeBitmaps.fullImg.get(position));
		// imageview.setScaleType(ScaleType.MATRIX);

	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		ImageView view = (ImageView) v;
		dumpEvent(event);

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
