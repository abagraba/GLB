package control;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import globj.core.Window;



public class KeyboardPress implements ControlState {
	
	private boolean	lastValue;
	private boolean	changed;
	
	private final String	name;
	private final int		key;
	private Window			window;
	
	public KeyboardPress(String name, int key) {
		this.name = name;
		this.key = key;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public boolean state() {
		return changed;
	}
	
	@Override
	public void update() {
		boolean down = GLFW.glfwGetKey(window.window(), key) != GL11.GL_FALSE;
		changed = down && !lastValue;
		lastValue = down;
	}
	
	@Override
	public void setWindow(Window window) {
		this.window = window;
	}
	
}
