package valkyrie.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import valkyrie.ui.gallery.GalleryActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

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
	// public static Vector<Bitmap> fullImg = new Vector<Bitmap>();
	public static Vector<String> fullImgPosition = new Vector<String>();
	public static Vector<File> fileVector = new Vector<File>();
	public static Vector<String> fullImgNames = new Vector<String>();
	public static Vector<String> thumbPosition = new Vector<String>();
	private FileManager fileManager = new FileManager();
	private int opt = 6;
	
	public DecodeBitmaps(int error) {
		if (error == 1){
		this.opt = 8;
		Log.d(TAG, "new opt ? : " + this.opt);
		thumbs.clear();
		decodeBitmap();
		}
		
		if (done == true) {
			// TODO: its too static ;)
			Log.d(TAG, "all work here was done hours ago ;)");
		} else if (files.listFiles() != null) {
			if (files.listFiles().length == 0 ) {
				Log.d(TAG, "weeeeeeehaaaaaaaa list 0");
				for (Bitmap b : DecodeBitmaps.thumbs) {
					b.recycle();
					Log.d(TAG,  " Bitmaps recycled");
				}
				thumbs.clear();
		
			}
			if (thumbList != null) {
				fileVector.clear();
				fullImgPosition.clear();
				thumbs.clear();
				fullImgNames.clear();
				Log.d(TAG, "do the decode");
				decodeBitmap();
			} else {
				fileVector.clear();
				fullImgPosition.clear();
				thumbs.clear();
				fullImgNames.clear();
				Log.d(TAG, "do the decode");
				decodeBitmap();
			}


		} else if (files.listFiles() == null) {

			Log.d(TAG, "weeeeeeehaaaaaaaa");

			// super.onDestroy();

		}
		Log.d(TAG, "done");
	}

	private void decodeBitmap() {

		fileList = files.listFiles();
		thumbList = thumbFiles.listFiles();
		BitmapFactory.Options thumbOpt = new BitmapFactory.Options();
		BitmapFactory.Options fullOpt = new BitmapFactory.Options();
		thumbOpt.inSampleSize = opt;
		fullOpt.inSampleSize = 4;

		if (fileList != null && thumbList != null) {

			for (Integer i = 0; i < fileList.length; i++) {
				 Log.d(TAG, fileList[i].getName());
				 Log.d(TAG, "-----------------------------");
				fullImgPosition.add(fileList[i].getAbsolutePath());
				fullImgNames.add(fileList[i].getName());
				fileVector.add(fileList[i]);
				Log.d(TAG, "file length: " + fileList.length +"but i still : " + i);
			}
		
			int thumbCounter = 0;
		
			for (int i = 0; i < fileList.length; i++) {

				Bitmap bitmapThumb;
				boolean match = false;

				for (int k = 0; k < thumbList.length; k++) {
					if (thumbList[k].getName().contentEquals(fileList[i].getName())) {
						match = true;
						Log.d(TAG, "break at : " + k);
						break;
					}
				
					
				}
			//	match = true;
				if (match) {
//					bitmapThumb  = BitmapFactory
//							.decodeFile(thumbList[thumbCounter]
//									.getAbsolutePath());
					thumbPosition.add(thumbList[thumbCounter].getAbsolutePath());
					
					//thumbs.add(bitmapThumb);
					thumbCounter++;
					Log.d(TAG, "so much thumbs : " + i);
				} else {
					Log.d(TAG, "new thumb crashes ? : " +i);
					Bitmap newThumb;
					newThumb = Bitmap.createScaledBitmap(
							BitmapFactory.decodeFile(
									fileList[i].getAbsolutePath(), thumbOpt),120,80,false);
							

					saveAThumb(newThumb, fileList[i].getName());
					thumbs.add(newThumb);
				
				}
			}
			done = true;
		} else {

		}
	}

	public void saveAThumb(Bitmap bitmap, String imgName) {

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
	}

	public void recycleBitmaps() {
		int i = 0;
		for (Bitmap b : DecodeBitmaps.thumbs) {
			i++;
			b.recycle();
			Log.d(TAG, i + " Bitmaps recycled");
		}
	}


}
