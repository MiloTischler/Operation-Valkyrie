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
	private String SDPATH = Environment.getExternalStorageState() + Environment.DIRECTORY_PICTURES;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		// save some pictures from drawable to sdcard for gallery preview
		FileManager fileManager = new FileManager();
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sample_0);
		
		fileManager.saveImageToGallery(bitmap);
	
		GridView gallery = (GridView) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				FileManager fileManager = new FileManager();
				fileManager.deleteImageFromGallery(fileManager.getLatestImage());
			}
		});

	}

	@Override
	public void onPause() {
		super.onPause();
		ImageAdapter rycBmp = new ImageAdapter(this);
		rycBmp.recycleBmps();


	}

}
