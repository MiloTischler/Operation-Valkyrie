package valkyrie.filter.ascii.test;

import java.lang.reflect.Field;

import valkyrie.filter.ascii.Font;
import valkyrie.ui.LayoutManager;
import valkyrie.ui.MainActivity;
import junit.framework.TestCase;

public class FontTest extends TestCase {

	public FontTest() {
		super("valkyrie.filter.ascci");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	public void testFontName(){
		String name = "test";
		Font font = new Font(name, true);
		assertEquals(font.getName(),name);	
	}
	
	public void testFontLUT(){
		String name = "test";
		int[] lut = new int[256];
		Font font = new Font(name, true);		
		assertEquals(font.getLUT().length,lut.length);
		boolean fieldsNotSet = true;
		lut = font.getLUT();
		for(int i = 0; i < lut.length; i++)
		{
			if(!((lut[i] >= 32) && (lut[i] <= 126)))
				fieldsNotSet = false;
		}
		assertTrue(fieldsNotSet);
	}
	
	public void testFontInvertLUT(){
		String name = "test";
		int[] lut = new int[256];
		Font font = new Font(name, true);		
		assertEquals(font.getInvertLUT().length,lut.length);
		boolean fieldsNotSet = true;
		lut = font.getInvertLUT();
		for(int i = 0; i < lut.length; i++)
		{
			if(!((lut[i] >= 32) && (lut[i] <= 126)))
				fieldsNotSet = false;
		}
		assertTrue(fieldsNotSet);
	}
}
