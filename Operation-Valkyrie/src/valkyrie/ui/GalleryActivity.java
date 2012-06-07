package valkyrie.ui;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Currency;
import java.util.Vector;

import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.main.R;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

import android.widget.GridView;

import android.widget.ImageView;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class GalleryActivity extends Activity {

	private String TAG = "GalleryActivity";

	private File files = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	private File fileList[];
	private int index = 0;
	private Bundle savedInsta;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// save some pictures from drawable to sdcard for gallery preview
		createPictures();
		savedInsta = savedInstanceState;
		// ------------------------------------------------------------------
		Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		DecodeBitmaps decodeBitmaps = new DecodeBitmaps(d.getWidth(),
				d.getHeight());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		GridView gallery = (GridView) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		registerForContextMenu(gallery);

		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.d(TAG, "obwohl longclick hier drin");
				Intent showPicIntent = new Intent(getApplicationContext(),
						ShowPicActivity.class);
				showPicIntent.putExtra("id", position);
				GalleryActivity.this.startActivity(showPicIntent);
				
			
			}
		
		});

		gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				GalleryActivity.this.index = arg2;
				Log.d(TAG, "set index lets try : " + index);
				return false;
			}
		});
		
		Log.d(TAG, "after onItemClickListener");

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("trololololloooo");
		menu.add(0, v.getId(), 0, "Format Sdcard");
		menu.add(0, v.getId(), 0, "Do nothing");
		Log.d(TAG, "Id" + v.getId());
		Log.d(TAG, "menuInfo" + menuInfo.toString());
		Log.d(TAG, "");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		FileManager fileManager = new FileManager();
		if (item.getTitle() == "Format Sdcard") {
			fileManager.deleteImageFromGallery(DecodeBitmaps.fullImgNames.get(index));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(1, 1);
			
			
			Toast.makeText(
					this.getApplicationContext(),
					"sdcard Formating....",
					Toast.LENGTH_SHORT).show();
			onCreate(savedInsta);
		} else if (item.getTitle() == "Do nothing") {
			Toast.makeText(
					this.getApplicationContext(),
					"self destruction initialized",
					Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		// DecodeBitmaps decodeBitmaps = new DecodeBitmaps();
		// decodeBitmaps.recycleBitmaps();
		DecodeBitmaps.done = false;
		super.onBackPressed();

	}

	private void createPictures() {
		FileManager fileManager = new FileManager();
		fileList = files.listFiles();
		if (fileList() != null) {
			if (fileList.length == 0) {
				Log.d(TAG, "no files in gallery produce some ...");
				// -------------------create some
				// files----------------------------
				fileManager.saveImageToGallery(BitmapFactory.decodeResource(
						this.getResources(), R.drawable.jellyfish));
				fileManager.saveImageToGallery(BitmapFactory.decodeResource(
						this.getResources(), R.drawable.tulips));

			} else {
				Log.d(TAG, fileList.length
						+ " files in gallery. No need for more.");
			}
		} else {
			Toast.makeText(
					this.getApplicationContext(),
					"couldnt create some files, and there are no in the folders so you will have a problem xD",
					Toast.LENGTH_SHORT).show();
		}

	}

}
