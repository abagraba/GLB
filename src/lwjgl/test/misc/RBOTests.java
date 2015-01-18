package lwjgl.test.misc;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;

import lwjgl.core.GL;
import lwjgl.core.RenderCommand;
import lwjgl.core.objects.framebuffers.RBO;
import lwjgl.debug.Logging;

public class RBOTests extends RenderCommand {
	
	boolean r = true;
	
	public static void main(String[] args) {
		GL.setTarget(new RBOTests());
		try {
			GL.startGL();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void uninit() {
		
	}
	
	@Override
	public void input() {
	}
	
	@Override
	public void render() {
		if (r) {
			Logging.debug(RBO.create("Test RBO", 100, 800, GL11.GL_DEPTH_COMPONENT));
			Logging.debug(RBO.create("Rocks", 1000, 800, GL11.GL_RGBA));
			Logging.debug(RBO.create("Coconut", 18000, 800, GL11.GL_RGBA));
			Logging.debug(RBO.create("Test RBO", 100, 800, GL11.GL_DEPTH_COMPONENT));
		}
		r = false;
	}
	
}
