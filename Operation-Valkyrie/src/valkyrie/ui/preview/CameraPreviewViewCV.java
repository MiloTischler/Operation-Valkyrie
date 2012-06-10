package valkyrie.ui.preview;

import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import valkyrie.filter.IFilter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Camera preview SurfaceView, uses the OpenCV camera object and is inflated by android in the main layout xml
 */
public class CameraPreviewViewCV extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final String TAG = "CameraPreviewViewCV";

	private SurfaceHolder holder;
	private VideoCapture camera;

	private IFilter filter;
	private boolean isFilterDisplayed = false;
	private boolean isLocked = false;

	/**
	 * Sets SurfaceHolder.Callback to this
	 * 
	 * @param Context context
	 */
	public CameraPreviewViewCV(Context context) {
		super(context);
		this.holder = getHolder();
		this.holder.addCallback(this);

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/**
	 * Sets SurfaceHolder.Callback to this
	 * 
	 * @param Context context
	 * @param AttributeSet attr
	 */
	public CameraPreviewViewCV(Context context, AttributeSet attr) {
		super(context, attr);
		this.holder = getHolder();
		this.holder.addCallback(this);

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/**
	 * Sets the filter for the preview. If no filter is set, the original
	 * image is displayed
	 * 
	 * @param IFilter filter
	 */
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}

	/**
	 * Checks if the filtered preview is displayed
	 * 
	 * @return the state of the filtered preview
	 */
	public boolean isFilterDisplayed() {
		if (this.isFilterDisplayed == true) {
			return true;
		}

		return false;
	}

	/**
	 * Toggles the filter usage of the surface.
	 * 
	 * @param display
	 */
	public void toggleFilter(boolean display) {
		if (this.isFilterDisplayed) {
			this.isFilterDisplayed = false;
		} else {
			this.isFilterDisplayed = true;
		}
	}

	/**
	 * Grabs a frame from the preview und manipulates it with the current filter if set. Sets the camera lock
	 * to true, for another picture resume() has to be called.
	 * 
	 * @return Bitmap picture
	 */
	public Bitmap takePicture() {
		if(this.camera == null) {
			return null;
		}
		
		Bitmap bitmap = null;
		Mat pictureMat = new Mat();
		
		synchronized (this) {
			if (this.filter != null) {
				this.camera.retrieve(pictureMat, this.filter.getFilterCaptureFormat());
				bitmap = this.filter.manipulatePreviewImage(pictureMat);
			} else {
				this.camera.retrieve(pictureMat, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
				bitmap = Bitmap.createBitmap(pictureMat.cols(), pictureMat.rows(), Bitmap.Config.ARGB_8888);
				Utils.matToBitmap(pictureMat, bitmap);
			}
	
			Log.d(TAG, "Called take picture, width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
			
			this.isLocked = true;
		}

		return bitmap;
	}
	
	/**
	 * Releases the camera lock after taking a picture
	 */
	public void resume() {
		this.isLocked = false;
	}
	
	/**
	 * Locks the camera (if the lock is set, no pictures can be taken)
	 */
	public void lock() {
		this.isLocked = true;
	}
	
	/**
	 * Returns the state of the camera lock
	 * 
	 * @return boolean, the state
	 */
	public boolean isLocked() {
		return this.isLocked;
	}

	/**
	 * Calculates the optimal preview size for the divice
	 * 
	 * @param SurfaceHolder _holder
	 * @param int format
	 * @param int width
	 * @param int height
	 */
	public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
		Log.i(TAG, "surfaceCreated");
		synchronized (this) {
			if (this.camera != null && this.camera.isOpened()) {
				List<Size> sizes = this.camera.getSupportedPreviewSizes();

				Size optimalPreviewSize = this.getOptimalPreviewSize(sizes, width, height);

				this.camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, optimalPreviewSize.width);
				this.camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, optimalPreviewSize.height);
			}
		}
	}

	/**
	 * Initializes and opens the OpenCV VideoCaputre camera for preview and pictures,
	 * if the camera is already open, it will be released
	 * 
	 * @param SurfaceHolder holder
	 */
	public void surfaceCreated(SurfaceHolder holder) {
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

	/**
	 * Releases the camera
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "surfaceDestroyed");
		if (this.camera != null) {
			synchronized (this) {
				this.camera.release();
				this.camera = null;
			}
		}
	}

	/**
	 * Process a frame from the preview with the actual filter
	 * 
	 * @param capture
	 * @return
	 */
	protected Bitmap processFrame(VideoCapture capture) {
		Mat bitmapMat = new Mat();

		Bitmap bitmap = null;

		if (this.filter != null && this.isFilterDisplayed) {
			capture.retrieve(bitmapMat, this.filter.getFilterCaptureFormat());
			bitmap = this.filter.manipulatePreviewImage(bitmapMat);
		} else {
			capture.retrieve(bitmapMat, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
			bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
			Utils.matToBitmap(bitmapMat, bitmap);
		}

		return bitmap;
	}

	/**
	 * Thread that listens to the OpenCV VideoCaputre, grabs the preview frames and draws them on the
	 * CameraPreviewViewCV SurfaceView  
	 */
	public void run() {
		Log.i(TAG, "Starting processing thread");
		while (true) {
			Bitmap bitmap = null;

			synchronized (this) {
				if (this.camera == null) {
					Log.e(TAG, "camera == null");
					break;
				}

				if (!this.camera.grab()) {
					Log.e(TAG, "camera.grab() failed");
					break;
				}

				bitmap = processFrame(this.camera);
			}

			if (bitmap != null) {
				Canvas canvas = this.holder.lockCanvas();
				if (canvas != null) {
					canvas.drawBitmap(bitmap, (canvas.getWidth() - bitmap.getWidth()) / 2,
							(canvas.getHeight() - bitmap.getHeight()) / 2, null);
					this.holder.unlockCanvasAndPost(canvas);
				}

				bitmap.recycle();
			}
		}

		Log.i(TAG, "Finishing processing thread");
	}

	/**
	 * Calculates the optimal preview size for a display with the supported camera sizes
	 * 
	 * @param List<Size> sizes, the supported camera sizes
	 * @param int surfaceWidth
	 * @param int surfaceHeight
	 * @return Size, the optimal preview size for the device display
	 */
	private Size getOptimalPreviewSize(List<Size> sizes, int surfaceWidth, int surfaceHeight) {
		int frameWidth = surfaceWidth;
		int frameHeight = surfaceHeight;

		double minDiff = Double.MAX_VALUE;
		for (Size size : sizes) {
			if (Math.abs(size.height - surfaceHeight) < minDiff) {
				frameWidth = (int) size.width;
				frameHeight = (int) size.height;
				minDiff = Math.abs(size.height - surfaceHeight);
			}
		}

		Log.i(TAG, "Set optimal preview size, width: " + frameWidth + " height: " + frameHeight);

		return new Size(frameWidth, frameHeight);
	}

	@Deprecated
	private Size getHighestPreviewSize(List<Size> sizes) {
		Size maxSize = new Size(Double.MIN_VALUE, Double.MIN_VALUE);

		for (Size size : sizes) {
			if (maxSize.width < size.width) {
				maxSize = size;
			}
		}

		return maxSize;
	}

}
