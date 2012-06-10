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

/**
 * Filter Interface 
 */
public interface IFilter {
	
	/**
	 * Setup methode, called at every programm start for filter intern initializations
	 * 
	 * @param FilterInternalStorage filterInternalStorage
	 * @param FilterAssets filterAssets
	 * @param Boolean firstRun
	 */
	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun);

	/**
	 * Returns the short name of the filter
	 * 
	 * @return String name
	 */
	public String getName();

	/**
	 * Returns the icon of the filter
	 * 
	 * @return Bitmap icon
	 */
	public Bitmap getIcon();
	
	/**
	 * Manipulates the image for the preview (Fast implementation!)
	 * 
	 * @param Mat bitmap
	 * @return Bitmap, manipulated image
	 */
	public Bitmap manipulatePreviewImage(Mat bitmap);
	
	/**
	 * Manipulates the image for taking a picture
	 * 
	 * @param Mat bitmap
	 * @return Bitmap, manipulated image
	 */
	public Bitmap manipulateImage(Mat bitmap);
	
	/**
	 * Returns the OpenCV video capture format the filter requests
	 * 
	 * @return int, OpenCV (see OpenCV Highgui class) video capture format the filter requests
	 */
	public int getFilterCaptureFormat();
	
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
