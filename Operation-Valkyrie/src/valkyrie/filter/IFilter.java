package valkyrie.filter;

import java.util.Vector;

import android.graphics.Bitmap;

public interface IFilter {
	public void manipulatePreviewImage(Bitmap bitmap);
	public void manipulateImage(Bitmap bitmap);
	public Vector<Object> getUIElements();
}
