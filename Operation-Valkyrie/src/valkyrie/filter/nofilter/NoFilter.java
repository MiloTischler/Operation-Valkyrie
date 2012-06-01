package valkyrie.filter.nofilter;

import java.util.HashMap;
import java.util.Vector;

import android.graphics.Bitmap;
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

	public HashMap<Integer, Vector<RelativeLayout>> getUIElements() {
		return new HashMap<Integer, Vector<RelativeLayout>>();
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
