package valkyrie.colorpicker;

import valkyrie.colorpicker.ColorPickerDialog.OnColorChangedListener;
import valkyrie.ui.LayoutManager;
import gueei.binding.Binder;
import gueei.binding.IBindableView;
import gueei.binding.ViewAttribute;
import gueei.binding.listeners.OnClickListenerMulticast;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;  
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ColorPicker extends Button implements IBindableView<ColorPicker>, View.OnClickListener,
		OnColorChangedListener {

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
//		Log.d("ColorPicker","Current changed color is: " + mColorAttr.get() );
		dialog.show();
	}

	public void colorChanged(int color) {
		mColorAttr.set(color);
		Log.d("ColorPicker","Current changed color is: " + Integer.toHexString(color));
		Log.d("ColorPicker","view ID: " + Integer.toHexString(this.getId()));
	}

	private ColorAttribute mColorAttr = new ColorAttribute(this);
	private String id ;

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
			id = getView().getTag().toString();
			getView().setBackgroundColor(Color.RED);
		}

		@Override
		public Integer get() {
			return mValue;
		}
	}

	

	

}
