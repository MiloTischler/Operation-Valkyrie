package valkyrie.file.test;

import java.io.File;

import valkyrie.file.DecodeBitmaps;

import android.os.Environment;
import android.test.AndroidTestCase;

public class DecodeBitmapTest extends AndroidTestCase {

	final static String SDPATH = Environment.getExternalStorageDirectory()
			.toString() + "/Valkyrie/Gallery/";
	final static String THUMBPATH = Environment.getExternalStorageDirectory()
			.toString() + "/Valkyrie/Thumbnls/";
	private File files = new File(SDPATH);
	private File thumbs = new File(THUMBPATH);
	private File filelist[] = files.listFiles();
	private File thumblist[] = thumbs.listFiles();

	public DecodeBitmapTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testFiles() {
		new DecodeBitmaps(0);
		assertEquals(DecodeBitmaps.fullImgPosition.size(), filelist.length);
		assertEquals(DecodeBitmaps.fullImgNames.size(), filelist.length);

	}

	public void testThumbs() {
		new DecodeBitmaps(0);
		
		assertEquals(DecodeBitmaps.thumbPosition.size(), thumblist.length);
		
	}

}
