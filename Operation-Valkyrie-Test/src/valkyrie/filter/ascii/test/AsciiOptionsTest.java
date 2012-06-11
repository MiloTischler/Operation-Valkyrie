package valkyrie.filter.ascii.test;

import java.lang.reflect.Field;

import valkyrie.colorpicker.ColorPicker;
import valkyrie.filter.FilterManager;
import valkyrie.filter.ascii.Ascii;
import valkyrie.filter.ascii.Converter;
import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;

import android.content.Context;
import android.graphics.Point;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

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

		try {
			this.runTestOnUiThread(new Runnable() {
				public void run() {

					CameraPreviewViewCV cameraPreviewView = (CameraPreviewViewCV) getActivity().findViewById(
							valkyrie.main.R.id.camera_preview_view);

					FilterManager filterCamera = new FilterManager(getActivity(), valkyrie.main.R.array.filters,
							cameraPreviewView);

					filterCamera.setActiveFilter(new Ascii());

				}
			});
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
			assertTrue(false);
		}
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
		int screenWidth = solo.getCurrentActivity().getWindowManager()
				.getDefaultDisplay().getWidth();
		int	screenHeight = solo.getCurrentActivity().getWindowManager()
				.getDefaultDisplay().getHeight();
		Log.d("test","width = " + screenWidth);
		
		this.solo.sleep(500);
		solo.sendKey(Solo.MENU);
		solo.clickOnView(solo.getView(R.id.backgroundcolor));
		this.solo.sleep(500);
		this.solo.clickOnScreen(screenWidth/2+120,(screenHeight/2));
		this.solo.sleep(300);
		this.solo.clickOnScreen(screenWidth/2,(screenHeight/2)+40);
		this.solo.sleep(500);
		this.solo.sendKey(Solo.MENU);
		this.solo.sleep(2000);
		
		Field field;
		try {
			field = Converter.class.getDeclaredField("forgroundcolor");
			field.setAccessible(true);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}

	public void testChangeForegroundColor() {
		
		int screenWidth = solo.getCurrentActivity().getWindowManager()
				.getDefaultDisplay().getWidth();
		int	screenHeight = solo.getCurrentActivity().getWindowManager()
				.getDefaultDisplay().getHeight();
		Log.d("test","width = " + screenWidth);
		
		this.solo.sleep(500);
		solo.sendKey(Solo.MENU);
		solo.clickOnView(solo.getView(R.id.foregroundcolor));
		this.solo.sleep(500);
		this.solo.clickOnScreen(screenWidth/2,screenHeight/2);
		this.solo.sleep(300);
		this.solo.clickOnScreen(screenWidth/2,screenHeight/2+40);
		this.solo.sleep(300);
		this.solo.sendKey(Solo.MENU);
		this.solo.sleep(2000);
		
		
	}

	public void testChangeColorMode() {
		fail("Not yet implemented");
	}
}
