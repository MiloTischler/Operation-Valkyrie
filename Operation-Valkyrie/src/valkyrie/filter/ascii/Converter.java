package valkyrie.filter.ascii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import java.lang.Math;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

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
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

public class Converter {

	public Converter() {
	}

	public Bitmap bitmapToGrayScale(Bitmap bmpOriginal) {
		// TODO dont forget to recycle and find better way for grayscale
		// ...
		Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(),
				bmpOriginal.getHeight(), Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;

	}

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

	public void asciiTextToImage(Vector<String> imageText) {
		// get right size and dont save bmp
		int fontsize = 5;
		// font options go here
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);

		String line = imageText.get(0);
		Rect rect = new Rect();
		paint.getTextBounds(line, 0, line.length(), rect);
		int width = fontsize * line.length();
		int hight = fontsize * imageText.size();
		Log.d("valkyrie", "Image size:" + width + " | " + hight);
		Bitmap mybitmap = Bitmap.createBitmap(width, hight,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);

		int hightPos = fontsize;
		int widthPos = fontsize;
		String chara = "";
		char[] lineArray;
		for (int i = 0; i < imageText.size(); i++) {
			lineArray = imageText.get(i).toCharArray();
			widthPos = 0;
			for (char string : lineArray) {
				chara = "";
				chara += string;
				c.drawText(chara, widthPos, hightPos, paint);
				// Log.d("valkyrie", "Image size:" + widthPos + " | " +
				// hightPos);
				widthPos += fontsize;
			}
			hightPos += fontsize;
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("valkyrie", "finish");

	}

	public Mat toGrayscale(Mat bitmapMat) {

		Imgproc.cvtColor(bitmapMat, bitmapMat, Imgproc.COLOR_BGR2GRAY);
		return bitmapMat;
	}

	Scalar black = new Scalar(255, 255, 255);
	static final int fontsize = 8;

