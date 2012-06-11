/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler 
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

package valkyrie.ui.test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import valkyrie.ui.IUpdateableUI;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class UpdateableRelativeLayoutTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private static final String TAG = "UpdateableRelativeLayoutTest";
	private Activity mainActivity = null;

	public UpdateableRelativeLayoutTest() {
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
	 * Tests if UpdateableRelativeLayout components are registered at the LayoutManager.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void testUpdateableRelativeLayout() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field;

		field = LayoutManager.class.getDeclaredField("registeredComponents");
		field.setAccessible(true);

		LayoutManager object = LayoutManager.getInstance();
		ArrayList<IUpdateableUI> returnObj = (ArrayList<IUpdateableUI>) field.get(object);

		assertNotNull(returnObj);
	}

}
