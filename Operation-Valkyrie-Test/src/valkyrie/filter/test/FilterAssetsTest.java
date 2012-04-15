package valkyrie.filter.test;

import java.io.IOException;
import java.io.InputStream;

import valkyrie.filter.FilterAssets;
import valkyrie.filter.nofilter.NoFilter;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FilterAssetsTest extends AndroidTestCase{
	private static final String TAG = "FilterAssetsTest";
	
	public FilterAssetsTest() {
		super();
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    }
    
    public void testList() {
    	NoFilter noFilter = new NoFilter();
    	FilterAssets noFilterAssets = new FilterAssets(noFilter, this.getContext());
    	
    	try {
			assertTrue(noFilterAssets.list("icon").length > 0);
			assertTrue(noFilterAssets.list("/").length == 0);
			assertTrue(noFilterAssets.list("").length == 0);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
    	
    }
    
    public void testOpen() {
    	NoFilter noFilter = new NoFilter();
    	FilterAssets noFilterAssets = new FilterAssets(noFilter, this.getContext());
    	
    	try {
			InputStream test = noFilterAssets.open("icon/" + noFilterAssets.list("icon")[0]);
			assertNotNull(test);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
    }
}
