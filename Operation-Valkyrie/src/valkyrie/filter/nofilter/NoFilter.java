package valkyrie.filter.nofilter;

import java.util.Vector;

import android.graphics.Bitmap;
import valkyrie.filter.IFilter;

public class NoFilter implements IFilter{

	public void manipulatePreviewImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		
	}

	public void manipulateImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		
	}

	public Vector<Object> getUIElements() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
