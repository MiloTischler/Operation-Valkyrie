package valkyrie.file.test;

import java.io.File;

import valkyrie.file.FileManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.test.AndroidTestCase;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FileManagerTest extends AndroidTestCase{

	final static String pathName =Environment.getExternalStorageDirectory().toString() + "/Valkyrie/Gallery/";
	
	public FileManagerTest() {
        super();
     }
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    public void testSaveImage() {
    	
    	Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8);
    	FileManager fm = new FileManager();
    	for(int i = 0; i < 2 ; i++) {
    	fm.saveImageToGallery(bitmap);
    	}
    	assertTrue("Image is here",fm.imageExists(fm.getLatestImage()));
    }
    
    public void testLoadImage() {
    	//TODO laurenz
    	fail("Not yet implemented");
    }
    
    public void testDeleteImage() {
    	//TODO laurenz
    	FileManager fm = new FileManager();
    	String imageName = fm.getLatestImage();
    	fm.deleteImageFromGallery(imageName);   
    	
    	fail("Not yet implemented");
    	
    }
}



