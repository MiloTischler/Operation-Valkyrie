package valkyrie.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import valkyrie.file.FileManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DecodeBitmaps {

	private static boolean done = false;
	private String TAG = "DecodeBitmaps";
	private File files = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	private File thumbFiles = new File(
			Environment.getExternalStorageDirectory() + "/Valkyrie/Thumbnls");
	private File fileList[];
	private File thumbList[];
	public static Vector<Bitmap> thumbs = new Vector<Bitmap>();
	public static Vector<Bitmap> fullImg = new Vector<Bitmap>();
	public static Vector<String> fullImgPosition= new Vector<String>();
	public FileManager fileManager = new FileManager();


	
	public DecodeBitmaps(int width, int heigth) {

		if (done == true) {
			// TODO: its too static ;)
			Log.d(TAG, "all work here was done hours ago ;)");

		} else
			decodeBitmap();
	}

	private void decodeBitmap() {

		fileList = files.listFiles();
		thumbList = thumbFiles.listFiles();
		BitmapFactory.Options thumbOpt = new BitmapFactory.Options();
		BitmapFactory.Options fullOpt = new BitmapFactory.Options();
		thumbOpt.inSampleSize = 6;
		fullOpt.inSampleSize = 4;
		

		for (Integer i = 0; i < fileList.length; i++) {
			Bitmap bitmapFull;
			Log.d(TAG, fileList[i].getName());
			Log.d(TAG, "-----------------------------");
			// TODO: too slow if those were calculated on every startup from the app
	//		bitmapFull = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(
	//				fileList[i].getAbsolutePath(), fullOpt), 1184, 720, false);
	//		bitmapFull = BitmapFactory.decodeFile(fileList[i].getAbsolutePath(),fullOpt);
	//		fullImg.add(bitmapFull);
			fullImgPosition.add(fileList[i].getAbsolutePath());
			
		}

		
		int th = 0;
		for (int i = 0; i < fileList.length; i++) {

			Bitmap bitmapThumb;
			Log.d(TAG,
					fileList[i].getName() + " - mit pfad - "
							+ fileList[i].getAbsolutePath());
			boolean match = false;

			for (File f : thumbList) {
				if (f.getName().contentEquals(fileList[i].getName())) {
					match = true;
					// Log.d(TAG, "match: " + match + " " + f.getName() + " = "
					// + fileList[i].getName());
				}
				// else Log.d(TAG, "match: " + match + " " + f.getName() + " = "
				// + fileList[i].getName());
			}

			
			if (match) {

				Log.d(TAG, "th :" + th + "  i :" + i + "but thumblist.length: "
						+ thumbList.length);
				Log.d(TAG,
						i + " " + thumbList[i].getName()
								+ " already Exists in "
								+ thumbList[i].getAbsolutePath());

				bitmapThumb = BitmapFactory.decodeFile(thumbList[i]
						.getAbsolutePath());
				thumbs.add(bitmapThumb);
				th++;
			} else {
				Log.d(TAG, i + " creating a new Thumbs");
				Bitmap newThumb;
				newThumb = Bitmap
						.createScaledBitmap(BitmapFactory.decodeFile(
								fileList[i].getAbsolutePath(), thumbOpt), 85,
								85, false);

				saveAThumb(newThumb, fileList[i].getName());
				thumbs.add(newThumb);
			}
		}
		done = true;
		//only for dev testing
	//	for (int i = fileList.length ; i < thumbList.length; i++){
	//		thumbs.add(BitmapFactory.decodeFile(thumbList[i].getAbsolutePath()));
	//		Log.d(TAG, i +"  adding more thumbs which will cause NullpointersEx on click :)");
	//	}
	}

	public void saveAThumb(Bitmap bitmap, String imgName) {
		
		

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/Valkyrie/Thumbnls/" + imgName);

		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			Log.d(TAG, "saveaThumb");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void recycleBitmaps(){
		int i = 0;
		for (Bitmap b : DecodeBitmaps.thumbs) {
			i++;
			b.recycle();
			Log.d(TAG, i + " Bitmaps recycled");
		}
		for (Bitmap b : DecodeBitmaps.fullImg) {
			b.recycle();
			i++;
			Log.d(TAG, i + " Bitmaps recycled");
		}
	}
}
