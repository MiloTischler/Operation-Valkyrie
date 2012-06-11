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
    	int arr[] = new int[100];

    	for(int i = 0; i < 100; i++) {
        	arr[i] = 1;
    	}
    	Bitmap bitmap = Bitmap.createBitmap(arr,10, 10, Bitmap.Config.RGB_565);
    	FileManager fm = new FileManager(this.getContext());
    	comparedImage = fm.getLatestImage();    	

    	fm.saveImageToGallery(bitmap);
    	
    	assertFalse("Error: Image wasn't saved",comparedImage.equals(fm.getLatestImage()));
    }
    
    public void testLoadImage() {
    	FileManager fm = new FileManager(this.getContext());    	
    	fm.loadImageFromGallery(fm.getLatestImage());
    	assertTrue("Error: Couldn't load Image", fm.getLatestImage() != "Error");

    }
    public void testDeleteImage() {

    	FileManager fm = new FileManager(this.getContext());
    	String imageName = fm.getLatestImage();
    	fm.deleteImageFromGallery(imageName);  
    	assertFalse("Error. Image not deleted", imageName.equals(fm.getLatestImage()));    	
    }
    
    public void testFolderEmpty() {
    	File file = new File(SDPATH);
    	assertTrue("Folder not yet created", file.exists());
    }
}



