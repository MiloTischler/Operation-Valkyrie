package valkyrie.filter.ascii;

import java.util.Vector;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;
import org.opencv.core.Scalar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Region;
import android.graphics.Typeface;
import android.util.Log;

/**
 * 
<<<<<<< HEAD
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
=======
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
>>>>>>> 3f2bf366ee02e6e3b794cc91ca23a413fcb8cde5
 * 
 */

public class Converter {
	private static final String TAG = "Converter";


	private final Paint paint;
	private int fontsize = 8;
	private final Canvas canvas;

	public Converter() {
		
		this.paint = new Paint();
		this.canvas = new Canvas();

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(this.fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);
	}



	@Deprecated
	public Vector<String> grayScaleToAsciiText(Bitmap gray, int[] LUT) {
		Vector<String> textVec = new Vector<String>();
		String textLine;
		for (int i = 0; i < gray.getHeight(); i++) {
			textLine = "";
			for (int j = 0; j < gray.getWidth(); j++) {
				// here goes LUT
				textLine += (char) LUT[Color.red(gray.getPixel(j, i))];
			}
			textVec.add(textLine);
		}
		return textVec;

	}
	
	public Bitmap colorToASCII(Mat color, int[] LUT) {		
		final int width = color.width() / this.fontsize; 
		final int height = color.height() / this.fontsize;
		
		Bitmap mybitmap = Bitmap.createBitmap(color.width(), color.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(color, color, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		
		this.canvas.drawColor(Color.WHITE);
		
		int widthPos = 0;
		int heightPos = this.fontsize;

		for (int i = 0; i < width; i++) {

			widthPos = 0;
			
			for (int j = 0; j < height; j++) {
				this.paint.setColor(Color.rgb((int) color.get(j, i)[0], (int) color.get(j, i)[1], (int) color.get(j, i)[2]));				
				this.canvas.drawText(String.valueOf((char) LUT[(int) color.get(j, i)[0]]), heightPos, widthPos, this.paint);

				widthPos += this.fontsize;
			}

			heightPos += this.fontsize;
		}

		color.release();
		
		return mybitmap;
	}
	
	public Bitmap fastColorToASCII(Mat color, int[] LUT) {	
		Mat colorOrgSize = new Mat(color.width(), color.height(), color.type());
		color.copyTo(colorOrgSize);
		
		final int width = color.width() / this.fontsize; 
		final int height = color.height() / this.fontsize;
		
		Bitmap mybitmap = Bitmap.createBitmap(color.width(), color.height(), Bitmap.Config.ARGB_8888);
		Imgproc.resize(color, color, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		
		this.canvas.drawColor(Color.WHITE);
		
		float[] asciiPositions = new float[(width * height) * 2];
		char[] asciiChars = new char[(width * height)];
		
		int pixel = 0;
		int position = 0;

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {				
				asciiChars[pixel] = (char) LUT[(int) color.get(y, x)[0]];
				
				asciiPositions[position] = (x * fontsize);
				asciiPositions[position + 1] = (y * fontsize) + fontsize;
				
				pixel++;
				position += 2;
			}
		}
		
		this.canvas.drawPosText(asciiChars, 0, asciiChars.length, asciiPositions, paint);
		
		Mat test = Utils.bitmapToMat(mybitmap);
		
		Imgproc.cvtColor(test, test, Imgproc.COLOR_RGBA2RGB, 4);
		
		Core.convertScaleAbs(test, test, 1/255f);

		Log.d("TESTA", "ME TESTA: " + test.get(0,0)[0] + " " + test.get(10, 10)[0]);
		
		colorOrgSize.convertTo(colorOrgSize, test.type());
		
		Log.d("TESTA", "ME COMPERA: test[t:" + test.type() +" - w:" + test.width() + " - h:" + test.height() + "]");
		Log.d("TESTA", "ME COMPERA: colorOrgSize[t:" + colorOrgSize.type() +" - w:" + colorOrgSize.width() + " - h:" + colorOrgSize.height() + "]");
		
		Core.multiply(colorOrgSize, test, colorOrgSize);
		
		color.release();
		
		return mybitmap;
	}

	public Bitmap grayscaleToASCII(Mat gray, int[] LUT) {
		final int width = gray.width() / this.fontsize; 
		final int height = gray.height() / this.fontsize;
		
		Bitmap mybitmap = Bitmap.createBitmap(gray.width(), gray.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(gray, gray, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		
		this.canvas.drawColor(Color.WHITE);
		
		float[] asciiPositions = new float[(width * height) * 2];
		char[] asciiChars = new char[(width * height)];
		
		int pixel = 0;
		int position = 0;

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {				
				asciiChars[pixel] = (char) LUT[(int) gray.get(y, x)[0]];
				
				asciiPositions[position] = (x * fontsize);
				asciiPositions[position + 1] = (y * fontsize) + fontsize;
				
				pixel++;
				position += 2;
			}
		}

		
		this.canvas.drawPosText(asciiChars, 0, asciiChars.length, asciiPositions, paint);
		gray.release();
		
		return mybitmap;
	}

}