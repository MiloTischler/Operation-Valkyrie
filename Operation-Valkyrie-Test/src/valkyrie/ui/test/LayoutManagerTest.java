package valkyrie.ui.test;

import java.lang.reflect.Field;

import valkyrie.ui.LayoutManager;
import android.app.Activity;
import android.test.AndroidTestCase;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class LayoutManagerTest extends AndroidTestCase {
	
	public void testGetInstance() {
		LayoutManager layoutManager = LayoutManager.getInstance();
		assertNotNull(layoutManager);
	}

	public void testSetMainActivity() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
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
	
}
