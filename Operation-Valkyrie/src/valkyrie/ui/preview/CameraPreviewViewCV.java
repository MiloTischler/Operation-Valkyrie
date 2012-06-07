package valkyrie.ui.preview;

import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import valkyrie.filter.IFilter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class CameraPreviewViewCV extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "Sample::SurfaceView";

    private SurfaceHolder       mHolder;
    private VideoCapture        mCamera;
    
    private IFilter filter;
    private boolean isFilterDisplayed = false;

    public CameraPreviewViewCV(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        Log.i(TAG, "Instantiated new " + this.getClass());
    }
    
    public CameraPreviewViewCV(Context context, AttributeSet attr) {
        super(context, attr);
        mHolder = getHolder();
        mHolder.addCallback(this);
        Log.i(TAG, "Instantiated new " + this.getClass());
    }
    
    public void setFilter(IFilter filter) {
    	this.filter = filter;
    }
    
	public boolean isFilterDisplayed() {
		if (this.isFilterDisplayed == true) {
			return true;
		}

		return false;
	}
	
	public void toggleFilter(boolean display) {
		if (this.isFilterDisplayed) {
			this.isFilterDisplayed = false;
		} else {
			this.isFilterDisplayed = true;
		}
	}

    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        Log.i(TAG, "surfaceCreated");
        synchronized (this) {
            if (mCamera != null && mCamera.isOpened()) {
                Log.i(TAG, "before mCamera.getSupportedPreviewSizes()");
                List<Size> sizes = mCamera.getSupportedPreviewSizes();
                Log.i(TAG, "after mCamera.getSupportedPreviewSizes()");
                int mFrameWidth = width;
                int mFrameHeight = height;

                // selecting optimal camera preview size
                {
                    double minDiff = Double.MAX_VALUE;
                    for (Size size : sizes) {
                        if (Math.abs(size.height - height) < minDiff) {
                            mFrameWidth = (int) size.width;
                            mFrameHeight = (int) size.height;
                            minDiff = Math.abs(size.height - height);
                        }
                    }
                }

                mCamera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, mFrameWidth);
                mCamera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, mFrameHeight);
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mCamera = new VideoCapture(Highgui.CV_CAP_ANDROID);
        if (mCamera.isOpened()) {
            (new Thread(this)).start();
        } else {
            mCamera.release();
            mCamera = null;
            Log.e(TAG, "Failed to open native camera");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        if (mCamera != null) {
            synchronized (this) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    protected Bitmap processFrame(VideoCapture capture) {
    	Mat rgba = new Mat();

		capture.retrieve(rgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);

		Bitmap bmp = Bitmap.createBitmap(rgba.cols(), rgba.rows(), Bitmap.Config.ARGB_8888);

		if (Utils.matToBitmap(rgba, bmp)) {
			if(this.filter != null && this.isFilterDisplayed) {
				bmp = this.filter.manipulatePreviewImage(bmp);
			}
			
			return bmp;
		}

		bmp.recycle();

		return null;
    }

    public void run() {
        Log.i(TAG, "Starting processing thread");
        while (true) {
            Bitmap bmp = null;

            synchronized (this) {
                if (mCamera == null) {
                	Log.e(TAG, "mCamera == null");
                	break;
                }

                if (!mCamera.grab()) {
                    Log.e(TAG, "mCamera.grab() failed");
                    break;
                }

                bmp = processFrame(mCamera);
            }

            if (bmp != null) {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth() - bmp.getWidth()) / 2, (canvas.getHeight() - bmp.getHeight()) / 2, null);
                    mHolder.unlockCanvasAndPost(canvas);
                }
                bmp.recycle();
            }
        }

        Log.i(TAG, "Finishing processing thread");
    }
}
