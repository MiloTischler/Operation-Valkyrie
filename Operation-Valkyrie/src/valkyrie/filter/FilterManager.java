package valkyrie.filter;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;

import valkyrie.filter.nofilter.NoFilter;
import valkyrie.main.R;

//TODO: filters need a way to access context

public class FilterManager {
	private static String tag = "FilterManager";
	
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
		Log.i(tag, "Changed filter to: " +  filter.getClass().getName());
		
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
				Log.e(tag, e.getMessage());
			} catch (InstantiationException e) {
				Log.e(tag, e.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e(tag, e.getMessage());
			} finally {
				if(filter != null) {
					Log.i(tag, "Added filter to list: " +  filter.getClass().getName());
					
					this.filters.add(filter);
				}
			}
		}

		return this.filters;
	}

	public IFilter getActiveFilter() {
		return (IFilter) this.activeFilter;
	}
}
