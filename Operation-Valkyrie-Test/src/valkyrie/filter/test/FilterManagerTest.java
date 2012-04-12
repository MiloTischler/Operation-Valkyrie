package valkyrie.filter.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.filter.ascii.Ascii;
import valkyrie.filter.nofilter.NoFilter;

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
    	assertNotNull(FilterManager.getInstance().getFilterList(this.getContext()));
    	assertTrue(FilterManager.getInstance().getFilterList(this.getContext()) instanceof ArrayList<?>);
    	
    	ArrayList<IFilter> filters = FilterManager.getInstance().getFilterList(this.getContext());
    	
    	//there has to be at least one filter in the list, the NoFilter
    	assertTrue(filters.isEmpty() == false);
    	
    	//check if we can find a NoFilter instance..
    	Boolean foundNoFilter = false;
    	
    	for(IFilter filter : filters) {
    		if(filter instanceof NoFilter) {
    			foundNoFilter = true;
    		}
    	}
    	
    	assertTrue(foundNoFilter);    	
    }
    
    public void testGetActiveFilter() {
    	assertNotNull(FilterManager.getInstance().getActiveFilter());
    	assertTrue(FilterManager.getInstance().getActiveFilter() instanceof IFilter);
    }
}
