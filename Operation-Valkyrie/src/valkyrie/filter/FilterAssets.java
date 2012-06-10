package valkyrie.filter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */

/**
 * Filters can access their asset folder by their FilterAssets object
 */
public class FilterAssets {
	private static final String TAG = "FilterAssets";
	
	private AssetManager assetManager = null;
	private String filterPath = null;
	
	/**
	 * Initialize android assets
	 * 
	 * @param IFilter filter
	 * @param Context context
	 */
	public FilterAssets(IFilter filter, Context context) {
		this.filterPath = new String(filter.getClass().getSimpleName());
		this.assetManager = context.getAssets();
		
		Log.i(TAG, "Init asset folder access for filter: " + filter.getClass().getSimpleName());
	}
	
	/**
	 * Returns a list of all the files and folders in the filter asset folder
	 * 
	 * @param String path inside the asset folder
	 * @return String[]
	 * @throws IOException
	 */
	public String[] list(String path) throws IOException {
		if(path.equals("/") || path.equals("\\")) {
			path = "";
		}
		
		return this.assetManager.list(this.filterPath + "/" + path);
	}
	
	/**
	 * Opens a InputStream to a file in the filter asset folder
	 * 
	 * @param String fileName
	 * @return InputStream 
	 * @throws IOException
	 */
	public InputStream open(String fileName) throws IOException {
		return this.assetManager.open(this.filterPath + "/" + fileName);
	}
}
