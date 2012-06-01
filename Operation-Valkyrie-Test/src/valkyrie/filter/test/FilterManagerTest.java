package valkyrie.filter.test;

import java.util.ArrayList;

import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.filter.nofilter.NoFilter;
import valkyrie.filter.FilterAssets;

import org.opencv.*;

import android.test.AndroidTestCase;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FilterManagerTest extends AndroidTestCase {
	private static final String TAG = "FilterCameraTest";
	
	public FilterManagerTest() {
		super();
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    public void testGetFilterList() {
    	FilterManager filterCamera = new FilterManager(mContext, valkyrie.main.R.array.filters);
    	
    	assertNotNull(filterCamera.getFilterList());
    	assertTrue(filterCamera.getFilterList() instanceof ArrayList<?>);
    	
    	ArrayList<IFilter> filters = filterCamera.getFilterList();
    	
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
    
    public void testActiveFilter() {
    	FilterManager filterCamera = new FilterManager(mContext, valkyrie.main.R.array.filters);
    	
    	assertNotNull(filterCamera.getActiveFilter());
    	
    	ArrayList<IFilter> filters = filterCamera.getFilterList();
    	
    	filterCamera.setActiveFilter(filters.get(0));
    	
    	assertEquals(filterCamera.getActiveFilter(), filters.get(0));
    	
    	IFilter unknown = filters.get(0);
    	
    	NoFilter noFilter = (NoFilter) unknown;
    	
    	filterCamera.setActiveFilter(new NoFilter());
    	
    	assertEquals(filterCamera.getActiveFilter(), noFilter);
    }

    public void testIsFirstRun() {
    	fail("Not yet implemented");
    }
}
