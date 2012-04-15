//package valkyrie.ui;
//
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.WindowManager;
//import android.view.Window;
//
//public abstract class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Runnable {
//
//	private static final String TAG = "We are now in the CameraPreview";
//
//	private SurfaceHolder surfaceholder;
//	private VideoCapture camera;
//
//	public CameraPreview(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//
//		surfaceholder = getHolder();
//		surfaceholder.addCallback(this);
//		// IMPORTANTE Log.v <- change log mode log.v only for testing and
//		// development
//		Log.v(TAG, "Instantiated new " + this.getClass() + "-> CameraPreview Constructor");
//	
//	}
//
//	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//		Log.v(TAG, "surfaceChanged");
//		// TODO Auto-generated method stub
//	     camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH,1280 );
//         camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 720 );
//
//	}
//
//	public void surfaceCreated(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		Log.v(TAG, "surfaceCreated");
//		camera = new VideoCapture();
//		if (camera.isOpened()) {
//			 (new Thread(this)).start();
//		} else {
//			camera.release();
//			camera = null;
//			Log.e(TAG, "Failed to open native camera");
//		}
//	}
//
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		Log.v(TAG, "surfaceDestroyed");
//		if (camera != null) {
//			camera.release();
//			camera = null;
//		}
//	}
//	
//
//}
