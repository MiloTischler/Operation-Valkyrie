package valkyrie.ui;

import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import valkyrie.filter.FilterCamera;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final String TAG = "CameraPreviewView";
	
	private SurfaceHolder surfaceHolder = null;
	
	private FilterCamera filterCamera = null;
	
	private Context context = null;
	
    public CameraPreviewView(Context context, AttributeSet attr) {
        super(context, attr);
        
        this.context = context;
        
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
                if (this.filterCamera == null)
                    break;

                if (!this.filterCamera.grab()) {
                    Log.e(TAG, "mCamera.grab() failed");
                    break;
                }

                bmp = this.processFrame(this.filterCamera);
            }

            if (bmp != null) {
                Canvas canvas = this.surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth() - bmp.getWidth()) / 2, (canvas.getHeight() - bmp.getHeight()) / 2, null);
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
                
                bmp.recycle();
            }
        }

        Log.i(TAG, "Finishing processing thread");
	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int frameFormat, int frameWidth, int frameHeight) {
		Log.i(TAG, "surfaceChanged");
		
		synchronized (this) {
			if(this.filterCamera != null && this.filterCamera.isOpened()) {
				Size frameSize = this.getCameraPreviewSize(frameWidth, frameHeight);
				
				this.filterCamera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, frameSize.width);
				this.filterCamera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, frameSize.height);
			}
		}
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceCreated");
		
		this.filterCamera = new FilterCamera(this.context, valkyrie.main.R.array.filters);
		
		if (this.filterCamera.isOpened()) {
            (new Thread(this)).start();
        } else {
        	this.filterCamera.release();
        	this.filterCamera = null;
            Log.e(TAG, "Failed to open native camera");
        }
		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i(TAG, "surfaceDestroyed");
        
        if (this.filterCamera != null) {
            synchronized (this) {
            	this.filterCamera.release();
            	this.filterCamera = null;
            }
        }
	}
	
	private Size getCameraPreviewSize(int frameWidth, int frameHeight) {
		
		List<Size> supportedPreviewSizes = this.filterCamera.getSupportedPreviewSizes(); 
		
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
