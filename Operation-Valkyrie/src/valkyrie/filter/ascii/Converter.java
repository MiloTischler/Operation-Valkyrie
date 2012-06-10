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
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */


/**
 * 
 * Converts an Image to Ascii in color or grayscale mode
 * 
 * @author Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 *
 */
public class Converter {
	private static final String TAG = "Converter";
	
	private static final int FONT_SCALE = 5;

	private final Canvas canvas;
	private final Paint paint;
	
	private int fontsize = 8;
	private int forgroundcolor = Color.BLACK;
	private int backgroundcolor = Color.WHITE;

	public Converter() {
		this.paint = new Paint();
		this.canvas = new Canvas();

		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(this.fontsize);
		paint.setAntiAlias(false);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);
	}



	/**
	 * Converts grayscale image into text pixel by pixel 
	 * @param Bitmap
	 * @param int[] LUT look up table needs 256 entries for each color in an 8bit grayscale image 
	 * @return Vector<String> each entry represents a pixel line from the image
	 */
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
	
	/**
	 * converts color matrix into bitmap with ascii where fontszie defines the pixel matrix used for one letter
	 * @param Mat color
	 * @param int[] LUT
	 * @return bitmap
	 */
	public Bitmap colorToASCII(Mat color, int[] LUT) {		
		final int width = color.width() / this.fontsize; 
		final int height = color.height() / this.fontsize;
		
		Bitmap mybitmap = Bitmap.createBitmap(color.width(), color.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(color, color, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		this.canvas.drawColor(this.backgroundcolor);
		
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

	/**
	 * converts grayscale matrix into bitmap with ascii where fontszie defines the pixel matrix used for one letter
	 * @param Mat gray
	 * @param int[] LUT
	 * @return bitmap
	 */
	public Bitmap grayscaleToASCII(Mat gray, int[] LUT) {
		final int width = gray.width() / this.fontsize; 
		final int height = gray.height() / this.fontsize;
		Bitmap mybitmap = Bitmap.createBitmap(gray.width(), gray.height(), Bitmap.Config.RGB_565);
		Imgproc.resize(gray, gray, new Size(width, height));
		
		this.canvas.setBitmap(mybitmap);
		this.canvas.drawColor(this.backgroundcolor);
		
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
	
	/**
	 * sets foreground color
	 * @param int foregroundcolor
	 */
	public void setForegroundColor(int foregroundcolor) {
		this.forgroundcolor = foregroundcolor;
		
		paint.setColor(this.forgroundcolor);
	}
	
	/**
	 * sets background color
	 * @param int backgroundcolor
	 */
	public void setBackgroundcolor(int backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}
	
	/**
	 * sets fontsize plus 5
	 * @param int fontsize
	 */
	public void setFontSize(int fontsize) {
		this.fontsize = fontsize + FONT_SCALE;
	}

}