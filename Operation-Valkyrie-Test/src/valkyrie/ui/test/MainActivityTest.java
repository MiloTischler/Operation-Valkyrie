package valkyrie.ui.test;

import java.io.FileReader;

import com.jayway.android.robotium.solo.Solo;

import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;
import valkyrie.widget.MultiDirectionSlidingDrawer;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "MainActivityTest";

	private MainActivity mainActivity;

	private Solo solo;

	public MainActivityTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.mainActivity = this.getActivity();

		LayoutManager.getInstance().setMainActivity(this.getActivity());

		solo = new Solo(this.getInstrumentation(), this.mainActivity);
	}

	public void testPreconditions() {
		assertNotNull(this.mainActivity);
		assertNotNull(this.getInstrumentation());
		assertNotNull(this.solo);
	}

	public void testFilterOptionsPanelSlideOut() {
		MultiDirectionSlidingDrawer multiDirectionSlidingDrawer = (MultiDirectionSlidingDrawer) this.getActivity()
				.findViewById(valkyrie.main.R.id.filter_options_panel);

		assertNotNull(multiDirectionSlidingDrawer);

		Display display = this.getActivity().getWindowManager().getDefaultDisplay();

		ImageView filterOptionsHandle = (ImageView) this.getActivity().findViewById(
				valkyrie.main.R.id.filter_options_handle);

		assertNotNull(filterOptionsHandle);

		assertTrue(filterOptionsHandle.getRight() == filterOptionsHandle.getWidth());

		// Drag handler from left to right
		this.solo.drag(filterOptionsHandle.getWidth() / 2, display.getWidth() / 2, display.getHeight() / 2,
				display.getHeight() / 2, 20);

		this.solo.sleep(500);

		assertTrue(filterOptionsHandle.getRight() == multiDirectionSlidingDrawer.getWidth());

		// Drag handler from right to left
		this.solo.drag(multiDirectionSlidingDrawer.getWidth() - (filterOptionsHandle.getWidth() / 2), 0,
				display.getHeight() / 2, display.getHeight() / 2, 20);

		this.solo.sleep(500);

		assertTrue(filterOptionsHandle.getRight() == filterOptionsHandle.getWidth());
	}
	


	public void testTakePhoto() {
		fail("Not yet implemented");
	}

	public void testShowGallery() {
		fail("Not yet implemented");
	}

	public void testToggleFilterEffect() {
		CameraPreviewViewCV cameraPreview = (CameraPreviewViewCV) getActivity().findViewById(
				R.id.camera_preview_view);
		
		assertNotNull(this.solo.getView(R.id.filter_effect_toggle));	
		
		if (cameraPreview.isFilterDisplayed() == true) {
			this.solo.clickOnView(this.solo.getView(R.id.filter_effect_toggle));
			assertTrue(cameraPreview.isFilterDisplayed() == false);
		} else {
			this.solo.clickOnView(this.solo.getView(R.id.filter_effect_toggle));
			assertTrue(cameraPreview.isFilterDisplayed() == true);
		}
	}
}
