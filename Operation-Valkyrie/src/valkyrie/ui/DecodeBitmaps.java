package valkyrie.ui;

import java.io.File;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class DecodeBitmaps {
	
	private static boolean done = false;
	private String TAG = "DecodeBitmaps";
	private File files = new File (Environment.getExternalStorageDirectory() + "/Valkyrie/Gallery");
	private File fileList[];
	public static Vector<Bitmap> thumbs = new Vector<Bitmap>();
	public static Vector<Bitmap> fullImg = new Vector<Bitmap>();
	
	public DecodeBitmaps() {
	
		if (done == true) {
			Log.d(TAG, "all worke done hours ago ;)");
		}else decodeBitmap();
	}

	private void decodeBitmap() {
	
		fileList = files.listFiles();
		BitmapFactory.Options thumbOpt = new BitmapFactory.Options();
		BitmapFactory.Options fullOpt = new BitmapFactory.Options();
		thumbOpt.inSampleSize = 6;
		fullOpt.inSampleSize = 2;
		
		
		
		for (File f : fileList){
			Bitmap bitmapThumb,bitmapFull;
			bitmapThumb = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), thumbOpt),1184/10 , 720/10 , false);
			thumbs.add(bitmapThumb);
			bitmapFull = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), fullOpt),1184 , 720 , false);
			fullImg.add(bitmapFull);

		}
		done = true;
		
	}
	
	

}
