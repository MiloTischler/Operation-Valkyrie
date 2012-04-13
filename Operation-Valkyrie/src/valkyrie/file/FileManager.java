package valkyrie.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.InputFilter.LengthFilter;
import android.util.Log;

public class FileManager {
	final static String tag = "FileManager";
	final static String fileName = "IMG";
	final static String zeros = "0000";
	final static String pathName = Environment.getExternalStorageDirectory().toString() + "/Valkyrie/Gallery/";

	public void saveImageToGallery(Bitmap bitmap) {
		OutputStream fOut = null;
		File directory = new File(pathName);
		if (!directory.exists())
			directory.mkdirs();
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

			String filenumber = zeros + highestnumber;
			filenumber = filenumber.substring(filenumber.length() - 4);

			file = new File(pathName, fileName + filenumber + ".png");
		} else {
			String filenumber = zeros + 1;
			filenumber = filenumber.substring(filenumber.length() - 4);
			file = new File(pathName, fileName + filenumber + ".png");
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
			Log.e(tag, e.toString());
			e.printStackTrace();
		}
	}

	public void saveImageToInternal(Bitmap bitmap) {
		// TODO laurenz
	}

	public Bitmap loadImageFromGallery(String imageName) {

		Bitmap bitmap = null;
		return bitmap;
	}

	public Bitmap loadImageFromInternal(String imageName) {
		// TODO Laurenz
		Bitmap bitmap = null;
		return bitmap;
	}

	public void deleteImageFromGallery(String imageName) {
		File fileToDelete = new File(pathName + imageName);
		if (fileToDelete.exists())
			fileToDelete.delete();

	}

	// testing purpose only:
	public Boolean imageExists(String imageName) {
				if (getLatestImage().equals(imageName))
				return true;	
		return false;
	}
	
	public String getLatestImage() {
		File directory = new File(pathName);
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
		return imageName;
	}

}
