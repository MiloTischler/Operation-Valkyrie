package valkyrie.ui;

import java.io.IOException;
import java.util.List;

import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.filter.IFilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class CameraDispatcher extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "CameraPreviewDispatcher";

	private SurfaceHolder surfaceHolder = null;
	private CameraPreviewView cameraPreviewView;

	public Camera camera = null;
	private Parameters parameters;
	private Size previewSize;

	private IFilter filter = null;
	private Bitmap picture = null;
	
	private boolean cameraLock = false;

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
		
		this.parameters.setPictureSize(sizes.get(1).width, sizes.get(1).height);

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
		if(display) {
			this.cameraPreviewView.setVisibility(View.VISIBLE);
		} else {
			this.cameraPreviewView.setVisibility(View.GONE);
		}
	}
	
	public boolean isPreviewDisplayed() {
		if(this.cameraPreviewView.getVisibility() == View.VISIBLE) {
			return true;
		}
		
		return false;
	}
	
	public void setFilter(IFilter filter) {
		this.filter = filter;
		
		if(this.cameraPreviewView != null && this.filter != null) {

			this.cameraPreviewView.setFilter(this.filter);
		} else {
			Log.e(TAG, "Unable to set filter to camera preview, camera preview viw is null");
		}
	}

	public Bitmap takePicture() {
		if(this.camera != null) {
			this.cameraLock = true;
			
			this.camera.takePicture(shutterCallback, null, pictureCallback);
			
			return this.picture;
		} else {
			Log.e(TAG, "Unable to take picture, camera is null");
			return null;
		}
	}

	private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {

		public void onShutter() {
			Log.i(TAG, "Shutter Callback");
		}

	};
	
	// Just a test for further improvements Begin
	class SavePhotoTask extends AsyncTask<byte[], String, String> {

		@Override
		protected String doInBackground(byte[]... data) {
			
			if(data[0] == null) {
				Log.d("WTF", "WTF ... null");
			} else {
				Log.d("WTF", "WTF ... not null");
			}
			
			return null;
		}
	  }
	// Just a test for further improvements End

	private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "Picture Callback");
			
			new SavePhotoTask().execute(data);

			
			//camera.stopPreview(); -> not necessary .. ?
			
			if(picture != null) {
				picture.recycle();
			}
			
			// @TODO: .. we should start a image processing thread here ..
			try {
				picture = BitmapFactory.decodeByteArray(data, 0, data.length);
				
				if(filter != null) {
					picture = filter.manipulateImage(picture);
				}
				
				// Some filemanager stuff i don't understand x)
				// saving the captured image to the SD
				FileManager filemanager = new FileManager();
				filemanager.saveImageToGallery(picture);
				DecodeBitmaps.done = false;
				
			} catch(Exception e) {
				Log.d("OMG", "OMFG .. very bad ..");
				Log.e(TAG, e.toString());
			}
			
			cameraLock = false;
			
			camera.startPreview();
			
			data = null;
		}
	};	
}
