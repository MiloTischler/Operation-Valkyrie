package valkyrie.filter;

import java.util.Vector;

import android.graphics.Bitmap;

<<<<<<< HEAD

=======
/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf 
 *
 */
>>>>>>> 16dbdb09781b533d4f9aa5ad31c983b519a01c9d
public interface IFilter {
	public String getName();
	public Bitmap getIcon();
	public void manipulatePreviewImage(Bitmap bitmap);
	public void manipulateImage(Bitmap bitmap);
	public Vector<Object> getUIElements();
}
