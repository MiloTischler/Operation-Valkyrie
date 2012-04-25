package valkyrie.filter;

import java.util.ArrayList;

import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import valkyrie.filter.nofilter.NoFilter;

import android.content.Context;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class FilterCamera extends VideoCapture {
	private static final String TAG = "FilterCamera";

	private Context context = null;
	private IFilter activeFilter = new NoFilter();
	private ArrayList<IFilter> filters = new ArrayList<IFilter>();

	public FilterCamera(Context context, Integer filterArray) {
		super(Highgui.CV_CAP_ANDROID);

		Log.i(TAG, "Initialized opencv camera");

		this.context = context;

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
					filter.setup(new FilterAssets(filter, context), this.isFirstRun());

					Log.i(TAG, "Load and setup filter: " + filter.getClass().getName());

					this.filters.add(filter);
				}
			}
		}
	}

	public void setActiveFilter(IFilter filter) {
		for (IFilter storedFilter : this.filters) {
			if (filter.getClass().getName() == storedFilter.getClass().getName()) {
				Log.i(TAG, "Successfully changed active filter to: " + storedFilter.getClass().getName());

				this.activeFilter = storedFilter;
			}
		}
	}

	public IFilter getActiveFilter() {
		return this.activeFilter;
	}

	public ArrayList<IFilter> getFilterList() {
		return this.filters;
	}

	private Boolean isFirstRun() {
		// TODO: implement via shared preferences

		return true;
	}
}
