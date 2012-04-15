package valkyrie.ui.test;

import valkyrie.ui.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mainActivity;
	
    public MainActivityTest() {
        super("valkyrie.ui", MainActivity.class);
     }
    
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    	this.mainActivity = this.getActivity();
    }
    
    public void testPreconditions() {
    	assertNotNull(this.mainActivity);
    }
}
