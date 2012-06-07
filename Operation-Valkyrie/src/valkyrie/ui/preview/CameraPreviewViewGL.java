package valkyrie.ui.preview;

import valkyrie.filter.IFilter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewViewGL extends GLSurfaceView implements Camera.PreviewCallback {
	private static final String TAG = "CameraPreviewViewGL";
	
	private IFilter filter = null;
	
	private int[] pixels = null;
	
	private Size previewSize = null;
	
	private Bitmap bitmap = null;
	
	private CameraPreviewRendererGL renderer = null;

	public CameraPreviewViewGL(Context context) {
		super(context);
		
		this.renderer = new CameraPreviewRendererGL();
		
		this.setRenderer(this.renderer);
	}
	
	public CameraPreviewViewGL(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.renderer = new CameraPreviewRendererGL();
		
		this.setRenderer(this.renderer);
	}
	
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}
	
	public void setPreviewSize(Size previewSize) {
		this.previewSize = previewSize;

		this.pixels = new int[this.previewSize.width * this.previewSize.height];
	}
	
	public void onPreviewFrame(byte[] data, Camera camera) {
		
		if (camera == null) {
			return;
		}

		int format = camera.getParameters().getPreviewFormat();

		switch (format) {
		case ImageFormat.NV21:
		case ImageFormat.YUY2:
			Log.i(TAG, "Camera preview using NV21 or YUY2");
			this.decodeYUV420SP(pixels, data, this.previewSize.width, previewSize.height);
			this.bitmap = Bitmap.createBitmap(pixels, this.previewSize.width, this.previewSize.height, Config.ARGB_8888);
			break;
		case ImageFormat.JPEG:
		case ImageFormat.RGB_565:
			Log.i(TAG, "Camera preview using JPEG or RGB");
			this.bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		default:
			Log.e(TAG, "Camera preview format not supported!");
		}
		
		Log.d(TAG, this.previewSize.width + " - " + this.previewSize.height);
		
		if(this.filter != null && this.bitmap != null) {
			this.bitmap = this.filter.manipulatePreviewImage(this.bitmap);
		}
		
		this.renderer.setBitmap(this.bitmap);
	}

	void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {

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
