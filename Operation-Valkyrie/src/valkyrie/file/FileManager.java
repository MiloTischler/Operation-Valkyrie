package valkyrie.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileManager {
	final static String TAG = "FileManager";
	final static String IMGNAME = "IMG";
	final static String IMGCOUNT = "0000";
	final static String SDPATH = Environment.getExternalStorageDirectory().toString() + "/Valkyrie/Gallery/";

	public FileManager() {
		initFileManager();
	}
	
	private void initFileManager() {
		File file = new File(SDPATH);
		if(!file.exists())
			file.mkdirs();
	}
	
	public void saveImageToGallery(Bitmap bitmap) {
		OutputStream fOut = null;
		File directory = new File(SDPATH);
//		if (!directory.exists())
//			directory.mkdirs();
		File files[] = directory.listFiles();
		File file;
		if (files.length > 0) {
			int highestnumber = 0;
			for (File f : files) {
				if (f.exists()) {

					Log.d("DEBUGName", f.getName());
					int newhighest = Integer.parseInt(getLatestImage().substring(3, getLatestImage().length() - 4));
					if (newhighest >= highestnumber) {
						highestnumber = newhighest + 1;
					}
					Log.d("DEBUG", String.valueOf(highestnumber));

				}
			}

			String filenumber = IMGCOUNT + highestnumber;
			filenumber = filenumber.substring(filenumber.length() - 4);

			file = new File(SDPATH, IMGNAME + filenumber + ".png");
		} else {
			String filenumber = IMGCOUNT + 1;
			filenumber = filenumber.substring(filenumber.length() - 4);
			file = new File(SDPATH, IMGNAME + filenumber + ".png");
		}

		try {

			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			Log.e("FileManager", e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
		bitmap.recycle();
	}

	public void saveImageToInternal(Bitmap bitmap) {
		// TODO laurenz
	}

	public Bitmap loadImageFromGallery(String imageName) {
		InputStream is = FileManager.class.getResourceAsStream(SDPATH + imageName);
		return BitmapFactory.decodeStream(is);

	}

	public Bitmap loadImageFromInternal(String imageName) {
		File imageFile = new File(SDPATH + imageName);
		if (imageFile.exists()) {
			InputStream is = FileManager.class.getResourceAsStream(SDPATH + imageName);
			return BitmapFactory.decodeStream(is);
		} else
			return null;
	}

	public void deleteImageFromGallery(String imageName) {
		File fileToDelete = new File(SDPATH + imageName);
		if (fileToDelete.exists())
			fileToDelete.delete();

	}

	public String getLatestImage() {
		File directory = new File(SDPATH);
		String imageName = null;
		File files[] = directory.listFiles();
		int highestnumber = 0;
		for (File f : files) {
			if (f.exists()) {

				Log.d("DEBUGName", f.getName());
				int newhighest = Integer.parseInt(f.getName().substring(3, f.getName().length() - 4));
				if (newhighest >= highestnumber) {
					highestnumber = newhighest + 1;
					imageName = f.getName();
				}
				Log.d("DEBUG", String.valueOf(highestnumber));
			}
		}
		if (imageName != null)
			return imageName;
		else 
			return "Error";
	}

}
