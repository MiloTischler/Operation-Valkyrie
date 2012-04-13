package valkyrie.filter;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import valkyrie.filter.nofilter.NoFilter;
import valkyrie.main.R;

//TODO: filters need a way to access context -> use asset folder

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FilterManager {
	private static final String TAG = "FilterManager";
	
	private static FilterManager instance = null;

	private ArrayList<IFilter> filters = new ArrayList<IFilter>();

	private IFilter activeFilter = new NoFilter();

	private FilterManager() {

	}

	public static FilterManager getInstance() {
		if (instance == null) {
			instance = new FilterManager();
		}

		return instance;
	}

	public void changeFilter(IFilter filter) {
		Log.i(TAG, "Changed filter to: " +  filter.getClass().getName());
		
		this.activeFilter = filter;
	}

	public ArrayList<IFilter> getFilterList(Context context) {
		
		this.filters.clear();

		ArrayList<String> filterNames = new ArrayList<String>(Arrays.asList(context
				.getResources().getStringArray(R.array.filters)));
		
		for(String filterName : filterNames) {
			IFilter filter = null;
			
			try {
				filter = (IFilter) Class.forName(filterName).newInstance();
			} catch (IllegalAccessException e) {
				Log.e(TAG, e.getMessage());
			} catch (InstantiationException e) {
				Log.e(TAG, e.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e(TAG, e.getMessage());
			} finally {
				if(filter != null) {
					Log.i(TAG, "Added filter to list: " +  filter.getClass().getName());
					
					this.filters.add(filter);
				}
			}
		}

		return this.filters;
	}

	public IFilter getActiveFilter() {
		return (IFilter) this.activeFilter;
	}
	
	public String getAssetFolder(IFilter filter) {		
		return "";
	}
}
