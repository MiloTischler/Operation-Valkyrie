package valkyrie.filter.grayscale;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import valkyrie.filter.FilterAssets;
import valkyrie.filter.FilterCaptureFormat;
import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.IFilter;
import valkyrie.main.R;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

public class Grayscale implements IFilter {
	private static final String TAG = "Grayscale";

	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun) {

	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		return null;
	}

	public Bitmap manipulatePreviewImage(Mat bitmapMat) {
		return this.toGrayscale(bitmapMat);
	}

	public Bitmap manipulateImage(Mat bitmapMat) {
		return this.toGrayscale(bitmapMat);
	}

	public int getFilterCaptureFormat() {
		return FilterCaptureFormat.Grey;
	}

	public TableLayout getUIElements(Activity mainActivity) {
		final LayoutInflater inflater = (LayoutInflater) mainActivity
				.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
		
		return (TableLayout) inflater.inflate(R.layout.greyscale, null);
	}

	private Bitmap toGrayscale(Mat bitmapMat) {
		if (bitmapMat == null) {
			Log.e(TAG, "Bitmap Mat is null");
		}

		Bitmap bitmap = Bitmap.createBitmap(bitmapMat.cols(), bitmapMat.rows(), Bitmap.Config.ARGB_8888);

		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_GRAY2RGBA, 4);

		Utils.matToBitmap(bitmapMat, bitmap);

		bitmapMat.release();

		return bitmap;
	}

}
