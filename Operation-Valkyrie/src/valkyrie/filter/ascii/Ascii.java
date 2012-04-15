package valkyrie.filter.ascii;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import valkyrie.filter.IFilter;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public class Ascii implements IFilter {
	
	private Bitmap bm;
	
	
	private Converter converter;
	
	public Ascii(){
		//test bitmap
		int[] colors = new int[10 * 10];
		for(int i = 0; i < 100; i++){
			colors[i] = Color.YELLOW;
			if((i % 10) == 0)
				colors[i] = Color.BLUE;
		}
		this.bm = Bitmap.createBitmap(colors,10, 10, Bitmap.Config.RGB_565);
		Log.d("valkyrie", this.bm.getPixel(1, 1) + " lol");
		
		
		this.converter = new Converter();
		manipulatePreviewImage(this.bm);
	}

	public void manipulatePreviewImage(Bitmap bitmap) {
		Bitmap bm2 = this.converter.bitmapToGrayScale(this.bm);
		Log.d("valkyrie",  bm2.getPixel(0, 0) +  " lol2");	
		this.converter.asciiTextToImage(this.converter.grayScaleToAsciiText(bm2));
	}

	public void manipulateImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		
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
	
}
