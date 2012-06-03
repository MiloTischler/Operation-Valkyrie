package valkyrie.ui;

import valkyrie.file.FileManager;
import java.io.File;
import java.util.Vector;

import valkyrie.main.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private String TAG = "ImageAdapter";
	private File imageList[];
	private int pictures = 0;
	private Vector<Bitmap> bitVec = new Vector<Bitmap>();
	public Vector<Bitmap> bitFullVec = new Vector<Bitmap>();
	private File file = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");

	public ImageAdapter(Context c) {
		mContext = c;
		Log.d(TAG, "Constructor");
		Bitmap bitmapFull;
		BitmapFactory.Options o = new BitmapFactory.Options();
		BitmapFactory.Options ofull = new BitmapFactory.Options();
		o.inSampleSize = 6;
		ofull.inSampleSize = 2;
		imageList = file.listFiles();
		for (int i = 0; i < imageList.length - 6; i++) {
			Log.i(TAG,"Image adapter durchlauf : " + i + ": path" + imageList[i].getAbsolutePath());
	//not necessary if i have some thumbnails
			Bitmap previewBitmap = Bitmap.createScaledBitmap(
					BitmapFactory.decodeFile(imageList[i].getAbsolutePath(),o),
					85, 85, false);
			bitmapFull = Bitmap.createScaledBitmap(
				BitmapFactory.decodeFile(imageList[i].getAbsolutePath()
							,ofull),500,300,false);
		
			bitFullVec.add(bitmapFull);

		//	Bitmap b = BitmapFactory.decodeFile(imageList[i].getAbsolutePath());
			bitVec.add(previewBitmap);//b);
		}
		this.pictures = bitVec.size();
		Log.d(TAG, "Constructor over and out");
	}

	public int getCount() {
		Log.d(TAG, "getCount");
		return this.pictures;
	}

	public Object getItem(int position) {
		Log.d(TAG, "getItem");
		return null;
	}

	public long getItemId(int position) {
		Log.d(TAG, "getItemId");
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "in getview");
		ImageView imageView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(1, 1, 1, 1);

		} else {
			imageView = (ImageView) convertView;
		}
		Log.i(TAG,"Image adapter setImage " );
		imageView.setImageBitmap(bitVec.elementAt(position));
//		imageView.setImageBitmap(Bitmap.createScaledBitmap(
//				BitmapFactory.decodeFile(imageList[position].getAbsolutePath()),
//				85, 85, false));

		Log.d(TAG, "after setImageResource getview");
		return imageView;
	}
//---------------test--------------------
	protected void finalize() throws Throwable
	{
		int i = 0;
		for (Bitmap e : bitVec) {
			
			e.recycle();
			Log.d(TAG, "recycle Bitmap " + i);
			i++;
		}
	  super.finalize(); 
	} 
	
//----------------------------------------
//	public void recycleBmps() {
//		int i = 0;
//		for (Bitmap e : bitVec) {
//		
//			e.recycle();
//			Log.d(TAG, "recycle Bitmap " + i);
//			i++;
//		}
//
//	}
}