package valkyrie.ui.preview;

import java.io.IOException;
import java.util.List;
import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.filter.IFilter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraDispatcher extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "CameraPreviewDispatcher";

	private SurfaceHolder surfaceHolder = null;
	private CameraPreviewView cameraPreviewView;

	public Camera camera = null;
	private Parameters parameters;
	private Size previewSize;

	private IFilter filter = null;

	private boolean cameraLock = false;

	private SavePictureTask savePictureTask;

	PictureProcessingCallback pictureProcessingCallback = null;

	public CameraDispatcher(Context context) {
		super(context);

		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public CameraDispatcher(Context context, AttributeSet attr) {
		super(context, attr);

		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder surfaceHolder) {

		if (this.cameraPreviewView == null) {
			Log.e(TAG, "Unable to start camera preview, camera preview view is null");
		}

		this.camera = Camera.open();
		try {

			this.camera.setPreviewDisplay(this.surfaceHolder);

			this.camera.setPreviewCallback(this.cameraPreviewView);

			this.parameters = this.camera.getParameters();

		} catch (IOException exception) {
			this.camera.release();
			this.camera = null;
			// TODO: add more exception handling logic here
		}
	}

	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		Log.d(TAG, "Stopping preview in SurfaceDestroyed().");

		this.camera.setPreviewCallback(null);
		this.camera.stopPreview();
		this.camera.release();
		this.camera = null;

	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {

		if (this.camera == null)
			return;

		List<Camera.Size> supportedPreviewSizes = this.parameters.getSupportedPreviewSizes();

		Parameters params = this.camera.getParameters();
		List<Camera.Size> sizes = params.getSupportedPictureSizes();

		int bestFrameWidth = w;
		int bestFrameHeight = h;

		double minDiff = Double.MAX_VALUE;

		for (Size supportedPreviewSize : supportedPreviewSizes) {
			if (Math.abs(supportedPreviewSize.height - h) < minDiff) {
				bestFrameWidth = (int) supportedPreviewSize.width;
				bestFrameHeight = (int) supportedPreviewSize.height;
				minDiff = Math.abs(supportedPreviewSize.height - h);
			}
		}

		this.parameters.setPreviewSize(bestFrameWidth, bestFrameHeight);

		this.parameters.setPreviewFormat(ImageFormat.NV21);

		this.parameters.setPictureFormat(ImageFormat.RGB_565);
		this.parameters.setJpegQuality(50);

		this.previewSize = this.parameters.getPreviewSize();

		// for some tests - Laurenz
		// setting the resolution of the picture taken
		this.parameters.setPictureSize(sizes.get(0).width, sizes.get(0).height);

		// set the camera's settings
		this.camera.setParameters(parameters);

		this.cameraPreviewView.setPreviewSize(this.previewSize);

		this.camera.startPreview();
	}

	public void setPreview(CameraPreviewView cameraPreviewView) {
		this.cameraPreviewView = cameraPreviewView;
	}

	public void displayPreview(boolean display) {
		if (display) {
			this.cameraPreviewView.setVisibility(View.VISIBLE);
		} else {
			this.cameraPreviewView.setVisibility(View.GONE);
		}
	}

	public boolean isPreviewDisplayed() {
		if (this.cameraPreviewView.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}

	public void setFilter(IFilter filter) {
		this.filter = filter;

		if (this.cameraPreviewView != null && this.filter != null) {

			this.cameraPreviewView.setFilter(this.filter);
		} else {
			Log.e(TAG, "Unable to set filter to camera preview, camera preview viw is null");
		}
	}

	public void takePicture(PictureProcessingCallback pictureProcessingCallback) {
		if (this.camera != null) {
			this.cameraLock = true;

			this.pictureProcessingCallback = pictureProcessingCallback;

			this.camera.takePicture(shutterCallback, null, pictureCallback);
		} else {
			Log.e(TAG, "Unable to take picture, camera is null");
		}
	}

	private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {

		public void onShutter() {
			Log.i(TAG, "Shutter Callback");
		}

	};

	private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "Picture Callback");
			// if(savePictureTask == null || savePictureTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
			// savePictureTask = new SavePictureTask();
			// savePictureTask.execute(data);
			// } else {
			// Toast.makeText(getContext(), "Camera already processing..", Toast.LENGTH_SHORT);
			// }

			Bitmap picture = null;
			
			BitmapFactory.Options opt = new BitmapFactory.Options();
		    opt.inPreferredConfig = Bitmap.Config.ARGB_8888;

			picture = BitmapFactory.decodeByteArray(data, 0, data.length, opt);

			if (filter != null) {				
				picture = filter.manipulateImage(picture);
			}

			pictureProcessingCallback.onManipulationProcessed(picture);
			
			if(picture != null) {
				picture.recycle();
			}

			camera.startPreview();
			cameraLock = false;
		}
	};

	public interface PictureProcessingCallback {
		public void onOriginalProcessed(Bitmap original);

		public void onManipulationProcessed(Bitmap manipulated);
	}

	private class SavePictureTask extends AsyncTask<byte[], Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(byte[]... data) {

			Bitmap picture = null;

			try {
				picture = BitmapFactory.decodeByteArray(data[0], 0, data[0].length);
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			} finally {
				if (filter != null) {
					picture = filter.manipulateImage(picture);
				}
			}

			return picture;
		}

		@Override
		protected void onPostExecute(Bitmap picture) {
			if (pictureProcessingCallback != null) {
				pictureProcessingCallback.onManipulationProcessed(picture);
			}

			if (picture != null) {
				picture.recycle();
				picture = null;
			}
		}
	}
}
