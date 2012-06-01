package valkyrie.filter.nofilter;

import valkyrie.main.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import valkyrie.filter.FilterAssets;
import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.IFilter;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class NoFilter implements IFilter {

	public void manipulatePreviewImage(Bitmap bitmap) {

	}

	public void manipulateImage(Bitmap bitmap) {

	}

	/**
	 * Returns the defined UI-Elements for the Options Panel as whole RelativeLayout.
	 * 
	 * @param mainActivity
	 *            Activity, the main activity of the Program. Gives us access to the LayoutInflater.
	 */
	public RelativeLayout getUIElements(Activity mainActivity) {

		final LayoutInflater inflater = (LayoutInflater) mainActivity
				.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
	
		return (RelativeLayout) inflater.inflate(R.layout.nofilter, null);
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		return null;
	}

	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun) {

	}
}
