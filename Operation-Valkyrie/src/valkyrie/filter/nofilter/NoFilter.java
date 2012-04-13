package valkyrie.filter.nofilter;

import java.util.Vector;

import android.graphics.Bitmap;
import valkyrie.filter.IFilter;

public class NoFilter implements IFilter{

	public void manipulatePreviewImage(Bitmap bitmap) {

	}

	public void manipulateImage(Bitmap bitmap) {

	}

	public Vector<Object> getUIElements() {
		return new Vector<Object>();
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