	public Bitmap grayScale8BitToAsciiPrieview(Mat gray, int[] LUT) {
		int remberWidth = gray.width();
		int remberHeight = gray.height();
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);
		int width = remberWidth / fontsize;
		int height = remberHeight / fontsize;
		String textLine;
		Bitmap mybitmap = Bitmap.createBitmap(gray.width(), gray.height(),
				Bitmap.Config.RGB_565);
		Imgproc.resize(gray, gray, new Size(width, height));
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);
		int hightPos = fontsize;
		int widthPos = 0;
		int m, n = 0;
		for (int i = 0; i < width; i++) {
			widthPos = 0;
			for (int j = 0; j < height; j++) {
				textLine = "";
				m = i;
				n = j;
				textLine += (char) LUT[(int) gray.get(n, m)[0]];
				c.drawText(textLine, hightPos, widthPos, paint);

				widthPos += fontsize;
			}
			hightPos += fontsize;
		}
		gray.release();
		return mybitmap;
	}

	public Bitmap lol(Mat gray, int[] LUT) {
		String textLine;
		int fontsize = 5;
		// font options go here
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);

		int width = gray.width() - (gray.width() % fontsize);
		int height = gray.height() - (gray.height() % fontsize);
		Bitmap mybitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);
		int hightPos = fontsize;
		int widthPos = 0;
		Mat sub = null;
		Scalar sca = null;
		int m, n = 0;
		for (int i = 0; i < (width - fontsize); i = i + fontsize) {
			widthPos = 0;
			for (int j = 0; j < (height - fontsize); j = j + fontsize) {
				textLine = "";
				m = i;
				n = j;
				if (gray.get(n, m) != null) {
					sub = gray.submat(j, j + fontsize, i, i + fontsize);
					sca = Core.mean(sub);
					textLine += (char) LUT[(int) sca.val[0]];
					c.drawText(textLine, hightPos, widthPos, paint);
				}
				widthPos += fontsize;
			}
			hightPos += fontsize;
		}
		gray.release();
		return mybitmap;
	}

	public Bitmap grayScaleToAsciiPrieview(Bitmap gray, int[] LUT) {
		String textLine;

		Log.d("valkyrie", "start");
		int fontsize = 7;
		// font options go here
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);

		int width = gray.getWidth() - (gray.getWidth() % fontsize);
		int hight = gray.getHeight() - (gray.getHeight() % fontsize);
		Bitmap mybitmap = Bitmap.createBitmap(width, hight,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);

		int hightPos = fontsize;
		int widthPos = 0;
		int pixelSum = 0;
		for (int i = 0; i < (width - fontsize); i = i + fontsize) {
			widthPos = 0;
			for (int j = 0; j < (hight - fontsize); j = j + fontsize) {
				pixelSum = 0;
				textLine = "";
				for (int x = i; x < (i + fontsize); x++) {
					for (int y = j; y < (j + fontsize); y++) {
						pixelSum += Color.red(gray.getPixel(x, y));
					}
				}

				textLine += (char) LUT[Math.round((float) pixelSum
						/ ((float) fontsize * (float) fontsize))];
				c.drawText(textLine, hightPos, widthPos, paint);
				widthPos += fontsize;
			}
			hightPos += fontsize;
		}

		// saving shouldnt be done here
		// String path = Environment.getExternalStorageDirectory().toString();
		// OutputStream fOut = null;
		// File file = new File(path, "lol2.jpg");
		// try {
		// fOut = new FileOutputStream(file);
		// mybitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		// fOut.flush();
		// fOut.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		Log.d("valkyrie", "finish");
		return mybitmap;
	}

	public void colorToAsciiPrieview(Bitmap gray, int[] LUT, Bitmap color) {
		String textLine;

		Log.d("valkyrie", "start");
		int fontsize = 7;
		// font options go here
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(fontsize);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setTextAlign(Align.CENTER);

		int width = gray.getWidth() - (gray.getWidth() % fontsize);
		int hight = gray.getHeight() - (gray.getHeight() % fontsize);
		Bitmap mybitmap = Bitmap.createBitmap(width, hight,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(mybitmap);
		c.drawColor(Color.WHITE);

		int hightPos = fontsize;
		int widthPos = 0;
		int pixelSum = 0;
		int redSum = 0;
		int greenSum = 0;
		int blueSum = 0;
		for (int i = 0; i < (width - fontsize); i = i + fontsize) {
			widthPos = 0;
			for (int j = 0; j < (hight - fontsize); j = j + fontsize) {
				pixelSum = 0;
				blueSum = 0;
				greenSum = 0;
				redSum = 0;
				textLine = "";
				for (int x = i; x < (i + fontsize); x++) {
					for (int y = j; y < (j + fontsize); y++) {
						pixelSum += Color.red(gray.getPixel(x, y));
						redSum += Color.red(color.getPixel(x, y));
						greenSum += Color.green(color.getPixel(x, y));
						blueSum += Color.blue(color.getPixel(x, y));
					}
				}

				textLine += (char) LUT[Math.round((float) pixelSum
						/ ((float) fontsize * (float) fontsize))];
				redSum = Math.round((float) redSum
						/ ((float) fontsize * (float) fontsize));
				greenSum = Math.round((float) greenSum
						/ ((float) fontsize * (float) fontsize));
				blueSum = Math.round((float) blueSum
						/ ((float) fontsize * (float) fontsize));
				paint.setColor(Color.rgb(redSum, greenSum, blueSum));
				c.drawText(textLine, hightPos, widthPos, paint);
				widthPos += fontsize;
			}
			hightPos += fontsize;
		}

		// saving shouldnt be done here ..
		// String path = Environment.getExternalStorageDirectory().toString();
		// OutputStream fOut = null;
		// File file = new File(path, "lol3.jpg");
		// try {
		// fOut = new FileOutputStream(file);
		// mybitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		// fOut.flush();
		// fOut.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		Log.d("valkyrie", "finish");
	}

}