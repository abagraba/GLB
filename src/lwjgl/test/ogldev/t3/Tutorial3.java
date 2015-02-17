package lwjgl.test.ogldev.t3;

import globj.core.GL;
import globj.core.RenderCommand;
import globj.objects.bufferobjects.StaticVBO;
import globj.objects.bufferobjects.VBO;
import globj.objects.bufferobjects.VBOTarget;
import globj.objects.bufferobjects.VBOs;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Tutorial3 extends RenderCommand {
	
	VBO vbo;
	
	@Override
	public void init() {
		float[] vertices = new float[] { -1, -1, 0, 1, -1, 0, 0, 1, 0 };
		vbo = new StaticVBO("Test VBO", VBOTarget.ARRAY, vertices);
		GL11.glClearColor(0, 0, 0, 0);
	}
	
	@Override
	public void uninit() {
		VBOs.destroyVBO(vbo.name);
		vbo = null;
	}
	
	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL20.glEnableVertexAttribArray(0);
		vbo.bind();
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		vbo.bindNone();
		GL20.glDisableVertexAttribArray(0);
	}
	
	public static void main(String[] args) {
		GL.setTarget(new Tutorial3());
		try {
			GL.startGL();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void input() {
		while (Keyboard.next()) {
			switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_ESCAPE:
					GL.close();
					break;
				case Keyboard.KEY_F11:
					if (!Keyboard.getEventKeyState())
						GL.toggleFS();
					break;
			}
		}
	}
	
}