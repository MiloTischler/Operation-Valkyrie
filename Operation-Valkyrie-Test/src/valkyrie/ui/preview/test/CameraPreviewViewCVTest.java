package valkyrie.ui.preview.test;

import valkyrie.main.R;
import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;
import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewViewCVTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "CameraPreviewViewCVTest";
	
	private CameraPreviewViewCV cameraPreview = null;
	
	public CameraPreviewViewCVTest() {
        super("valkyrie.ui", MainActivity.class);
     }
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    
    	this.cameraPreview = (CameraPreviewViewCV) this.getActivity().findViewById(R.id.camera_preview_view);
    }
    
    public void testPreviewSurface() {
    	assertTrue(this.cameraPreview.isShown());
    	assertNotNull(this.cameraPreview.getHolder());
    }
    
    public void testTakePicture() {
    	Bitmap bitmap = this.cameraPreview.takePicture();
    	
    	//Test if we get a valid bitmap
    	assertNotNull(bitmap);
    	assertTrue(bitmap.getWidth() > 0 && bitmap.getHeight() > 0);
    	
    	//Test camera lock
    	assertTrue(this.cameraPreview.isLocked());
    	this.cameraPreview.resume();
    	assertFalse(this.cameraPreview.isLocked());
    }
}
