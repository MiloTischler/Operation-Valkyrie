package valkyrie.filter.nofilter;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import valkyrie.main.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
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

	public Bitmap manipulatePreviewImage(Mat bitmapMat) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(bitmapMat, bitmap);
		bitmapMat.release();
		
		return bitmap;
	}

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
				.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
	
		return (TableLayout) inflater.inflate(R.layout.nofilter, null);
	}
	
	public int getFilterCaptureFormat() {
		return Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA;
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
