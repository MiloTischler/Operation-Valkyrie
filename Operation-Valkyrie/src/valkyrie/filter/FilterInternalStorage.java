package valkyrie.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */

/**
 * Filters can access their internal storage folder by their FilterInternalStorage object
 */
public class FilterInternalStorage {
	private static final String TAG = "FilterInternalStorage";
	
	private String filterPath = null;
	private Context context = null;
	private File filterFolder = null;
	
	/**
	 * Initialize internal storage folder for the filter
	 * 
	 * @param IFilter filter
	 * @param Context context
	 */
	public FilterInternalStorage(IFilter filter, Context context) {
		this.filterPath = new String(filter.getClass().getSimpleName());
		this.context = context;
		this.filterFolder = this.context.getDir(this.filterPath, Context.MODE_PRIVATE);
		
		Log.i(TAG, "New internal storage folder for filter: " + filter.getClass().getSimpleName());
	}
	
	/**
	 * Opens a FileOutputStream to a file inside the filter folder
	 * 
	 * @param String fileName
	 * @return FileOutputStream
	 * @throws FileNotFoundException
	 */
	public FileOutputStream openFileOutput(String fileName) throws FileNotFoundException {				
		return new FileOutputStream(this.filterFolder.getPath() + "/" + fileName);
	}
	
	/**
	 * Opens a FileInputStream to a file inside the filter folder
	 * 
	 * @param String fileName
	 * @return FileInputStream
	 * @throws FileNotFoundException
	 */
	public FileInputStream openFileInput(String fileName) throws FileNotFoundException {
		return new FileInputStream(this.filterFolder.getPath() + "/" + fileName);
	}
	
	/**
	 * Checks if the filter folder in the internal storage exists
	 * 
	 * @return true if the root folder exists
	 */
	public boolean rootFolderExists() {		
		if(this.filterFolder.isDirectory() && this.filterFolder.exists()) {
			return true;
		}
		
		return false;
	}
}
