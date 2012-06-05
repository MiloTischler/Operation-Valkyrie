package valkyrie.ui;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Currency;
import java.util.Vector;

import valkyrie.file.FileManager;
import valkyrie.main.R;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import android.widget.GridView;

import android.widget.ImageView;

public class GalleryActivity extends Activity {

	private String TAG = "GalleryActivity";

	private File files = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	private File fileList[];
    

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// save some pictures from drawable to sdcard for gallery preview
		createPictures();
		// ------------------------------------------------------------------

		//DecodeBitmaps decodeBitmaps = new DecodeBitmaps();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);




		GridView gallery = (GridView) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));

		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Intent showPicIntent = new Intent(getApplicationContext(),
						ShowPicActivity.class);
				showPicIntent.putExtra("id", position);
				GalleryActivity.this.startActivity(showPicIntent);

			}
		});
		Log.d(TAG, "after onItemClickListener");

	}

	@Override
	public void onPause() {

	
		super.onPause();
	}
	
	
	

	/*
	 * some fixes with paths and "if" needed but perhaps not necessary if
	 * thumbnail is created from the camera itself
	 */
	// public void createThumbnls() {
	// FileManager fileManager = new FileManager();
	// File file = new File(Environment.getExternalStorageDirectory()
	// + "/Valkyrie/Thumbnls");
	// // Vector<Bitmap> bitVec = new Vector<Bitmap>();
	// File imageList[];
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inSampleSize = 6;
	// imageList = file.listFiles();
	//
	// for (int i = 0; i < imageList.length; i++) {
	// Log.i(TAG, "Image adapter durchlauf : " + i + "prepare for if");
	//
	// if (fileManager
	// .loadImageFromGallery(imageList[i].getAbsolutePath()) == null) {
	// Bitmap previewBitmap = Bitmap.createScaledBitmap(BitmapFactory
	// .decodeFile(imageList[i].getAbsolutePath(), o), 85, 85,
	// false);
	// Log.i(TAG, "Image adapter durchlauf prepare for save : " + i
	// + ": path" + imageList[i].getAbsolutePath());
	// fileManager.saveImageToGallery(previewBitmap);
	//
	// }
	//
	// // Bitmap b =
	// // BitmapFactory.decodeFile(imageList[i].getAbsolutePath());
	//
	// }
	//
	// }

	private void createPictures() {
		FileManager fileManager = new FileManager();
		fileList = files.listFiles();

		if (fileList.length == 1) {
			Log.d(TAG, "no files in gallery produce some ...");
			// -------------------create some files----------------------------
			// Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.hydrangeas);
			// fileManager.saveImageToGallery(bitmap);
			// fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.desert));
			// fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.chrysanthemum));
			//fileManager.saveImageToGallery(BitmapFactory.decodeResource(
			//		this.getResources(), R.drawable.tulips));
			 fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			 R.drawable.jellyfish));
			// fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.desert));
			 fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			 R.drawable.chrysanthemum));
			// fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.tulips));
			// fileManager.saveImageToGallery(BitmapFactory.decodeResource(this.getResources(),
			// R.drawable.jellyfish));
		} else {
			Log.d(TAG, fileList.length + " files in gallery. No need for more.");
		}

	}
	



}
