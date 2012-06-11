package valkyrie.ui.gallery;



import valkyrie.file.DecodeBitmaps;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


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
/**
 * Extended class from BaseAdapter, which manages the gridview and the displaying
 * of its pictures.
 */
public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageAdapter";
	
	private Context mContext;
	private int pictures = 0;
	private BitmapFactory.Options opt = new BitmapFactory.Options();


	/**
	 * Sets the Context for the ImageView
	 * and the number of thumbs to display
	 * 
	 * @param c
	 */
	public ImageAdapter(Context c) {
		this.mContext = c;
		this.pictures = DecodeBitmaps.thumbPosition.size();
		
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

	/**
	 * Sets the pictures of the GridView with the thumbs which lies in the Thumb folder.
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		opt.inSampleSize = 4;
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
		//decode Bitmaps for viewing in gridView
		try{
	//	imageView.setImageBitmap(DecodeBitmaps.thumbs.get(position));	
		imageView.setImageBitmap(BitmapFactory.decodeFile(DecodeBitmaps.thumbPosition.get(position)));
		}
		//if we have to less memory to display all the thumbs, create new thumbs with downscaled pixels and resolutions
		catch (OutOfMemoryError e){
			Log.d(TAG, "Running out of Memory using more compressed bitmaps");
			imageView.setImageBitmap(BitmapFactory.decodeFile(DecodeBitmaps.thumbPosition.get(position),opt));
		}
		return imageView;
	}
}