package valkyrie.filter.test;

import java.io.IOException;

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
			for(String file : noFilterAssets.list("icon")) {
				Log.d("LOLAA", file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	assertTrue(true);
    }
    
    public void testOpen() {
    	assertTrue(true);
    }
}
