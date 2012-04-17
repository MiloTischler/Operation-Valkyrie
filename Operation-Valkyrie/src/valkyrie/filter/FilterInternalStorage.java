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
public class FilterInternalStorage {
	private static final String TAG = "FilterInternalStorage";
	
	private String filterPath = null;
	private Context context = null;
	private File filterFolder = null;
	
	public FilterInternalStorage(IFilter filter, Context context) {
		this.filterPath = new String(filter.getClass().getSimpleName());
		this.context = context;
		this.filterFolder = this.context.getDir(this.filterPath, Context.MODE_PRIVATE);
		
		Log.i(TAG, "New internal storage folder for filter: " + filter.getClass().getSimpleName());
	}
	
	public FileOutputStream openFileOutput(String fileName) throws FileNotFoundException {
		return this.context.openFileOutput(this.filterPath + "/" + fileName, Context.MODE_PRIVATE);
	}
	
	public FileInputStream openFileInput(String fileName) throws FileNotFoundException {
		return this.context.openFileInput(this.filterPath + "/" + fileName);
	}
	
	public boolean rootFolderExists() {
		if(this.filterFolder.isDirectory() && this.filterFolder.exists()) {
			return true;
		}
		
		return false;
	}
}
