/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

package valkyrie.ui.test;

import java.lang.reflect.Field;

import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.ViewGroup;

public class LayoutManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private static final String TAG = "LayoutManagerTest";
	private Activity mainActivity = null;

	public LayoutManagerTest() {
		super("valkyrie.ui", MainActivity.class);
	}

	/**
	 * Set the real mainActivity of the Application.
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.mainActivity = this.getActivity();

		LayoutManager.getInstance().setMainActivity(this.getActivity());
	}

	/**
	 * Tests whether the singleton instance is created correctly.
	 */
	public void testGetInstance() {
		LayoutManager layoutManager = LayoutManager.getInstance();
		assertNotNull(layoutManager);
	}

	/**
	 * Tests if private member variable mainActivity is set correctly from the setter method
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testSetMainActivity() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {

		// simulate programs mainActivity
		Activity mainActivity = new Activity();

		// set it
		LayoutManager.getInstance().setMainActivity(mainActivity);

		// check if private attribute was set correctly, via reflection
		Field field = LayoutManager.class.getDeclaredField("mainActivity");
		field.setAccessible(true);

		LayoutManager object = LayoutManager.getInstance();
		Activity returnObj = (Activity) field.get(object);

		assertNotNull(returnObj);
	}

	/**
	 * Tests if the UI elements were added.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testNotifyUI() {

		try {
			this.runTestOnUiThread(new Runnable() {
				public void run() {

					Field field;

					try {
						field = MainActivity.class.getDeclaredField("filterManager");

						field.setAccessible(true);

						FilterManager filterManager = (FilterManager) field.get(mainActivity);
						IFilter filterObject = filterManager.getActiveFilter();

						LayoutManager.getInstance().notifyUI(filterObject);

						ViewGroup optionsContent = (ViewGroup) mainActivity.findViewById(R.id.filter_options_content);
						int elementCount = optionsContent.getChildCount();
						assertTrue(elementCount > 0);
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
			assertTrue(false);
		}
	}
}
