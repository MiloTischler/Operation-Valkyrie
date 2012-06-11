package valkyrie.filter.ascii.test;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import valkyrie.filter.ascii.Ascii;
import valkyrie.ui.MainActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class AsciiTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "AsciiTest";
	private Mat mat;
	private Bitmap input;

	public AsciiTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		this.input = BitmapFactory.decodeResource(this.getActivity()
				.getResources(),
				valkyrie.main.test.R.drawable.camera_test_pictrue);
		this.mat = new Mat();
		Utils.bitmapToMat(this.input, mat);
	}
	
	public void testReturnValues(){
		Ascii ascii = new Ascii();
		assertNotNull(ascii.manipulatePreviewImage(this.mat));
		assertNotNull(ascii.getName());
		assertNotNull(ascii.getFilterCaptureFormat());
		assertNotNull(ascii.getUIElements(this.getActivity()));
	}
	
}
