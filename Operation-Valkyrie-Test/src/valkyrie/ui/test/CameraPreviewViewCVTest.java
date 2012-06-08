package valkyrie.ui.test;

import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewViewCVTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "CameraPreviewViewCVTest";
	
	public CameraPreviewViewCVTest() {
        super("valkyrie.ui", MainActivity.class);
     }
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    public void testTakePicture() {

    }
}
