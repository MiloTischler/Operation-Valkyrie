package valkyrie.filter.ascii.test;

import valkyrie.filter.FilterManager;
import valkyrie.filter.ascii.Ascii;
import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class AsciiOptionsTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "AsciiOptionsTest";

	private MainActivity mainActivity;

	private Solo solo;

	public AsciiOptionsTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.mainActivity = this.getActivity();

		LayoutManager.getInstance().setMainActivity(this.getActivity());

		solo = new Solo(this.getInstrumentation(), this.mainActivity);
		
		CameraPreviewViewCV cameraPreviewView = (CameraPreviewViewCV) this.getActivity().findViewById(
				valkyrie.main.R.id.camera_preview_view);
		
		FilterManager filterCamera = new FilterManager(this.getActivity(), valkyrie.main.R.array.filters,
				cameraPreviewView);
		
		filterCamera.setActiveFilter(new Ascii());
	}

	public void testPreconditions() {
		assertNotNull(this.mainActivity);
		assertNotNull(this.getInstrumentation());
		assertNotNull(this.solo);
	}
	
	public void testChangeFontSize() {
		fail("Not yet implemented");
		
		this.solo.sendKey(Solo.MENU);
		
		assertNotNull(this.solo.getView(R.id.ascii_option_fontsize));
	}
	
	public void testChangeBackgroundColor() {
		fail("Not yet implemented");
	}
	
	public void testChangeForegroundColor() {
		fail("Not yet implemented");
	}
	
	public void testChangeColorMode() {
		fail("Not yet implemented");
	}
}
