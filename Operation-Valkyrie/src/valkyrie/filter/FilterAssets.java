package valkyrie.filter;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

//TODO: problem.. maybe some filters want to write files..
// .. solution: Export their assets folder in internal storage by installation O.O
// .. give them a handle to that exported folder

// or.. not by installation, maybe check if folder in storage exists

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FilterAssets {
	private static final String TAG = "FilterAssets";
	
	private AssetManager assetManager = null;
	private String filterPath = null;
	
	public FilterAssets(IFilter filter, Context context) {
		this.filterPath = new String(filter.getClass().getSimpleName());
		this.assetManager = context.getAssets();
	}
	
	public String[] list(String path) throws IOException {
		if(path.equals("/") || path.equals("\\")) {
			path = "";
		}
		
		return this.assetManager.list(this.filterPath + "/" + path);
	}
	
	public InputStream open(String fileName) throws IOException {
		return this.assetManager.open(this.filterPath + "/" + fileName);
	}
}
