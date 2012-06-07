package valkyrie.ui.preview;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * 
 * COPYRIGHT: Paul Neuhold, Laurenz Theuerkauf, Alexander Ritz, Jakob Schweighofer, Milo Tischler
 * © Milo Tischler, Jakob Schweighofer, Alexander Ritz, Paul Neuhold, Laurenz Theuerkauf
 * 
 */
public class CameraPreviewRendererGL implements Renderer {
	private static final String TAG = "CameraPreviewRendererGL";

	// buffer holding the vertices
	private FloatBuffer vertexBuffer;	

	private final float vertices[] = {
			-1.0f, -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f, -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f			// V4 - top right
	};

	// buffer holding the texture coordinates
	private FloatBuffer textureBuffer;	
	
	// Mapping coordinates for the vertices
	private final float texture[] = { 
			0.0f, 0.625f,		// top left		(V2)
			0.9375f, 0.625f,		// bottom left	(V1)
			0.0f, 0.0f,		// top right	(V4)
			0.9375f, 0.0f		// bottom right	(V3)
	};
	
	private int[] textures = new int[1];
	
	private Bitmap bitmap = null;
	
	private boolean loaded = false;

	public CameraPreviewRendererGL() {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		// allocates the memory from the byte buffer
		this.vertexBuffer = byteBuffer.asFloatBuffer();
		// fill the vertexBuffer with the vertices
		this.vertexBuffer.put(vertices);
		// set the cursor position to the beginning of the buffer
		this.vertexBuffer.position(0);
		
		// same for texture
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Reset the Modelview Matrix
		gl.glLoadIdentity();
		
		//Ähm.. lol
		gl.glTranslatef(0.0f, 0.0f, -1.0f);
		
		// Load the texture for the square
		if(this.loaded == false && this.bitmap != null) {
			this.loadGLTexture(gl, this.bitmap);
			this.loaded = true;
		} else if(this.bitmap != null) {
			this.updateGLTexture(gl, this.bitmap);
		}
		
		// Drawing
		// gl.glTranslatef(0.0f, 0.0f, -5.0f); // move 5 units INTO the screen is the same as moving the camera 5 units away
		this.drawSquare(gl);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do

		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();

	    gl.glOrthof(0, 480f, 800f, 0, 0, 1);
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, false);
	}
	

//	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
//		int width = bm.getWidth();
//		int height = bm.getHeight();
//		
//		float scaleWidth = ((float) newWidth) / width;
//		float scaleHeight = ((float) newHeight) / height;
//		
//		// create a matrix for the manipulation
//		Matrix matrix = new Matrix();
//		
//		// resize the bit map
//		matrix.postScale(scaleWidth, scaleHeight);
//		
//		// recreate the new Bitmap
//		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
//		
//		bm.recycle();
//	
//		return resizedBitmap;
//	}


		
	private void drawSquare(GL10 gl) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		//gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	private void loadGLTexture(GL10 gl, Bitmap bitmap) {
		// generate one texture pointer
		gl.glDeleteTextures(1, textures, 0);
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}
	
	private void updateGLTexture(GL10 gl, Bitmap bitmap) {
		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}
}
