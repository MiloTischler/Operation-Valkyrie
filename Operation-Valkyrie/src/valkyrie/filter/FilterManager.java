package valkyrie.filter;

import java.util.ArrayList;

import valkyrie.filter.nofilter.NoFilter;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.preview.CameraPreviewViewCV;

import android.content.Context;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

/**
 * Manages the various filters and initializes the filters
 */
public class FilterManager {
	private static final String TAG = "FilterManager";

	private Context context = null;
	private CameraPreviewViewCV cameraPreview = null;
	private IFilter activeFilter = new NoFilter();
	private ArrayList<IFilter> filters = new ArrayList<IFilter>();

	/**
	 * Setup the filters by their names from the filterArray
	 * 
	 * @param Context context
	 * @param Integer filterArray
	 * @param CameraPreviewViewCV cameraPreview
	 */
	public FilterManager(Context context, Integer filterArray, CameraPreviewViewCV cameraPreview) {
		Log.i(TAG, "Initialized opencv camera");

		this.context = context;
		this.cameraPreview = cameraPreview;

		this.filters.clear();

		String[] filterNames = this.context.getResources().getStringArray(filterArray);

		for (String filterName : filterNames) {
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
				if (filter != null) {
					filter.setup(new FilterInternalStorage(filter, context), new FilterAssets(filter, context),
							this.isFirstRun());

					Log.i(TAG, "Load and setup filter: " + filter.getClass().getName());

					this.filters.add(filter);
				}
			}
		}
	}

	/**
	 * Sets the active filter to the given one. Also informs the cameraPreview and the LayoutManager
	 * 
	 * @param IFilter filter
	 */
	public void setActiveFilter(IFilter filter) {
		for (IFilter storedFilter : this.filters) {
			if (filter.getClass().getName().equals(storedFilter.getClass().getName()))  {
				Log.i(TAG, "Successfully changed active filter to: " + storedFilter.getClass().getName());
				this.activeFilter = storedFilter;

				this.cameraPreview.setFilter(this.activeFilter);
				
				// notify UI about filter change
				LayoutManager.getInstance().notifyUI(this.activeFilter);
			}
		}
	}

	/**
	 * Returns the active filter
	 * 
	 * @return IFilter
	 */
	public IFilter getActiveFilter() {	
		return this.activeFilter;
	}

	/**
	 * Returns the list of all filters
	 * 
	 * @return ArrayList<IFilter>
	 */
	public ArrayList<IFilter> getFilterList() {
		return this.filters;
	}

	private Boolean isFirstRun() {
		// TODO: implement via shared preferences for filter setup methode
		return true;
	}
}
