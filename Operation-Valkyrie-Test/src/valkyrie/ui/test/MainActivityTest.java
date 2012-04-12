package valkyrie.ui.test;

import valkyrie.ui.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

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