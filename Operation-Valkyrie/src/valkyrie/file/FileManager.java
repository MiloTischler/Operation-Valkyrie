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
	final static String pathName = Environment.getExternalStorageDirectory()
			.toString() + "/Valkyrie/Gallery/";
	

	public void saveImageToGallery(Bitmap bitmap) {
		File pathtest = new File(pathName);
		if (!pathtest.exists())
			pathtest.mkdirs();
		OutputStream fOut = null;
		// get the number of the last image
		
		File directory = new File(pathName);
		File files[] = directory.listFiles();
		File lastfile = new File("test");

		String[] filename = null;
		int highestnumber = 0;
		for (File f : files) {

			if(f.exists()) {
			lastfile= f;
			filename = lastfile.toString().split("/"); 
			int newhighest = Integer.parseInt(filename[filename.length-1].substring(3, filename[filename.length-1].length()-4));
			if(newhighest > highestnumber)
				Log.d("DEBUG", String.valueOf(highestnumber));
				highestnumber = highestnumber+1;
			}
		}
		
		Log.d("DEBUG", String.valueOf(highestnumber));
		
		
		//
		
		File file ;
		
		if(lastfile.exists()){
			
			file = new File(pathName, fileName+ highestnumber +  ".png");
		}
		else {
		 file = new File(pathName, fileName + 1 + ".png");
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

	}

	// testing purpose only:
	public Boolean imageExists() {
		File directory = new File(pathName);
		File files[] = directory.listFiles();

		for (File f : files) {

			f.exists();
			return true;
		}
		return false;
	}

}
