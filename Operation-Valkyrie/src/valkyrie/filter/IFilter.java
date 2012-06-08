package valkyrie.filter;

import org.opencv.core.Mat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.TableLayout;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public interface IFilter {
	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun);

	public String getName();

	public Bitmap getIcon();
	
	public Bitmap manipulatePreviewImage(Mat bitmap);
	
	public Bitmap manipulateImage(Mat bitmap);
	
	public int getFilterCaptureFormat();
	
	public void initOptions();
	/**
	 * Retrieves the filter specific options panel.
	 * 
	 * @param mainActivity
	 *            The main activity of the program. Needed for layout inflation.
	 * @return RelativeLayout a RelativeLayout containing all UI-Elements, which can be directly embedded into the
	 *         main.xml layout.
	 */
	public TableLayout getUIElements(Activity mainActivity);
}
