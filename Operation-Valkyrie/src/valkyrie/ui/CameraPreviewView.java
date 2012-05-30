package valkyrie.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewView extends ImageView {
	private static final String TAG = "CameraPreviewView";

	private Canvas cameraCanvas = null;
	
	public CameraPreviewView(Context context) {
		super(context);
	}
	
	public CameraPreviewView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public void setCameraCanvas(Canvas cameraCanvas) {		
		this.cameraCanvas = cameraCanvas;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {		
		if(this.cameraCanvas != null) {
			super.onDraw(this.cameraCanvas);
		}
	}

}
