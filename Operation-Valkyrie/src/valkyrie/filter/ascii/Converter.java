package valkyrie.filter.ascii;

import java.util.Vector;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * � Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

public class Converter {

	private Paint paint;
	private int fontsize = 8;
	private Canvas canvas;

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
		int width = color.width() / this.fontsize; //final ?
		int height = color.height() / this.fontsize;
		
		Bitmap mybitmap = Bitmap.createBitmap(color.width(), color.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(color, color, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		
		this.canvas.drawColor(Color.WHITE);
		
		int hightPos = this.fontsize;
		int widthPos = 0;

		for (int i = 0; i < width; i++) {
			widthPos = 0;
			for (int j = 0; j < height; j++) {
			this.paint.setColor(Color.rgb((int) color.get(j, i)[0], (int) color.get(j, i)[1], (int) color.get(j, i)[2]));
				
				this.canvas.drawText(String.valueOf((char) LUT[(int) color.get(j, i)[0]]), hightPos, widthPos, this.paint);

				widthPos += this.fontsize;
			}
			hightPos += this.fontsize;
		}
		color.release();
		
		return mybitmap;
	}

	public Bitmap grayscaleToASCII(Mat gray, int[] LUT) {
		int width = gray.width() / this.fontsize;
		int height = gray.height() / this.fontsize;
		
		String textLine = new String();

		//		Imgproc.cvtColor(gray, gray, Imgproc.COLOR_RGB2HSV, 4);
		
//		Imgproc.Canny(gray, gray, 1f, 1f);
		
		Bitmap mybitmap = Bitmap.createBitmap(gray.width(), gray.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(gray, gray, new Size(width, height));
		
		Canvas canvas = new Canvas(mybitmap);
		canvas.drawColor(Color.WHITE);
		
		int hightPos = this.fontsize;
		int widthPos = 0;
		int m, n = 0;
		for (int i = 0; i < width; i++) {
			widthPos = 0;
			for (int j = 0; j < height; j++) {
				textLine = "";
				m = i;
				n = j;
				textLine += (char) LUT[(int) gray.get(n, m)[0]];
				canvas.drawText(textLine, hightPos, widthPos, this.paint);

				widthPos += this.fontsize;
			}
			hightPos += this.fontsize;
		}
		gray.release();
		
		return mybitmap;
	}
}