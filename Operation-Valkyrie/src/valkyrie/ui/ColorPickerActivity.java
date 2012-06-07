package valkyrie.ui;



import valkyrie.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import gueei.binding.observables.IntegerObservable;

public class ColorPickerActivity extends Activity {
    
	public final IntegerObservable Color = new IntegerObservable(android.graphics.Color.RED);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ColorPicker","set content View");
        setContentView(R.layout.main);
    }
}