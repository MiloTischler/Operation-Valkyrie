package valkyrie.ui;



import valkyrie.main.R;
import android.app.Activity;
import android.os.Bundle;
import gueei.binding.observables.IntegerObservable;

@Deprecated
public class ColorPickerActivity extends Activity {
    
	public final IntegerObservable Color = new IntegerObservable(android.graphics.Color.RED);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }
}