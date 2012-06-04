package valkyrie.ui;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private String TAG = "ImageAdapter";
	private int pictures = 0;



	public ImageAdapter(Context c) {
		this.mContext = c;
		Log.d(TAG, "Constructor");
		this.pictures = DecodeBitmaps.thumbs.size();
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
		
		ImageView imageView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			Log.d(TAG, "convertView Null");
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(1, 1, 1, 1);

		} else {
			imageView = (ImageView) convertView;
		}
//		imageView = (ImageView) convertView;
		Log.i(TAG, "Image adapter setImage ");
		imageView.setImageBitmap(DecodeBitmaps.thumbs.get(position));
		return imageView;
	}

	// ---------------test--------------------
//	protected void finalize() throws Throwable {
//		recycleBmps();
//		super.finalize();
//	}
//
//	// ----------------------------------------
//	public void recycleBmps() {
//		int i = 0;
//		for (Bitmap e : bitVec) {
//
//			e.recycle();
//			Log.d(TAG, "recycle Bitmap " + i);
//			i++;
//		}
//		for (Bitmap e : bitFullVec) {
//
//			e.recycle();
//			Log.d(TAG, "recycle2 Bitmap " + i);
//			i++;
//		}
//
//	}
}