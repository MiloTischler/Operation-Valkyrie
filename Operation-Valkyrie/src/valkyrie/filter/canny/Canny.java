package valkyrie.filter.canny;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;
import org.opencv.*;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import valkyrie.filter.FilterAssets;
import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.IFilter;
import valkyrie.main.R;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

public class Canny implements IFilter {
	private static final String TAG = "Canny";

	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun) {

	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		return null;
	}

	public Bitmap manipulatePreviewImage(Mat bitmapMat) {
		Imgproc.Canny(bitmapMat, bitmapMat, 1f, 1f);
		
		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_GRAY2RGBA, 4);

		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(bitmapMat, bitmap);
		bitmapMat.release();

		return bitmap;
	}

	public Bitmap manipulateImage(Mat bitmapMat) {	
		Imgproc.Canny(bitmapMat, bitmapMat, 1f, 1f);
		
		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_GRAY2RGBA, 4);

		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(bitmapMat, bitmap);
		bitmapMat.release();

		return bitmap;
	}

	public int getFilterCaptureFormat() {
		return Highgui.CV_CAP_ANDROID_GREY_FRAME;
	}

	public TableLayout getUIElements(Activity mainActivity) {
		final LayoutInflater inflater = (LayoutInflater) mainActivity
				.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);

		return (TableLayout) inflater.inflate(R.layout.canny, null);
	}

}
