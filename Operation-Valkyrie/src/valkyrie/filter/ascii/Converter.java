package valkyrie.filter.ascii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import valkyrie.filter.IFilter;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */

public class Converter {
	
	
	public Converter() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Bitmap bitmapToGrayScale(Bitmap bmpOriginal) {  
	    //TODO dont forget to recycle and find better way for grayscale
		// ...
	    Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(),bmpOriginal.getHeight(), Bitmap.Config.RGB_565);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;

	}
	
	public Vector<String> grayScaleToAsciiText(Bitmap gray){
		Vector<String> textVec = new Vector<String>();
		String textLine;
		for (int i = 0; i < gray.getHeight(); i++) {
			textLine = "";
			for (int j = 0; j < gray.getWidth(); j++) {
				//here goes LUT
				 textLine += (char)Color.red(gray.getPixel(j, i));	
			}
			textVec.add(textLine);
		}
		return textVec;
		
		
	}
	
	public void asciiTextToImage(Vector<String> imageText){
		// get right size and dont save bmp
		int fontsize = 20;
		//font options go here
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		
		String line = imageText.get(0);
		Rect rect = new Rect();
		paint.getTextBounds(line, 0, line.length(), rect);
		
		
			//Log.d("valkyrie",  image);
		Bitmap mybitmap = Bitmap.createBitmap(rect.width(), fontsize * imageText.size(), Bitmap.Config.RGB_565);
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);
		
		int hight = fontsize;
		for(int i = 0; i < imageText.size(); i++){
			c.drawText(imageText.get(i),0,hight - 1,paint);
			hight += fontsize;
		}
		
		
		
		// saving shouldnt be done here
		String path = Environment.getExternalStorageDirectory().toString();
		OutputStream fOut = null;
		File file = new File(path, "lol.jpg");
		try {
			fOut = new FileOutputStream(file);
			mybitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("valkyrie",  "finish");	


//		MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());

	}
	
	private int[] lut;
}