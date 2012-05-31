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
	private File file = new File(Environment.getExternalStorageDirectory() + "/Valkyrie/Gallery/");

	public ImageAdapter(Context c) {
		mContext = c;
		Log.d(TAG, "Constructor");

		imageList = file.listFiles();
		for (int i = 0; i < imageList.length; i++) {
			Log.d("Image: " + i + ": path", imageList[i].getAbsolutePath());
			Bitmap b = BitmapFactory.decodeFile(imageList[i].getAbsolutePath());
			bitVec.add(b);
		}
		this.pictures = bitVec.size();
		Log.d(TAG, "Constructor over and out");
	}

	public int getCount() {
		return this.pictures;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
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

		imageView.setImageBitmap(bitVec.elementAt(position));

		Log.d(TAG, "after setImageResource getview");
		return imageView;
	}
	


	public void recycleBmps() {
		for (Bitmap e : bitVec){
			e.recycle();
			Log.d(TAG, "recycle Bitmap gogo");
		}
		
	}
}