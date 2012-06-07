package valkyrie.filter.ascii;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

import org.opencv.core.Mat;

import valkyrie.main.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import valkyrie.filter.FilterAssets;
import valkyrie.filter.FilterCaptureFormat;
import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.IFilter;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class Ascii implements IFilter {

	private Bitmap bm;
	private Font activeFont;
	private Converter converter;
	
	private Vector<Font> fonts;
	private String fontsList[] = { "test1", "test2" };

	/**
	 * TODO: Sry aber bei mir wirft des a nullpointer exception wenn ich alle filter für den
	 * filtermanager instancier, lg milo
	 */
	public Ascii(){
		this.fonts = new Vector<Font>();
		for (String name : this.fontsList) {
			this.fonts.add(new Font(name, true));
		}
		
		this.activeFont = this.fonts.get(0);
		
		this.converter = new Converter();

	}
	
	public Bitmap manipulatePreviewImage(Mat bitmapMat) {
		
		return null;
//		Bitmap bm2 = this.converter.bitmapToGrayScale(bitmap);
//		this.converter.grayScaleToAsciiPrieview(bm2, this.activeFont.getLUT());
//		this.converter.colorToAsciiPrieview(bm2, this.activeFont.getLUT(), bitmap);
	}

	public Bitmap manipulateImage(Mat bitmapMat) {
//		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
//		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2, this.activeFont.getLUT()));
		
		return null;
	}
	
	public int getFilterCaptureFormat() {
		return FilterCaptureFormat.Grey;
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

		return (TableLayout) inflater.inflate(R.layout.ascii, null);
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		return null;
	}

	public void setup(FilterInternalStorage filterInternalStorage, FilterAssets filterAssets, Boolean firstRun) {

	}
	
	public void test(){
        FileInputStream in;
        BufferedInputStream buf;
        String path = Environment.getExternalStorageDirectory().toString();
        try {
       	    //in = new FileInputStream( path + "/oruxmaps/cursors/neodraig2.png");
        	in = new FileInputStream( path + "/Valkyrie/Screenshots/Screenshot_2012-06-01-17-00-21.png");
        	buf = new BufferedInputStream(in);
            this.bm = BitmapFactory.decodeStream(buf);
            if (in != null) {
         	in.close();
            }
            if (buf != null) {
         	buf.close();
            }
        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }
//		manipulatePreviewImage(this.bm);
//		manipulateImage(this.bm);
	}
}
