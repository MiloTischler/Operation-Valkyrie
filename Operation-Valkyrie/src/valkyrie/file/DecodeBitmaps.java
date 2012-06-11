package valkyrie.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */

/**
 * 
 * This class handles all the file paths and thumb paths so that a correct
 * viewing in the gridview and then viewing the correct item in FUllscreen mode
 * is possible. Also responsible for creating new thumbs in case of new pictures
 * in the gallery.
 * 
 */
public class DecodeBitmaps {

	public static boolean done = false;
	private String TAG = "DecodeBitmaps";
	private File files = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	private File thumbFiles = new File(
			Environment.getExternalStorageDirectory() + "/Valkyrie/Thumbnls");
	private File fileList[];
	private File thumbList[];
	public static Vector<Bitmap> thumbs = new Vector<Bitmap>();
	public static Vector<String> fullImgPosition = new Vector<String>();
	public static Vector<File> fileVector = new Vector<File>();
	public static Vector<String> fullImgNames = new Vector<String>();
	public static Vector<String> thumbPosition = new Vector<String>();
	private int opt = 4;

	/**
	 * called if new pictures are taken or some are deleted
	 * 
	 * @param int error out of memory for displaying the thumbs in the gridview
	 */
	public DecodeBitmaps(int error) {
		// in case of outofmemory for creating thumbs
		if (error == 1) {
			this.opt = 6;
			Log.d(TAG, "new opt ? : " + this.opt);
			thumbs.clear();
			thumbPosition.clear();
			fullImgNames.clear();
			fullImgPosition.clear();
			decodeBitmap();
		} else {
			// if thumbs are already saved skip this class
			if (done == true) {
				// TODO: its too static ;)
				Log.d(TAG, "all work here was done hours ago ;)");
			} else {
				thumbs.clear();
				thumbPosition.clear();
				fullImgNames.clear();
				fullImgPosition.clear();
				decodeBitmap();
			}
		}

	}

	/**
	 * this function is called everytime we have a new picture in the
	 * galleryfolder then it checks if all the other thumbs are still exists, if
	 * not new ones will be created.
	 */
	private void decodeBitmap() {

		fileList = files.listFiles();
		thumbList = thumbFiles.listFiles();
		BitmapFactory.Options thumbOpt = new BitmapFactory.Options();
		thumbOpt.inSampleSize = opt;

		if (fileList != null && thumbList != null) {
			// save positions and names for all files
			// to display and check all thumbs and full images
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i] != null) {
					fullImgPosition.add(fileList[i].getAbsolutePath());
					fullImgNames.add(fileList[i].getName());
					Log.d(TAG, "file length: " + fileList.length
							+ "but i still : " + i);
				} else {
					i++;
				}

			}

			int thumbCounter = 0;
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i] != null) {
					boolean match = false;
					// check if a thumb of a given file already exists
					for (File f : thumbList) {
						// Log.d(TAG,
						// "Checking if thumb already exists for all thumbs " +
						// i + ": " + f.getName());
						if (f.getName().contentEquals(fileList[i].getName())) {
							match = true;
							Log.d(TAG, "thumb name here with : " + f.getName());
						}

					}
					if (match) {
						thumbPosition.add(thumbList[thumbCounter]
								.getAbsolutePath());

						thumbCounter++;
						Log.d(TAG, "thumb already exists : " + i);
						// if a new picture is taken we didnt have a thumb so
						// match
						// == false
						// so we need a new thumb
					} else {
						Log.d(TAG,
								"saving thumb with name: "
										+ fileList[i].getName());
						Bitmap newThumb;
						newThumb = BitmapFactory.decodeFile(
								fileList[i].getAbsolutePath(), thumbOpt);
						saveAThumb(newThumb, fileList[i].getName(), i);

					}
				} else {
					i++;
				}
				done = true;
			}
		} else {

		}
		Log.d(TAG, "done");
	}

	/**
	 * A compressed png is produced in a thumbfolder for a quicker performance
	 * in the gridview. Only if the sdcard is not mounted.
	 * 
	 * @param Bitmap
	 *            bitmap The bitmap to save in form of a thumb
	 * @param String
	 *            imgName The given Name for the thumb
	 */
	public void saveAThumb(Bitmap bitmap, String imgName, int vecPos) {

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/Valkyrie/Thumbnls/" + imgName);
			try {
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());
				Log.d(TAG, "saved a Thumb");

			} catch (IOException e) {
				Log.d(TAG, "io crash ? ");
				e.printStackTrace();
			}
			
			thumbList = thumbFiles.listFiles();
			thumbPosition.add(thumbList[vecPos].getAbsolutePath());
			
			bitmap.recycle();

		} else {
			Log.d(TAG, "couldnt save the thumb");
			bitmap.recycle();
		}
	}

	/**
	 * Recycles all the bitmaps
	 */
	public void recycleBitmaps() {
		int i = 0;
		for (Bitmap b : DecodeBitmaps.thumbs) {
			i++;
			b.recycle();
			Log.d(TAG, i + " Bitmaps recycled");
		}
	}

}
