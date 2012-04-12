package valkyrie.filter;

import java.util.ArrayList;

import valkyrie.filter.nofilter.NoFilter;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class FilterManager {
	private static FilterManager instance = null;
	
	private ArrayList<IFilter> filters = new ArrayList<IFilter>();
	
	private IFilter activeFilter = null;
	
	private FilterManager() {
		this.activeFilter = new NoFilter();
	}
	
	public static FilterManager getInstance() {
		if(instance == null) {
			instance = new FilterManager();
		}
		
		return instance;
	}
	
	public void changeFilter(IFilter filter) {
		this.activeFilter = filter;
	}
	
	public ArrayList<IFilter> getFilterList() {
		return this.filters;
	}
	
	public IFilter getActiveFilter() {
		return (IFilter) this.activeFilter;
	}
}
