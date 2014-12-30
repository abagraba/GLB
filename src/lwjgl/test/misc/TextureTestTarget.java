package lwjgl.test.misc;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

import lwjgl.core.GL;
import lwjgl.core.RenderTarget;
import lwjgl.core.VBO;
import lwjgl.core.texture.MagnifyFilter;
import lwjgl.core.texture.MinifyFilter;
import lwjgl.core.texture.Texture;
import lwjgl.core.texture.Texture2D;
import lwjgl.core.texture.Texture2DDataTarget;
import lwjgl.core.texture.Texture2DTarget;
import lwjgl.core.texture.TextureFormat;
import lwjgl.debug.Logging;

/**
 * Test Target to test functionality of Vertex Buffer Objects. <br/>
 * <br/>
 * For more information, visit <a
 * href="http://www.ozone3d.net/tutorials/opengl_vbo.php"
 * >http://www.ozone3d.net/tutorials/opengl_vbo.php</a>
 *
 */
public class TextureTestTarget extends RenderTarget {
	
	private static float theta = 0;
	private static float rps = (float) (2 * Math.PI) / 6;
	
	private VBO vertices, tcoords;
	
	@Override
	public void init() {
		
		FloatBuffer v = BufferUtils.createFloatBuffer(12);
		FloatBuffer t = BufferUtils.createFloatBuffer(12);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		try {
			Texture2D tex = Texture2D.create("Test", Texture2DTarget.TEXTURE_2D);
			tex.setFilter(MinifyFilter.NEAREST, MagnifyFilter.NEAREST);
			tex.setData(Texture2DDataTarget.TEXTURE_2D, ImageIO.read(new File("src/lwjgl/test/misc/Untitled.png")), 0, TextureFormat.RGBA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		v.put(new float[] { -1, -1, -1, 1, 1, 1, 1, -1, 1, 1, -1, -1 }).flip();
		t.put(new float[] { 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0 }).flip();
		
		Logging.logObject(Texture2D.get("Test"));
		
		vertices = new VBO(GL15.GL_ARRAY_BUFFER);
		vertices.bufferData(v);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);
		
		tcoords = new VBO(GL15.GL_ARRAY_BUFFER);
		tcoords.bufferData(t);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float aspect = 2 * (float) Display.getWidth() / Display.getHeight();
		GL11.glOrtho(-aspect, aspect, -2, 2, -1, 1);
		
	}
	
	@Override
	public void uninit() {
		vertices.deleteBuffer();
		tcoords.deleteBuffer();
	}
	
	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		Texture2D.get("Test").bind();
		
		Logging.logObject(Texture2D.get("Test"));
		
		GL11.glRotatef(theta, 0, 0, 1);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		//Logging.logInfo(Texture.status());
		//Logging.logInfo(Texture.constants());
		
		
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
	
	public static void main(String[] args) {
		GL.setTarget(new TextureTestTarget());
		try {
			GL.startGL();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean l, r;
	
	@Override
	public void input() {
		while (Keyboard.next()) {
			switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_LEFT:
					l = Keyboard.getEventKeyState();
					break;
				case Keyboard.KEY_RIGHT:
					r = Keyboard.getEventKeyState();
					break;
				case Keyboard.KEY_ESCAPE:
					GL.close();
					break;
			}
		}
		
		if (l)
			theta += rps * 0.001f * GL.deltaTime();
		if (r)
			theta -= rps * 0.001f * GL.deltaTime();
	}
	
}