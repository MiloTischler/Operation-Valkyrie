package valkyrie.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;

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
	
	public FilterInternalStorage(IFilter filter, Context context) {
		this.filterPath = new String(filter.getClass().getSimpleName());
		this.context = context;
		
		//TODO: Check if folder for filter exists, otherwise create it!
	}
	
	public FileOutputStream openFileOutput(String fileName) throws FileNotFoundException {
		return this.context.openFileOutput(this.filterPath + "/" + fileName, Context.MODE_PRIVATE);
	}
	
	public FileInputStream openFileInput(String fileName) throws FileNotFoundException {
		return this.context.openFileInput(this.filterPath + "/" + fileName);
	}
	
	private boolean folderExists() {
		return true;
	}
}
