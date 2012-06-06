package valkyrie.ui;

import valkyrie.filter.IFilter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.Size;

public class CameraPreviewView extends SurfaceView implements Camera.PreviewCallback {
	private static final String TAG = "CameraPreviewView";

	private Bitmap actBmp = null;

	private int[] pixels = null;

	private Size previewSize = null;

	private SurfaceHolder surfaceHolder = null;
	
	private IFilter filter = null;
	
	private long fpsTime = 0;
	private int frameCounter = 0;

	public CameraPreviewView(Context context) {
		super(context);

		this.surfaceHolder = this.getHolder();
	}

	public CameraPreviewView(Context context, AttributeSet attr) {
		super(context, attr);

		this.surfaceHolder = this.getHolder();
	}
	
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}

	public void setPreviewSize(Size previewSize) {
		this.previewSize = previewSize;

		this.pixels = new int[this.previewSize.width * this.previewSize.height];
	}

	public void onPreviewFrame(byte[] data, Camera camera) {
		
		if (camera == null || this.getVisibility() == View.GONE) {
			return;
		}
		
		//Calculate FPS
		this.frameCounter++;
		
		long delay = System.currentTimeMillis() - this.fpsTime;
		if (delay > 1000) {            
            Log.i(TAG, "Preview Frame FPS:" + (((double)frameCounter)/delay)*1000);
            
            this.frameCounter = 0;
            this.fpsTime = System.currentTimeMillis(); 
        }
		
		//Convert data[] to bitmap
		int format = camera.getParameters().getPreviewFormat();

		switch (format) {
		case ImageFormat.NV21:
		case ImageFormat.YUY2:
			Log.i(TAG, "Camera preview using NV21 or YUY2");
			CameraPreviewView.decodeYUV420SP(pixels, data, this.previewSize.width, previewSize.height);
			this.actBmp = Bitmap.createBitmap(pixels, this.previewSize.width, this.previewSize.height, Config.ARGB_8888);
			break;
		case ImageFormat.JPEG:
		case ImageFormat.RGB_565:
			Log.i(TAG, "Camera preview using JPEG or RGB");
			this.actBmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		default:
			Log.e(TAG, "Camera preview format not supported!");
		}

		//Draw bitmap on surface canvas
		Canvas canvas = this.surfaceHolder.lockCanvas();
		
		if(this.filter != null && this.actBmp != null) {
			this.actBmp = this.filter.manipulatePreviewImage(this.actBmp);
		}
		
		canvas.drawBitmap(this.actBmp, 0, 0, null);
		
		this.surfaceHolder.unlockCanvasAndPost(canvas);

		//Cleanup
		if(this.actBmp != null) {
			this.actBmp.recycle();
		}
	}

	private static void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {

		final int frameSize = width * height;

		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
	}
}
