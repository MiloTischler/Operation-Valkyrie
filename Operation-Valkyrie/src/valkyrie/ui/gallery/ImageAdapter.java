package valkyrie.ui.gallery;



import valkyrie.file.DecodeBitmaps;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageAdapter";
	
	private Context mContext;
	private int pictures = 0;



	public ImageAdapter(Context c) {
		this.mContext = c;
		this.pictures = DecodeBitmaps.thumbs.size();
		
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
		Log.i(TAG, "Image adapter setImage ");
		imageView.setImageBitmap(DecodeBitmaps.thumbs.get(position));
		return imageView;
	}
}