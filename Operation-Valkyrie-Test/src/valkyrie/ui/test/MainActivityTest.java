package valkyrie.ui.test;

import com.jayway.android.robotium.solo.Solo;

import valkyrie.ui.MainActivity;
import valkyrie.widget.MultiDirectionSlidingDrawer;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.widget.ImageView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
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
		
		solo = new Solo(this.getInstrumentation(), this.mainActivity);
	}

	public void testPreconditions() {
		assertNotNull(this.mainActivity);
		assertNotNull(this.getInstrumentation());
		assertNotNull(this.solo);
	}

	public void testFilterOptionsPanelSlideOut() {
		MultiDirectionSlidingDrawer multiDirectionSlidingDrawer = (MultiDirectionSlidingDrawer) this.getActivity()
				.findViewById(valkyrie.main.R.id.filterOptionsPanel);
		
		assertNotNull(multiDirectionSlidingDrawer);

		Display display = this.getActivity().getWindowManager().getDefaultDisplay();

		ImageView filterOptionsHandle = (ImageView) this.getActivity().findViewById(valkyrie.main.R.id.filterOptionsHandle);

		assertNotNull(filterOptionsHandle);
		
		assertTrue(filterOptionsHandle.getRight() == filterOptionsHandle.getWidth());
		
		//Drag handler from left to right
		this.solo.drag(filterOptionsHandle.getWidth() / 2, display.getWidth() / 2, display.getHeight() / 2, display.getHeight() / 2, 20);
		
		this.solo.sleep(500);
		
		assertTrue(filterOptionsHandle.getRight() == display.getWidth());
		
		//Drag handler from right to left
		this.solo.drag(display.getWidth() - (filterOptionsHandle.getWidth() / 2), 0, display.getHeight() / 2, display.getHeight() / 2, 20);
		
		this.solo.sleep(500);
		
		assertTrue(filterOptionsHandle.getRight() == filterOptionsHandle.getWidth());
	}
}
