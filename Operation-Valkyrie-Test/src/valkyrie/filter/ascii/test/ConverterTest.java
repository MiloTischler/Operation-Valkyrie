package valkyrie.filter.ascii.test;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import valkyrie.filter.ascii.Converter;
import valkyrie.filter.ascii.Font;
import valkyrie.ui.MainActivity;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

public class ConverterTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Mat mat;
	private Bitmap input;
	
	public ConverterTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		this.input = BitmapFactory.decodeResource(this.getActivity().getResources(), valkyrie.main.test.R.drawable.camera_test_pictrue);
		this.mat = new Mat();
		this.mat = Utils.bitmapToMat(this.input);
	}
	
	public void testBWtoAscci() {
		Converter con = new Converter();
		Font font = new Font("test", true);
		Bitmap ascii = con.grayscaleToASCII(this.mat, font.getLUT());
		assertNotNull(ascii);
		assertTrue((ascii.getHeight() <= this.input.getHeight()));
		assertTrue((ascii.getWidth() <= this.input.getWidth()));
	}
	
	public void testColortoAscci(){
		Converter con = new Converter();
		Font font = new Font("test", true);
		Bitmap ascii = con.colorToASCII(this.mat, font.getLUT());
		assertNotNull(ascii);
		assertTrue((ascii.getHeight() <= this.input.getHeight()));
		assertTrue((ascii.getWidth() <= this.input.getWidth()));
	}

}
