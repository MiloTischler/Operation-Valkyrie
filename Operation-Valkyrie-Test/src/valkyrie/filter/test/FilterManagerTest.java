package valkyrie.filter.test;

import java.util.ArrayList;

import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.filter.nofilter.NoFilter;
import valkyrie.filter.FilterAssets;
import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import valkyrie.ui.preview.CameraPreviewViewCV;

import org.opencv.*;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class FilterManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "FilterCameraTest";

	public FilterManagerTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		LayoutManager.getInstance().setMainActivity(this.getActivity());
	}

	public void testGetFilterList() {
		CameraPreviewViewCV cameraPreviewView = (CameraPreviewViewCV) this.getActivity().findViewById(
				valkyrie.main.R.id.camera_preview_view);
		FilterManager filterCamera = new FilterManager(this.getActivity(), valkyrie.main.R.array.filters,
				cameraPreviewView);

		assertNotNull(filterCamera.getFilterList());
		assertTrue(filterCamera.getFilterList() instanceof ArrayList<?>);

		ArrayList<IFilter> filters = filterCamera.getFilterList();

		// there has to be at least one filter in the list, the NoFilter
		assertTrue(filters.isEmpty() == false);

		// check if we can find a NoFilter instance..
		Boolean foundNoFilter = false;

		for (IFilter filter : filters) {
			if (filter instanceof NoFilter) {
				foundNoFilter = true;
			}
		}

		assertTrue(foundNoFilter);
	}

	public void testActiveFilter() {
		try {
			this.runTestOnUiThread(new Runnable() {
				public void run() {

					CameraPreviewViewCV cameraPreviewView = (CameraPreviewViewCV) getActivity().findViewById(
							valkyrie.main.R.id.camera_preview_view);
					FilterManager filterManager = new FilterManager(getActivity(), valkyrie.main.R.array.filters,
							cameraPreviewView);

					assertNotNull(filterManager.getActiveFilter());

					ArrayList<IFilter> filters = filterManager.getFilterList();

					filterManager.setActiveFilter(filters.get(0));

					assertEquals(filterManager.getActiveFilter(), filters.get(0));

					IFilter unknown = filters.get(0);

					NoFilter noFilter = (NoFilter) unknown;

					filterManager.setActiveFilter(new NoFilter());

					assertEquals(filterManager.getActiveFilter(), noFilter);

				}
			});
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
			assertTrue(false);
		}

	}

	public void testIsFirstRun() {
		fail("Not yet implemented");
	}
}
