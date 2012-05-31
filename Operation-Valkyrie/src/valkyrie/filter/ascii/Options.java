package valkyrie.filter.ascii;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

public class Options {

	private int[] pixelMatrix;
	private int contrast;
	private int fontSize;
	private String font;
	private Color color;
	private Color backgroundColor;
	private boolean antiAliasing;
	private boolean isColor;

	private ArrayList<View> uiElements;

	public Options() {
		uiElements.add(null);
	}
}
