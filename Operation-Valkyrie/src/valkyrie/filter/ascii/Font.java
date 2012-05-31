package valkyrie.filter.ascii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

/**
 * The Font Class Manages the initialization of a font and holds the look up table for this font
 * 
 * 
 * 
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */

public class Font {

	private int[] LUT; // Look up Table index = color intensity; value = letter
	private String name; // font name
	
	
	public Font(String name, boolean install) {
		
		this.name = name;
		this.LUT = new int[256];
		if(install)
			initializeFont();
		else
			load();
	}

	
	/**
	 * Initializes Font for the first time with a simple algorithm
	 * Just takes the percentage of a letter and saves it in the LUT
	 * 
	 * 
	 * TODO cleanup file output and other stuff
	 * TODO later, implement better algorithms
	 */
	private void initializeFont() {
		String letter = "";
		Map<Integer, Double> intensityList = new HashMap<Integer, Double>();

		double min = 1, max = 0;
		for (int i = 0; i < 255; i++) {
			letter = "";
			letter += (char) i;
			int fontsize = 20;
			
			// font options go here
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.BLACK);
			paint.setTextSize(fontsize);
			paint.setAntiAlias(false);
			paint.setTypeface(Typeface.MONOSPACE);

			Rect rect = new Rect();
			paint.getTextBounds(letter, 0, 1, rect);
			// Log.d("valkyrie", letter);
			// Log.d("valkyrie", rect.width() + "|" + rect.height());
			// Log.d("valkyrie", image);
			if ((rect.width() != 0) && (rect.height() != 0)) {
				Bitmap mybitmap = Bitmap.createBitmap(25, 25,
						Bitmap.Config.RGB_565);
				Canvas c = new Canvas(mybitmap);
				c.drawColor(Color.WHITE);
				c.drawText(letter, 0, 20, paint);
				String path = Environment.getExternalStorageDirectory()
						.toString();
				OutputStream fOut = null;
				File file = new File(path + "/lol/", "lol" + i + ".jpg");
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
				int blackPixelCounter = 0;
				int imgPixels = mybitmap.getHeight() * mybitmap.getWidth();
				for (int y = 0; y < mybitmap.getHeight(); y++) {
					for (int x = 0; x < mybitmap.getWidth(); x++) {
						if (mybitmap.getPixel(x, y) != Color.WHITE) {
							blackPixelCounter++;
						}
					}
				}
				double intensity = (double) blackPixelCounter
						/ (double) imgPixels;
				// Log.d("valkyrie", intensity +" ");
				if (blackPixelCounter == 40) {
					intensityList.put(i, -2.0);
				} else {
					intensityList.put(i, intensity);
					if (intensity > max) {
						max = intensity;
					} else if (intensity < min) {
						min = intensity;
					}

				}

			} else {
				intensityList.put(i, -1.0);
			}
		}
		// Log.d("valkyrie", java.util.Arrays.toString(intensityList.));
		Log.d("valkyrie", max + "|" + min);
		Iterator iterator=intensityList.entrySet().iterator();
		 
//        for (Map.Entry entry : intensityList.entrySet()) {
//        	Log.d("valkyrie", "Key : " + entry.getKey() 
//       			+ " Value : " + entry.getValue());
//        }
		// now lets sort this shit
		Map<Integer,Double> sortedMap =  sortByComparator(intensityList);


//		Log.d("valkyrie", max + "|" + min);
//        for (Map.Entry entry : sortedMap.entrySet()) {
//        	Log.d("valkyrie", "Key : " + entry.getKey() 
//       			+ " Value : " + entry.getValue());
//        }
        
        //finally our lut
        int i = 0;
        int test = 0;
        for (Map.Entry entry : sortedMap.entrySet()) {
        	if (entry.getValue().equals(-1.0) || entry.getValue().equals(-2.0))
        	{
        		test++;
        	    this.LUT[i] = 96; 
        	}else
        		this.LUT[i] = (Integer) entry.getKey(); 
        	Log.d("valkyrie", entry.getKey() + "");
        	
        	i++;
        }
        
        Log.d("valkyrie",java.util.Arrays.toString(this.LUT));
        Log.d("valkyrie",test + "|");
		

	}

	/**
	 * Sorts a map by value increasing
	 * 
	 * @param unsortMap 
	 * @return sorted Map :-)
	 */
	private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap) {
		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Loads LUT from JSON encoded file for Performance reasons
	 * 
	 */
	private void load() {

	}

	/**
	 * Saves LUT to JSON decoded file for Performance reasons
	 * 
	 */
	private void save() {

	}

	public int[] getLUT() {
		return LUT;
	}

	public void setLUT(int[] lUT) {
		LUT = lUT;
	}
	
	public String getName() {
		return name;
	}
}
