package valkyrie.filter.grayscale;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
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

	public Bitmap manipulatePreviewImage(Bitmap bitmap) {
		return this.toGrayscale(bitmap);
	}

	public Bitmap manipulateImage(Bitmap bitmap) {
		if(bitmap == null) {
			Log.d("OMGOMG", "OMFG is null..");
		} else {
			Log.d("OMGOMG", "OMFG is NOT null..");
		}
		
		return bitmap;
	}

	public TableLayout getUIElements(Activity mainActivity) {
		return null;
	}
	
	private Bitmap toGrayscale(Bitmap bitmap) {
		Mat bitmapMat = Utils.bitmapToMat(bitmap);		
		
		Bitmap.Config config = bitmap.getConfig();
		
		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_GRAY2RGBA, 4);
		
		Utils.matToBitmap(bitmapMat, bitmap);
		
		return bitmap;
	}

}
