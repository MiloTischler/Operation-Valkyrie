package valkyrie.filter.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import valkyrie.filter.FilterInternalStorage;
import valkyrie.filter.nofilter.NoFilter;
import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class FilterInternalStorageTest extends AndroidTestCase {
	private static final String TAG = "FilterInternalStorageTest";

	public FilterInternalStorageTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testWriteAndOpenFile() {
		this.writeAndOpenFile("test.txt");
	}

	public void testRootFolderExists() {
		NoFilter noFilter = new NoFilter();

		FilterInternalStorage internalStorage = new FilterInternalStorage(noFilter, this.getContext());

		String filterPath = new String(noFilter.getClass().getSimpleName());

		File filterFolder = this.getContext().getDir(filterPath, Context.MODE_PRIVATE);

		assertTrue(filterFolder.exists());
		assertTrue(filterFolder.isDirectory());

		deleteDir(filterFolder);

		assertFalse(internalStorage.rootFolderExists());
		assertFalse(filterFolder.isDirectory());
		assertFalse(filterFolder.exists());
	}

	private void writeAndOpenFile(String path) {
		NoFilter noFilter = new NoFilter();

		FilterInternalStorage internalStorage = new FilterInternalStorage(noFilter, this.getContext());

		assertNotNull(internalStorage);

		String fileContent = "That's a testcase of the class " + TAG;
		String fileName = path;

		// lets write a file
		FileOutputStream out = null;

		byte[] buffer = fileContent.getBytes();

		try {
			out = internalStorage.openFileOutput(fileName);

			assertNotNull(out);

			for (int ch = 0; ch < buffer.length; ch++) {
				out.write(buffer[ch]);
			}

			out.close();
			out.flush();
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}

		// lets open the file
		FileInputStream in = null;

		StringBuffer fileContentComp = new StringBuffer();

		try {
			in = internalStorage.openFileInput(fileName);

			assertNotNull(in);

			int ch;

			while ((ch = in.read()) != -1) {
				fileContentComp.append((char) ch);
			}
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}

		// compare the content
		assertTrue(fileContent.equals(fileContentComp.toString()));
	}

	/**
	 * QUELLE: http://javaalmanac.com/egs/java.io/DeleteDir.html
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
}
