package lwjgl.test.ogldev.t4;

import globj.core.GL;
import globj.core.RenderCommand;
import globj.objects.bufferobjects.StaticVBO;
import globj.objects.bufferobjects.VBO;
import globj.objects.bufferobjects.VBOTarget;
import globj.objects.bufferobjects.VBOs;
import globj.objects.shaders.Program;
import globj.objects.shaders.Programs;
import globj.objects.shaders.Shader;
import globj.objects.shaders.ShaderType;
import globj.objects.shaders.Shaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Tutorial4 extends RenderCommand {
	
	VBO vbo;
	Shader vert;
	Shader frag;
	Program prog;
	int t;
	
	@Override
	public void init() {
		float[] vertices = new float[] { -1, -1, 0, 1, -1, 0, 0, 1, 0 };
		vbo = new StaticVBO("Test VBO", VBOTarget.ARRAY, vertices);
		GL11.glClearColor(0, 0, 0, 0);
		
		InputStream vin;
		InputStream fin;
		try {
			vin = new FileInputStream("src/lwjgl/test/ogldev/t4/shader.vs");
			fin = new FileInputStream("src/lwjgl/test/ogldev/t4/shader.fs");
			vert = Shaders.createShader("Vert", ShaderType.VERTEX, vin);
			frag = Shaders.createShader("Frag", ShaderType.FRAGMENT, fin);
		} catch (IOException e) {
			e.printStackTrace();
		}
		vert.debug();
		frag.debug();
		
		prog = Programs.createProgram("Test", vert, frag);
		
		prog.debug();
	}
	
	@Override
	public void uninit() {
		VBOs.destroyVBO(vbo.name);
		vbo = null;
	}
	
	@Override
	public void render() {
		prog.bind();

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL20.glEnableVertexAttribArray(0);
		vbo.bind();
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		vbo.bindNone();
		GL20.glDisableVertexAttribArray(0);
		
		prog.bindNone();
	}
	
	public static void main(String[] args) {
		GL.setTarget(new Tutorial4());
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