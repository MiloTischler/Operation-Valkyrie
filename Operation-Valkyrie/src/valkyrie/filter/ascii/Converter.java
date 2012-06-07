package valkyrie.filter.ascii;

import java.util.Vector;

import org.opencv.core.Mat;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

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


	private Paint paint;
	private int fontsize = 5;

	public Converter() {
		
		this.paint = new Paint();

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

	public Bitmap grayscaleToASCII(Mat gray, int[] LUT) {
		int width = gray.width() / this.fontsize;
		int height = gray.height() / this.fontsize;
		
		String textLine = new String();

		Bitmap mybitmap = Bitmap.createBitmap(gray.width(), gray.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(gray, gray, new Size(width, height));
		
		Imgproc.cvtColor(gray, gray, Imgproc.COLOR_RGB2HSV, 4);
		
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

				textLine += (char) LUT[(int) gray.get(n, m)[2]];
				canvas.drawText(textLine, hightPos, widthPos, this.paint);

				widthPos += this.fontsize;
			}
			hightPos += this.fontsize;
		}

		gray.release();
		
		return mybitmap;
	}

}