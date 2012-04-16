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

	final static String SDPATH =Environment.getExternalStorageDirectory().toString() + "/Valkyrie/Gallery/";
	
	public FileManagerTest() {
        super();
     }
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    
    public void testSaveImage() {
    	String comparedImage;
    	Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8);
    	FileManager fm = new FileManager();
    	
    	comparedImage = fm.getLatestImage();
    	
    	fm.saveImageToGallery(bitmap);
    	
    	assertFalse("Image is not another one",comparedImage.equals(fm.getLatestImage()));
    }
    
    public void testLoadImage() {
    	FileManager fm = new FileManager();
    	
    	fm.loadImageFromGallery(fm.getLatestImage());
    	assertTrue(fm.getLatestImage() != "Error");

    }
    
    public void testDeleteImage() {

    	FileManager fm = new FileManager();
    	String imageName = fm.getLatestImage();

    	fm.deleteImageFromGallery(imageName);  
    	assertFalse("Error. Image not deleted", imageName.equals(fm.getLatestImage()));

    	
    }
    public void testFolderEmpty() {
//    	FileManager fm = new FileManager();
    	File file = new File(SDPATH);
    	assertTrue("Folder not yet created", file.exists());
    }
}



