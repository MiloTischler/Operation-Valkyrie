/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

package valkyrie.filter.nofilter;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import valkyrie.filter.FilterAssets;
import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.IFilter;
import valkyrie.main.R;
import valkyrie.ui.MainActivity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.TableLayout;

/**
 * A Filter for the Camera.
 * The Camera Image is processed trough this Filter when active.
 * 
 * In this Case no processing is done.
 */
public class NoFilter implements IFilter {

	/**
	 * Processes the PreviewImage and adds an effect.
	 * In this case no processing is done.
	 * 
	 * @param bitmapMat
	 *            Mat (OpenCV), Matrix representation of the image to be processed.
	 * @return Bitmap the manipulated Bitmap.
	 */
	public Bitmap manipulatePreviewImage(Mat bitmapMat) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(bitmapMat, bitmap);
		bitmapMat.release();

		return bitmap;
	}

	/**
	 * Processes the final Image and adds an effect.
	 * In this case no processing is done.
	 * 
	 * @param bitMap
	 *            Mat (OpenCV), Matrix representation of the image to be processed.
	 * @return Bitmap the manipulated Bimap.
	 */
	public Bitmap manipulateImage(Mat bitmapMat) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(bitmapMat, bitmap);
		bitmapMat.release();

		return bitmap;
	}

	/**
	 * Returns the defined UI-Elements for the Options Panel as whole RelativeLayout.
	 * 
	 * @param mainActivity
	 *            Activity, the main activity of the Program. Gives us access to the LayoutInflater.
	 */
	public TableLayout getUIElements(Activity mainActivity) {

		final LayoutInflater inflater = (LayoutInflater) mainActivity
				.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);

		return (TableLayout) inflater.inflate(R.layout.nofilter, null);
	}

	/**
	 * Returns the Format of the CameraImage.
	 * 
	 * @return int the ImageFormat of the Camera
	 */
	public int getFilterCaptureFormat() {
		return Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA;
	}

	/**
	 * Returns the Name of this Filter.
	 * 
	 * @return String the name of this Filter
	 */
	public String getName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns the Icon of this Filter.
	 * 
	 * @return
	 */
	public Bitmap getIcon() {
		return null;
	}

	/**
	 * Setup the storage for this Filter.
	 * 
	 * @param filterInternalStorage
	 *            FilterInternalStorage, object that manages the image storage for the Filter
	 * @param filterAssets
	 *            FilterAssets, manages any needed Files for the Filter
	 * @param firstRun
	 *            Boolean, defines whether its the first run of the app or not
	 */
	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun) {

	}
}
