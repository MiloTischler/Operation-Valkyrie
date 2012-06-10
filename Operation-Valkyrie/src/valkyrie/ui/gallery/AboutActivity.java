package valkyrie.ui.gallery;

import valkyrie.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//just a new activity with a xml layout files for displaying the about informations
public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

	}

}
