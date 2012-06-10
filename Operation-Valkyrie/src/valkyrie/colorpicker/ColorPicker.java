package valkyrie.colorpicker;


import valkyrie.colorpicker.ColorPickerDialog.OnColorChangedListener;
import valkyrie.main.R;
import valkyrie.ui.LayoutManager;
import gueei.binding.Binder;
import gueei.binding.IBindableView;
import gueei.binding.ViewAttribute;
import gueei.binding.listeners.OnClickListenerMulticast;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.AttributeSet;  
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ColorPicker extends TextView implements IBindableView<ColorPicker>, View.OnClickListener,
		OnColorChangedListener {
	
	ColorPickerDialog dialog = null;
//	OnColorChangedListener listener = null;

	public ColorPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ColorPicker(Context context) {
		super(context);
		init();
	}

	private void init() {
		Binder.getMulticastListenerForView(this, OnClickListenerMulticast.class).register(this);
		
	}

	public ViewAttribute<? extends View, ?> createViewAttribute(String arg0) {
		if (arg0.equals("color"))
			return mColorAttr;
		return null;
	}

	public void onClick(View v) {
		// Bring up dialog
		ColorPickerDialog dialog = new ColorPickerDialog(getContext(), this, mColorAttr.get());
		dialog.show();
	}

	public void colorChanged(int color) {
		mColorAttr.set(color);
		
		Log.d("ColorPicker","Current changed color is: " + Integer.toHexString(color) );
		Log.d("ColorPicker","view ID: " + Integer.toHexString(this.getId()));
		if(this.getId() == R.id.foregroundcolor) {
		Log.d("ColorPicker","view Tag: Foregroundcolor " );
		
		SharedPreferences options = LayoutManager.getInstance().getSharedPreferencesOfCurrentFilter();
		SharedPreferences.Editor editor = options.edit();
		editor.putInt("foreground", color);
		editor.commit();
		}
		else if(this.getId() == R.id.backgroundcolor) {
			Log.d("ColorPicker","view Tag: Backgroundcolor " );
		
		SharedPreferences options = LayoutManager.getInstance().getSharedPreferencesOfCurrentFilter();
		SharedPreferences.Editor editor = options.edit();
		editor.putInt("background", color);
		editor.commit();
		}
	}
	
//	public void setOnColorChangedListener(OnColorChangedListener l) {
//		listener = l;
//	}

	private ColorAttribute mColorAttr = new ColorAttribute(this);

	public class ColorAttribute extends ViewAttribute<ColorPicker, Integer> {
		public ColorAttribute(ColorPicker view) {
			super(Integer.class, view, "color");
		}

		private int mValue = 0;

		@Override
		protected void doSetAttributeValue(Object arg0) {
			if (arg0 instanceof Integer) {
				getView().setBackgroundColor((Integer) arg0);
				mValue = (Integer) arg0;
				return;
			}
			mValue = 0;
			getView().setBackgroundColor(Color.RED);
		}

		@Override
		public Integer get() {
			return mValue;
		}
	}

	

	

}
