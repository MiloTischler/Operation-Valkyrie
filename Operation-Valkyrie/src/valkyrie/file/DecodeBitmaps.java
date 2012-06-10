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
	private int opt = 6;

	public DecodeBitmaps(int error) {
	//in case of outofmemory for creating thumbs
		if (error == 1) {
			this.opt = 8;
			Log.d(TAG, "new opt ? : " + this.opt);
			thumbs.clear();
			thumbPosition.clear();
			fullImgNames.clear();
			fullImgPosition.clear();
			decodeBitmap();
		} else {
			//if thumbs are already saved skip this class
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
		Log.d(TAG, "done");
	}

	private void decodeBitmap() {

		fileList = files.listFiles();
		thumbList = thumbFiles.listFiles();
		BitmapFactory.Options thumbOpt = new BitmapFactory.Options();
		thumbOpt.inSampleSize = opt;

		
		if (fileList != null && thumbList != null) {
			//save positions and names for all files
			//to display and check all thumbs and full images
			for (int i = 0; i < fileList.length; i++) {
				fullImgPosition.add(fileList[i].getAbsolutePath());
				fullImgNames.add(fileList[i].getName());
				Log.d(TAG, "file length: " + fileList.length + "but i still : "
						+ i);
			}

			int thumbCounter = 0;
			for (int i = 0; i < fileList.length; i++) {

				boolean match = false;
				//check if a thumb of a given file already exists
				for (File f : thumbList) {
					if (f.getName().contentEquals(fileList[i].getName())) {
						match = true;
						Log.d(TAG, "thumb name here with :" + f.getName());
					}

				}
				if (match) {
					thumbPosition
							.add(thumbList[thumbCounter].getAbsolutePath());
					thumbCounter++;
					Log.d(TAG, "thumb already exists : " + i);
				//if a new picture is taken we didnt have a thumb so match == false 
				//so we need a new thumb
				} else {
					Log.d(TAG, "new thumb crashes ? created : " + i);
					Bitmap newThumb;
					newThumb = Bitmap.createScaledBitmap(
							BitmapFactory.decodeFile(
									fileList[i].getAbsolutePath(), thumbOpt),
							120, 80, false);
					saveAThumb(newThumb, fileList[i].getName());
					thumbPosition.add(fileList[i].getAbsolutePath());

				}
			}
			done = true;
		} else {

		}
	}

	//save a thumb of the given bitmap / img and its name 
	public void saveAThumb(Bitmap bitmap, String imgName) {

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);

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
			bitmap.recycle();

		} else {
			Log.d(TAG, "couldnt save the thumb");
			bitmap.recycle();
		}
	}

	//function for recycling the bitmaps
	public void recycleBitmaps() {
		int i = 0;
		for (Bitmap b : DecodeBitmaps.thumbs) {
			i++;
			b.recycle();
			Log.d(TAG, i + " Bitmaps recycled");
		}
	}

}
