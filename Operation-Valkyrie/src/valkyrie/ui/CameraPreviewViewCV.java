package valkyrie.ui;

import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.main.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewViewCV extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final String TAG = "CameraPreviewDispatcher";

	private SurfaceHolder surfaceHolder = null;
	private VideoCapture camera = null;
	private IFilter filter = null;

	public CameraPreviewViewCV(Context context) {
		super(context);
		
		this.surfaceHolder = this.getHolder();
		this.surfaceHolder.addCallback(this);

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	public CameraPreviewViewCV(Context context, AttributeSet attr) {
		super(context, attr);

		this.surfaceHolder = this.getHolder();
		this.surfaceHolder.addCallback(this);

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	private Bitmap processFrame(VideoCapture capture) {
		Mat rgba = new Mat();

		capture.retrieve(rgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);

		Bitmap bmp = Bitmap.createBitmap(rgba.cols(), rgba.rows(), Bitmap.Config.ARGB_8888);

		if (Utils.matToBitmap(rgba, bmp))
			return bmp;

		bmp.recycle();

		return null;
	}

	public void run() {
		Log.i(TAG, "Starting processing thread");

		while (true) {

			Bitmap bmp = null;

			synchronized (this) {
				
				if (this.camera == null) {
					Log.e(TAG, "Thread error.. filter camera = null");
					break;
				}

				if (!this.camera.grab()) {
					Log.e(TAG, "mCamera.grab() failed");
					break;
				}

				bmp = this.processFrame(this.camera);
			}

			if (bmp != null) {
				Canvas canvas = this.surfaceHolder.lockCanvas();
				if (canvas != null) {
					canvas.drawBitmap(bmp, (canvas.getWidth() - bmp.getWidth()) / 2,
							(canvas.getHeight() - bmp.getHeight()) / 2, null);
					this.surfaceHolder.unlockCanvasAndPost(canvas);
				}

				bmp.recycle();
			} 
		}
		
		if(this.camera.isOpened()) {
			this.camera.release();
			this.camera = null;
		}
		
		Log.i(TAG, "Ended processing thread");
	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int frameFormat, int frameWidth, int frameHeight) {
		Log.i(TAG, "surfaceChanged");

		synchronized (this) {
			if (this.camera != null && this.camera.isOpened()) {
				Size frameSize = this.getCameraPreviewSize(this.getWidth(), this.getHeight());

				this.camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, frameSize.width);
				this.camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, frameSize.height);
			}
		}
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceCreated");

		this.camera = new VideoCapture(Highgui.CV_CAP_ANDROID); 
        if (this.camera.isOpened()) {
            (new Thread(this)).start();
        } else {
        	this.camera.release();
        	this.camera = null;
            Log.e(TAG, "Failed to open native camera");
        }
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceDestroyed");

		if (this.camera != null) {
			synchronized (this) {
				this.camera.release();
				this.camera = null;
			}
		}
	}
	
	public void disconnect() {
		Log.i(TAG, "disconnected");

		if (this.camera != null) {
			synchronized (this) {
				this.camera.release();
				this.camera = null;
			}
		}
	}
	
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}
	
	public void reconnect() {
		Log.i(TAG, "reconnected");
		
		surfaceDestroyed(this.getHolder());
	}

	private Size getCameraPreviewSize(int frameWidth, int frameHeight) {

		List<Size> supportedPreviewSizes = this.camera.getSupportedPreviewSizes();

		int bestFrameWidth = frameWidth;
		int bestFrameHeight = frameHeight;

		double minDiff = Double.MAX_VALUE;

		for (Size supportedPreviewSize : supportedPreviewSizes) {
			if (Math.abs(supportedPreviewSize.height - frameHeight) < minDiff) {
				bestFrameWidth = (int) supportedPreviewSize.width;
				bestFrameHeight = (int) supportedPreviewSize.height;
				minDiff = Math.abs(supportedPreviewSize.height - frameHeight);
			}
		}

		return new Size(bestFrameWidth, bestFrameHeight);
	}

}
