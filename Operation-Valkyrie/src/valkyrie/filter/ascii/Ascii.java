package valkyrie.filter.ascii;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
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
	public Ascii() {
		// //test bitmap
		// int[] colors = new int[10 * 10];
		// for(int i = 0; i < 100; i++){
		// colors[i] = Color.YELLOW;
		// if((i % 10) == 0)
		// colors[i] = Color.RED;
		// }
		// //this.bm = Bitmap.createBitmap(colors,10, 10, Bitmap.Config.RGB_565);
		//
		//
		// FileInputStream in;
		// BufferedInputStream buf;
		// String path = Environment.getExternalStorageDirectory().toString();
		// try {
		// in = new FileInputStream( path + "/oruxmaps/cursors/neodraig2.png");
		// buf = new BufferedInputStream(in);
		// this.bm = BitmapFactory.decodeStream(buf);
		// if (in != null) {
		// in.close();
		// }
		// if (buf != null) {
		// buf.close();
		// }
		// } catch (Exception e) {
		// Log.e("Error reading file", e.toString());
		// }
		//
		//
		//
		// Log.d("valkyrie", this.bm.getPixel(1, 1) + " lol");
		//
		// this.fonts = new Vector<Font>();
		// for (String name : this.fontsList) {
		// this.fonts.add(new Font(name, true));
		// }
		//
		// this.activeFont = this.fonts.get(0);
		//
		// this.converter = new Converter();
		// manipulatePreviewImage(this.bm);
	}

	public void manipulatePreviewImage(Bitmap bitmap) {
		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2, this.activeFont.getLUT()));
	}

	public void manipulateImage(Bitmap bitmap) {
		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2, this.activeFont.getLUT()));

	}

	/**
	 * Returns the defined UI-Elements for the Options Panel.
	 */
	public HashMap<Integer, Vector<RelativeLayout>> getUIElements(Activity mainActivity) {
		
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
