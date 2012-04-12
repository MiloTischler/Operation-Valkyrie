package valkyrie.filter.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import valkyrie.filter.FilterManager;
import valkyrie.filter.ascii.Ascii;

public class FilterManagerTest extends AndroidTestCase {
	
	public FilterManagerTest() {
        super();
     }
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    public void testChangeFilter() {
    	Ascii asciiFilterOriginal = new Ascii();
    	FilterManager.getInstance().changeFilter(asciiFilterOriginal);
    	Ascii asciiFilterReturned = (Ascii) FilterManager.getInstance().getActiveFilter();
    	
    	assertEquals(asciiFilterOriginal, asciiFilterReturned);
    }
    
    public void testGetFilterList() {
    	assertNotNull(FilterManager.getInstance().getFilterList());
    	assertTrue(FilterManager.getInstance().getFilterList() instanceof ArrayList<?>);
    }
    
    public void testGetActiveFilter() {
    	assertNotNull(FilterManager.getInstance().getActiveFilter());
    }
}
