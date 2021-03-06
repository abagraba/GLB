package globj.objects.shaders;


import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL43;

import globj.core.Window;
import globj.objects.GLObject;
import lwjgl.debug.GLDebug;



@NonNullByDefault
public class ShaderInput extends GLObject {
	
	private final int				location;
	private final ShaderUniformType	type;
	private final int				arraySize;
	
	protected List<ShaderType> shaders = new ArrayList<ShaderType>();
	
	private ShaderInput(int program, int index, boolean legacy) {
		super(name31(program, index), index);
		this.location = GL20.glGetAttribLocation(program, name);
		IntBuffer size = BufferUtils.createIntBuffer(1);
		IntBuffer type = BufferUtils.createIntBuffer(1);
		GL20.glGetActiveAttrib(program, index, 0, size, type);
		this.arraySize = size.get();
		ShaderUniformType uniformtype = ShaderUniformType.get(type.get());
		if (uniformtype == null) {
			GLDebug.write("Invalid ShaderUniformType.");
			uniformtype = ShaderUniformType.FLOAT_VEC4;
		}
		this.type = uniformtype;
		if (legacy)
			GLDebug.write("Using legacy ShaderInput.");
	}
	
	private ShaderInput(int program, int index) {
		super(name43(program, index), index);
		IntBuffer res = getResource(program, new int[] { GL43.GL_LOCATION, GL43.GL_ARRAY_SIZE, GL43.GL_TYPE }, index, 3);
		location = res.get();
		arraySize = res.get();
		ShaderUniformType uniformtype = ShaderUniformType.get(res.get());
		if (uniformtype == null) {
			GLDebug.write("Invalid ShaderUniformType.");
			uniformtype = ShaderUniformType.FLOAT_VEC4;
		}
		type = uniformtype;
	}
	
	/**************************************************/
	
	public static ShaderInput buildInput(int program, int index) {
		if (Window.versionCheck(4, 3))
			return new ShaderInput(program, index);
		else
			return new ShaderInput(program, index, true);
	}
	
	/**************************************************
	 ********************** Util **********************
	 **************************************************/
	
	@SuppressWarnings("all")
	private static IntBuffer getResource(int program, int[] args, int index, int results) {
		IntBuffer req = BufferUtils.createIntBuffer(args.length);
		IntBuffer res = BufferUtils.createIntBuffer(results);
		req.put(args).flip();
		GL43.glGetProgramResourceiv(program, GL43.GL_PROGRAM_INPUT, index, req, null, res);
		return res;
	}
	
	private static int getResource(int program, int[] args, int index) {
		return getResource(program, args, index, 1).get();
	}
	
	@SuppressWarnings("all")
	private static String name31(int program, int index) {
		return GL20.glGetActiveAttrib(program, index, null, null);
	}
	
	@SuppressWarnings("all")
	private static String name43(int program, int index) {
		int length = getResource(program, new int[] { GL43.GL_NAME_LENGTH }, index);
		return GL43.glGetProgramResourceName(program, GL43.GL_PROGRAM_INPUT, index, length);
	}
	
	/**************************************************
	 ********************* Getters ********************
	 **************************************************/
	/**
	 * @return the location of this shader input
	 */
	public int location() {
		return location;
	}
	
	/**
	 * @return the type of this shader input
	 */
	public ShaderUniformType type() {
		return type;
	}
	
	/**
	 * @return the array size of this shader input
	 */
	public int arraySize() {
		return arraySize;
	}
	
	/**************************************************
	 ********************** Debug *********************
	 **************************************************/
	@Override
	public void debug() {
		GLDebug.write(this);
	}
	
	@Override
	public void debugQuery() {
		GLDebug.write(this);
	}
	
	private String typeName() {
		if (arraySize > 1)
			return type + " [" + arraySize + "]";
		return type.toString();
	}
	
	@Override
	@Nullable
	public String toString() {
		if (location != -1)
			return String.format("[%d]%24s\t%s", location, name, typeName());
		return String.format("\t%s\t%s", name, typeName());
	}
}
