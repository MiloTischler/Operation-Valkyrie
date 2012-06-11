package valkyrie.ui.gallery.test;

import java.io.File;
import java.util.ArrayList;

import valkyrie.ui.MainActivity;
import valkyrie.ui.gallery.AboutActivity;
import valkyrie.ui.gallery.GalleryActivity;
import valkyrie.ui.gallery.ShowPicActivity;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;

import com.jayway.android.robotium.solo.Solo;



public class GalleryActivityTest extends
		ActivityInstrumentationTestCase2<GalleryActivity> {

	private static final String TAG = "GalleryActivityTest";
	private GalleryActivity galleryActivity;
	private Solo solo;
	File file = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	File filelist[] = file.listFiles();

	public GalleryActivityTest() {
		super("valkyrie.ui.gallery", GalleryActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		this.galleryActivity = this.getActivity();

		solo = new Solo(this.getInstrumentation(), this.galleryActivity);
	}
	
	public void testPreconditions() {
		assertNotNull(this.galleryActivity);
		assertNotNull(this.getInstrumentation());
		assertNotNull(this.solo);
		this.solo.assertCurrentActivity("GalleryActivity", GalleryActivity.class);
	}
	
	public void testClickImage () {
		
		int index = 0;
		
		for (File f : filelist){
			assertNotNull(f);
			this.solo.clickOnImage(index);
			this.solo.assertCurrentActivity("ShowPicActivity", ShowPicActivity.class);
			this.solo.goBack();
			this.solo.assertCurrentActivity("ShowPicActivity", GalleryActivity.class);
			index++;			
		}
	}
	
	public void testLongClickImage (){
		
		ArrayList<GridView> views = new ArrayList<GridView>();
		views= this.solo.getCurrentGridViews();
		views.get(0).getCount();
		int pictures = views.get(0).getCount();
		

		
		if (file.listFiles().length  >= 2){
		this.solo.clickLongOnView(views.get(0).getChildAt(0));
		this.solo.clickOnText("Delete selected Picture");
		this.solo.sleep(1000);
		
		views= this.solo.getCurrentGridViews();
		assertEquals(pictures-1, views.get(0).getCount());
		
		this.solo.clickLongOnView(views.get(0).getChildAt(0));
		this.solo.clickOnText("Delete all Pictures");
		this.solo.clickOnText("Yes");
		this.solo.sleep(1000);
	
		views= this.solo.getCurrentGridViews();
		assertEquals(0, views.get(0).getCount());
		}
	}
	
	public void testAboutScreen () {
		
		this.solo.clickOnMenuItem("About");
		this.solo.assertCurrentActivity("AboutScreen", AboutActivity.class);
		this.solo.goBack();
		this.solo.assertCurrentActivity("GalleryActivity", GalleryActivity.class);
		
	}

	
}
