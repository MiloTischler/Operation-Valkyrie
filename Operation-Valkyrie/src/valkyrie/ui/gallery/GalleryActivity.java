package valkyrie.ui.gallery;

import java.io.File;
import java.util.ArrayList;

import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.filter.FilterManager;
import valkyrie.filter.IFilter;
import valkyrie.main.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob
 * Schweighofer, Milo Tischler © Milo Tischler, Jakob Schweighofer, Alexander
 * Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class GalleryActivity extends Activity {
	private static final String TAG = "GalleryActivity";
	private File files = new File(Environment.getExternalStorageDirectory()
			+ "/Valkyrie/Gallery");
	private File fileList[];
	private int index = 0;
	private Bundle savedInsta;
	private int YES = 1;
	private int NO = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// create some pictures from drawable to sdcard for gallery preview if
		// no exists
		createPictures();
		// ------------------------------------------------------------------
		DecodeBitmaps decodeBitmaps = new DecodeBitmaps();
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
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {

				GalleryActivity.this.index = position;
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Picture Options");
		menu.add(0, v.getId(), 0, "Delete selected Picture");
		menu.add(0, v.getId(), 0, "Show Filters for Converting");

		SubMenu subMenu = menu.addSubMenu(0, v.getId(), 0,
				"Delete all Pictures");
		SubMenu subMenuFilters = menu.addSubMenu(0,v.getId(),0,"Filters");
		SubMenu subMenuCreate = menu.addSubMenu(0,v.getId(),0,"Gods of Valkyrie");
		subMenu.add(0, YES, 0, "Yes");
		subMenu.add(0, NO, 0, "No");
		subMenuFilters.add(0, v.getId(), 0, "GrayScale");
		subMenuFilters.add(0, v.getId(), 0, "Ascii");
		subMenuFilters.add(0, v.getId(), 0, "Matrix");
		subMenuFilters.add(0, v.getId(), 0, "y");
		subMenuFilters.add(0, v.getId(), 0, "x");
		subMenuCreate.add(0, v.getId(), 0, "Ritzy");

		subMenuCreate.add(0, v.getId(), 0, "Paul");
		subMenuCreate.add(0, v.getId(), 0, "Milo");
		subMenuCreate.add(0, v.getId(), 0, "Laurenz");
		subMenuCreate.add(0, v.getId(), 0, "Schweigi");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		FileManager fileManager = new FileManager();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 4;
		if (item.getTitle() == "Delete selected Picture") {

			fileManager.deleteImageFromGallery(DecodeBitmaps.fullImgNames
					.get(index));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();

			Toast.makeText(this.getApplicationContext(), "Picture Deleted",
					Toast.LENGTH_SHORT).show();
			onCreate(savedInsta);

		} else if (item.getTitle() == "Delete all Pictures") {

			Log.d(TAG, "YES OR NO");

		}else if (item.getItemId() == YES) {
			Log.d(TAG, "FUCK YOU YES = 1");
			for (String s : DecodeBitmaps.fullImgNames) {
				fileManager.deleteImageFromGallery(s);
			}
			Toast.makeText(this.getApplicationContext(),
					"All Pictures were deleted", Toast.LENGTH_SHORT).show();
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
			
		} else if (item.getItemId() == NO) {
			Toast.makeText(this.getApplicationContext(),
					"PUSSY, RLY SUCH A PUSSY", Toast.LENGTH_LONG).show();
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
		} else if (item.getTitle() == "Laurenz"){
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.laurenz,opt));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
			
		} else if (item.getTitle() == "Milo"){
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.milo,opt));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
			
		} else if (item.getTitle() == "Schweigi"){
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.schweigi,opt));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
		} else if (item.getTitle() == "Paul"){
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.paul,opt));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
		} else if (item.getTitle() == "Ritzy"){
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.ritzy,opt));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps();
			onCreate(savedInsta);
			
		}





		// Uri uri = Uri.fromFile(DecodeBitmaps.fileVector.get(index));
		// Intent intent = new Intent();
		// intent.setDataAndType(uri, "image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		//
		// startActivityForResult(
		// Intent.createChooser(intent, "Select Picture"), index);
		else if (item.getTitle() == "Show Filters for Converting") {
			Toast.makeText(this.getApplicationContext(), "Doesnt do shit",
					Toast.LENGTH_SHORT).show();

		} else if (item.getTitle() == "test submenu") {
			Log.d(TAG, "test submenu clicked");
		}

		else {
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
		DecodeBitmaps decodeBitmaps = new DecodeBitmaps();
		decodeBitmaps.recycleBitmaps();
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
