package valkyrie.filter.ascii;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import valkyrie.filter.FilterAssets;
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
	private String fontsList[] = {
			"test1",
			"test2"
	};
	
	public Ascii(){
		//test bitmap
		int[] colors = new int[10 * 10];
		for(int i = 0; i < 100; i++){
			colors[i] = Color.YELLOW;
			if((i % 10) == 0)
				colors[i] = Color.RED;
		}
		//this.bm = Bitmap.createBitmap(colors,10, 10, Bitmap.Config.RGB_565);
        

        FileInputStream in;
        BufferedInputStream buf;
        String path = Environment.getExternalStorageDirectory().toString();
        try {
       	    in = new FileInputStream( path + "/oruxmaps/cursors/neodraig2.png");
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
		
		
		
		Log.d("valkyrie", this.bm.getPixel(1, 1) + " lol");
		
		this.fonts = new Vector<Font>();
		for (String name : this.fontsList) {
			this.fonts.add(new Font(name, true));
		}
		
		this.activeFont = this.fonts.get(0);
		
		this.converter = new Converter();
		manipulatePreviewImage(this.bm);
		
	
		
	}

	public void manipulatePreviewImage(Bitmap bitmap) {
		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2, this.activeFont.getLUT()));
	}

	public void manipulateImage(Bitmap bitmap) {
		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2, this.activeFont.getLUT()));
		
	}

	public Vector<Object> getUIElements() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setup(FilterAssets filterAssets, Boolean firstRun) {
		// TODO Auto-generated method stub
		
	}
}
