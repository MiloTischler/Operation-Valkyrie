package valkyrie.colorpicker;

import gueei.binding.Binder;
import android.app.Application;

public class ColorPickerApplication extends Application {

	@Override
    public void onCreate() {
	    super.onCreate();
	    Binder.init(this);
    }
}
