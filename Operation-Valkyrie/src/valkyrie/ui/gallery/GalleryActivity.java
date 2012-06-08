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

		this.savedInsta = savedInstanceState;
		DecodeBitmaps decodeBitmaps = new DecodeBitmaps(NO);
		setContentView(R.layout.gallery);

		GridView gallery = (GridView) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		registerForContextMenu(gallery);
		super.onCreate(savedInstanceState);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

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
	public boolean onCreateOptionsMenu(Menu menu) {

		SubMenu gods = menu.addSubMenu("Show me the Gods");
		gods.add("Paul");
		gods.add("Laurenz");
		gods.add("Milo");
		gods.add("Ritzy");
		gods.add("Schweigi");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Toast.makeText(this.getApplicationContext(),
				"Dont be afraid",
				Toast.LENGTH_LONG).show();
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		FileManager fileManager = new FileManager();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 4;

		if (item.getTitle() == "Laurenz") {

			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.laurenz ));

			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);

		} else if (item.getTitle() == "Milo") {
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.milo ));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);

		} else if (item.getTitle() == "Schweigi") {
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.schweigi));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);
		} else if (item.getTitle() == "Paul") {
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.paul));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);
		} else if (item.getTitle() == "Ritzy") {
			fileManager.saveImageToGallery(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.ritzy));
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Picture Options");
		menu.add(0, v.getId(), 0, "Delete selected Picture");

		SubMenu subMenu = menu.addSubMenu(0, v.getId(), 0,
				"Delete all Pictures");
		SubMenu subMenuFilters = menu.addSubMenu(0, v.getId(), 0, "Use Filter");
		subMenu.add(0, YES, 0, "Yes");
		subMenu.add(0, NO, 0, "No");
		subMenuFilters.add(0, v.getId(), 0, "GrayScale");
		subMenuFilters.add(0, v.getId(), 0, "Ascii");
		subMenuFilters.add(0, v.getId(), 0, "Matrix");
		subMenuFilters.add(0, v.getId(), 0, "Laplace");
		subMenuFilters.add(0, v.getId(), 0, "Canny Edge");

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
			DecodeBitmaps callConst = new DecodeBitmaps(NO);

			Toast.makeText(this.getApplicationContext(), "Picture Deleted",
					Toast.LENGTH_SHORT).show();
			onCreate(savedInsta);

		} else if (item.getTitle() == "Delete all Pictures") {

		} else if (item.getItemId() == YES) {
			Log.d(TAG, "FUCK YOU YES = 1");
			for (String s : DecodeBitmaps.fullImgNames) {
				fileManager.deleteImageFromGallery(s);
			}
			Toast.makeText(this.getApplicationContext(),
					"All Pictures were deleted", Toast.LENGTH_SHORT).show();
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);

		} else if (item.getItemId() == NO) {
			Toast.makeText(this.getApplicationContext(), "Whatever you like",
					Toast.LENGTH_LONG).show();
			DecodeBitmaps.done = false;
			DecodeBitmaps callConst = new DecodeBitmaps(NO);
			onCreate(savedInsta);
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
		super.onBackPressed();
		DecodeBitmaps.done = false;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

}
