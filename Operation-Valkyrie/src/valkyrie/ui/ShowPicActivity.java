package valkyrie.ui;

import valkyrie.main.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.gesture.*;

public class ShowPicActivity extends Activity implements OnTouchListener {

	private String TAG = "ShowPicActivity";
	ImageAdapter imageAdapter = new ImageAdapter(this);
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	private int i = 0;
	private float x = 0;
	private float y = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);
		Intent intent = getIntent();

		int position = intent.getExtras().getInt("id");

		ImageView imageview = (ImageView) findViewById(R.id.full_image_view);
		imageview.setOnTouchListener(this);
	//	imageview.setScaleType(ScaleType.CENTER_CROP);
		imageview.setImageBitmap(imageAdapter.bitFullVec.elementAt(position));
	//	imageview.setScaleType(ScaleType.MATRIX);

	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		ImageView view = (ImageView) v;
		dumpEvent(event);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
		//	view.setImageBitmap(imageAdapter.bitFullVec.get(i));
			x = event.getX();
			y = event.getY();
			
			mode = DRAG;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			Log.d(TAG, "mode=NONE");
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - x, event.getY() - y);
			}else if (mode == ZOOM) {
				matrix.set(savedMatrix);
				matrix.postScale((float)1.2,(float) 1.2, 600, 370);
				
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			
			
			break;
		}

		view.setImageMatrix(matrix);

		return true;
	}

	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
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
