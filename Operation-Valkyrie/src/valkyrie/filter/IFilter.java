package valkyrie.filter;

import java.util.Vector;

import android.graphics.Bitmap;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
public interface IFilter {
	public String getName();
	public Bitmap getIcon();
	public void manipulatePreviewImage(Bitmap bitmap);
	public void manipulateImage(Bitmap bitmap);
	public Vector<Object> getUIElements();
}
