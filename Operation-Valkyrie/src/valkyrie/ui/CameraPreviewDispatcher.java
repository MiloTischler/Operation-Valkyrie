package valkyrie.ui;

import java.io.IOException;
import java.util.List;


import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewDispatcher extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "CameraPreviewDispatcher";
	
	SurfaceHolder surfaceHolder = null;

	private Camera camera = null;

	private Parameters parameters;

	private Size previewSize;
	
	private CameraPreviewView cameraPreviewView;

	public CameraPreviewDispatcher(Context context) {
		super(context);

		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public CameraPreviewDispatcher(Context context, AttributeSet attr) {
		super(context, attr);

		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		
		this.camera = Camera.open();
		try {
			
			SurfaceView view = new SurfaceView(this.getContext());
			
			this.camera.setPreviewDisplay(view.getHolder());

			this.camera.setPreviewCallback(this.cameraPreviewView);
			
			this.parameters = this.camera.getParameters();

		} catch (IOException exception) {
			this.camera.release();
			this.camera = null;
			// TODO: add more exception handling logic here
		}
	}

	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		Log.d(TAG,"Stopping preview in SurfaceDestroyed().");
		
		this.camera.setPreviewCallback(null);
		this.camera.stopPreview();
		this.camera.release();
		this.camera = null;
	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
		
		if(this.camera == null)
			return;
			
		List<Camera.Size> supportedPreviewSizes = this.parameters.getSupportedPreviewSizes();

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
		
		this.previewSize = this.parameters.getPreviewSize();

		// set the camera's settings
		this.camera.setParameters(parameters);
		
		this.cameraPreviewView.setPreviewSize(this.previewSize);
		
		this.camera.startPreview();
	}
	
	public void setPreview(CameraPreviewView cameraPreviewView) {
		this.cameraPreviewView = cameraPreviewView;
	}
}
