package valkyrie.ui.gallery;


import valkyrie.file.DecodeBitmaps;
import valkyrie.file.FileManager;
import valkyrie.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;
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

/**
 * GalleryActivty if responsible for displaying the Gridview. Also has some
 * Listeners implemented for opening the Picture in fullscreen Mode and open a
 * context Menu for longClick.
 * 
 * Also responsible for creating and managing an OptionsMenu.
 * 
 * For displaying a picture in Fullscreen mode a new Activity is created, same
 * is true for the Aboutscreen.
 */
public class GalleryActivity extends Activity {
	private static final String TAG = "GalleryActivity";
	private int index = 0;
	private Bundle savedInsta;
	private int YES = 1;
	private int NO = 0;

	/**
	 * Sets the ContentView to the GalleryLayout Registers ContextMenu and
	 * therefore implements a ClickListener and a onLongClickListener
	 * 
	 * 
	 * @param Bundle
	 *            savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.savedInsta = savedInstanceState;
		new DecodeBitmaps(NO);
		setContentView(R.layout.gallery);

		GridView gallery = (GridView) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		registerForContextMenu(gallery);
		super.onCreate(savedInstanceState);

		gallery.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * Starts a new Activity when a thumb in the Gridview is selected.
			 */
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Intent showPicIntent = new Intent(getApplicationContext(),
						ShowPicActivity.class);
				showPicIntent.putExtra("id", position);
				GalleryActivity.this.startActivity(showPicIntent);
			}
		});
		// added an onitemlongclickListener for longclick options
		gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			/**
			 * sets the position on long click for further uses.
			 */
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {

				GalleryActivity.this.index = position;
				return false;
			}
		});

	}

	/**
	 * Adds a menu Point for the About Informations
	 * 
	 * @param Menu menu
	 *            
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("About");
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Handles the click on about in the optionsMenu
	 * 
	 * @param MenuItem item
	 *   
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getTitle() == "About") {
			Intent aboutIntent = new Intent(GalleryActivity.this,
					AboutActivity.class);
			GalleryActivity.this.startActivity(aboutIntent);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * Creates some menu entries and a submenu for the ContextMenu
	 * 
	 * @param ContextMenu menu
	 * @param View v 
	 * @param ContextMenuInfo menuInfo
	 * 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.setHeaderTitle("Picture Options");
		menu.add(0, v.getId(), 0, "Delete selected Picture");
		SubMenu subMenu = menu.addSubMenu(0, v.getId(), 0,
				"Delete all Pictures");
		subMenu.add(0, YES, 0, "Yes");
		subMenu.add(0, NO, 0, "No");
	}

	/**
	 * Handles the Events from the clicks on the items from the contextMenu
	 * 
	 * @param MenuItem item
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		FileManager fileManager = new FileManager(this.getApplicationContext());
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 4;
		if (item.getTitle() == "Delete selected Picture") {

			fileManager.deleteImageFromGallery(DecodeBitmaps.fullImgNames
					.get(index));
			DecodeBitmaps.done = false;
			new DecodeBitmaps(NO);

			Toast.makeText(this.getApplicationContext(), "Picture Deleted",
					Toast.LENGTH_SHORT).show();
			onCreate(savedInsta);

		} else if (item.getTitle() == "Delete all Pictures") {

		} else if (item.getItemId() == YES) {
			for (String s : DecodeBitmaps.fullImgNames) {
				fileManager.deleteImageFromGallery(s);
			}
			Toast.makeText(this.getApplicationContext(),
					"All Pictures were deleted", Toast.LENGTH_SHORT).show();
			DecodeBitmaps.done = false;
			new DecodeBitmaps(NO);
			onCreate(savedInsta);

		} else if (item.getItemId() == NO) {
			DecodeBitmaps.done = false;
			new DecodeBitmaps(NO);
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
