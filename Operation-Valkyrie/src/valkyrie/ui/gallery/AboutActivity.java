package valkyrie.ui.gallery;

import valkyrie.main.R;
import android.app.Activity;
import android.os.Bundle;


/**
 * Just a new Activity which sets the about layout and display it.
 * 
 *
 */
public class AboutActivity extends Activity {

	/**
	 * Just start the Actitvity with the specific layout
	 * 
	 * @param Bundle savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

	}

}
