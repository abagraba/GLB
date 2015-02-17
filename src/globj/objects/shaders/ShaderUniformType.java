package globj.objects.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

public enum ShaderUniformType {
	BOOL("Boolean", GL20.GL_BOOL),
	BOOL_VEC2("Boolean Vec2", GL20.GL_BOOL_VEC2),
	BOOL_VEC3("Boolean Vec3", GL20.GL_BOOL_VEC3),
	BOOL_VEC4("Boolean Vec4", GL20.GL_BOOL_VEC4),
	INT("Integer", GL11.GL_INT),
	INT_VEC2("Integer Vec2", GL20.GL_INT_VEC2),
	INT_VEC3("Integer Vec3", GL20.GL_INT_VEC3),
	INT_VEC4("Integer Vec4", GL20.GL_INT_VEC4),
	UNSIGNED_INT("Unsigned Integer", GL11.GL_UNSIGNED_INT),
	UNSIGNED_INT_VEC2("Unsigned Integer Vec2", GL30.GL_UNSIGNED_INT_VEC2),
	UNSIGNED_INT_VEC3("Unsigned Integer Vec3", GL30.GL_UNSIGNED_INT_VEC3),
	UNSIGNED_INT_VEC4("Unsigned Integer Vec4", GL30.GL_UNSIGNED_INT_VEC4),
	FLOAT("Float", GL11.GL_FLOAT),
	FLOAT_VEC2("Float Vec2", GL20.GL_FLOAT_VEC2),
	FLOAT_VEC3("Float Vec3", GL20.GL_FLOAT_VEC3),
	FLOAT_VEC4("Float Vec4", GL20.GL_FLOAT_VEC4),
	DOUBLE("Double", GL11.GL_DOUBLE),
	DOUBLE_VEC2("Double Vec2", GL40.GL_DOUBLE_VEC2),
	DOUBLE_VEC3("Double Vec3", GL40.GL_DOUBLE_VEC3),
	DOUBLE_VEC4("Double Vec4", GL40.GL_DOUBLE_VEC4),
	FLOAT_MAT2("Float Matrix [2x2]", GL20.GL_FLOAT_MAT2),
	FLOAT_MAT3("Float Matrix [3x3]", GL20.GL_FLOAT_MAT3),
	FLOAT_MAT4("Float Matrix [4x4]", GL20.GL_FLOAT_MAT4),
	FLOAT_MAT2x3("Float Matrix [2x3]", GL21.GL_FLOAT_MAT2x3),
	FLOAT_MAT2x4("Float Matrix [2x4]", GL21.GL_FLOAT_MAT2x4),
	FLOAT_MAT3x2("Float Matrix [3x2]", GL21.GL_FLOAT_MAT3x2),
	FLOAT_MAT3x4("Float Matrix [3x4]", GL21.GL_FLOAT_MAT3x4),
	FLOAT_MAT4x2("Float Matrix [4x2]", GL21.GL_FLOAT_MAT4x2),
	FLOAT_MAT4x3("Float Matrix [4x3]", GL21.GL_FLOAT_MAT4x3),
	DOUBLE_MAT2("Double Matrix [2x2]", GL40.GL_DOUBLE_MAT2),
	DOUBLE_MAT3("Double Matrix [3x3]", GL40.GL_DOUBLE_MAT3),
	DOUBLE_MAT4("Double Matrix [4x4]", GL40.GL_DOUBLE_MAT4),
	DOUBLE_MAT2x3("Double Matrix [2x3]", GL40.GL_DOUBLE_MAT2x3),
	DOUBLE_MAT2x4("Double Matrix [2x4]", GL40.GL_DOUBLE_MAT2x4),
	DOUBLE_MAT3x2("Double Matrix [3x2]", GL40.GL_DOUBLE_MAT3x2),
	DOUBLE_MAT3x4("Double Matrix [3x4]", GL40.GL_DOUBLE_MAT3x4),
	DOUBLE_MAT4x2("Double Matrix [4x2]", GL40.GL_DOUBLE_MAT4x2),
	DOUBLE_MAT4x3("Double Matrix [4x3]", GL40.GL_DOUBLE_MAT4x3),
	
	SAMPLER_1D("1D Texture Sampler", GL20.GL_SAMPLER_1D),
	SAMPLER_1D_INT("1D Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_1D),
	SAMPLER_1D_UINT("1D Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_1D),
	SAMPLER_1D_SHADOW("1D Shadow Texture Sampler", GL20.GL_SAMPLER_1D_SHADOW),
	
	SAMPLER_2D("2D Texture Sampler", GL20.GL_SAMPLER_2D),
	SAMPLER_2D_INT("2D Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_2D),
	SAMPLER_2D_UINT("2D Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_2D),
	SAMPLER_2D_SHADOW("2D Shadow Texture Sampler", GL20.GL_SAMPLER_2D_SHADOW),
	
	SAMPLER_3D("3D Texture Sampler", GL20.GL_SAMPLER_3D),
	SAMPLER_3D_INT("3D Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_3D),
	SAMPLER_3D_UINT("3D Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_3D),
	
	SAMPLER_1D_ARRAY("1D Texture Array Sampler", GL30.GL_SAMPLER_1D_ARRAY),
	SAMPLER_1D_ARRAY_INT("1D Texture Array Sampler [Integer]", GL30.GL_INT_SAMPLER_1D_ARRAY),
	SAMPLER_1D_ARRAY_UINT("1D Texture Array Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_1D_ARRAY),
	SAMPLER_1D_SHADOW_ARRAY("1D Shadow Texture Array Sampler", GL30.GL_SAMPLER_1D_ARRAY_SHADOW),
	
	SAMPLER_2D_ARRAY("2D Texture Array Sampler", GL30.GL_SAMPLER_2D_ARRAY),
	SAMPLER_2D_ARRAY_INT("2D Texture Array Sampler [Integer]", GL30.GL_INT_SAMPLER_2D_ARRAY),
	SAMPLER_2D_ARRAY_UINT("2D Texture Array Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_2D_ARRAY),
	SAMPLER_2D_SHADOW_ARRAY("2D Shadow Texture Array Sampler", GL30.GL_SAMPLER_2D_ARRAY_SHADOW),
	
	SAMPLER_RECTANGLE("Rectangle Texture Sampler", GL31.GL_SAMPLER_2D_RECT),
	SAMPLER_RECTANGLE_INT("Rectangle Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_2D_RECT),
	SAMPLER_RECTANGLE_UINT("Rectangle Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_2D_RECT),
	SAMPLER_RECTANGLE_SHADOW("Rectangle Shadow Texture Sampler", GL31.GL_SAMPLER_2D_RECT_SHADOW),
	
	SAMPLER_CUBE("Cubemap Texture Sampler", GL20.GL_SAMPLER_CUBE),
	SAMPLER_CUBE_INT("Cubemap Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_CUBE),
	SAMPLER_CUBE_UINT("Cubemap Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_CUBE),
	SAMPLER_CUBE_SHADOW("Cubemap Shadow Texture Sampler", GL30.GL_SAMPLER_CUBE_SHADOW),
	
	SAMPLER_2D_MULTISAMPLE("2D Multisample Texture Sampler", GL32.GL_SAMPLER_2D_MULTISAMPLE),
	SAMPLER_2D_MULTISAMPLE_INT("2D Multisample Texture Sampler [Integer]", GL32.GL_INT_SAMPLER_2D_MULTISAMPLE),
	SAMPLER_2D_MULTISAMPLE_UINT("2D Multisample Texture Sampler [Unsigned Integer]", GL32.GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE),
	
	SAMPLER_2D_MULTISAMPLE_ARRAY("2D Multisample Texture Array Sampler", GL32.GL_SAMPLER_2D_MULTISAMPLE_ARRAY),
	SAMPLER_2D_MULTISAMPLE_ARRAY_INT("2D Multisample Texture Array Sampler [Integer]", GL32.GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY),
	SAMPLER_2D_MULTISAMPLE_ARRAY_UINT("2D Multisample Texture Array Sampler [Unsigned Integer]", GL32.GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY),
	
	SAMPLER_BUFFER("Buffer Texture Sampler", GL30.GL_SAMPLER_BUFFER),
	SAMPLER_BUFFER_INT("Buffer Texture Sampler [Integer]", GL30.GL_INT_SAMPLER_BUFFER),
	SAMPLER_BUFFER_UINT("Buffer Texture Sampler [Unsigned Integer]", GL30.GL_UNSIGNED_INT_SAMPLER_BUFFER);
	
	public final String name;
	public final int value;
	
	private ShaderUniformType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static ShaderUniformType get(int i) {
		for (ShaderUniformType target : values())
			if (target.value == i)
				return target;
		return null;
	}
	
	public String toString() {
		return name;
	}
	
}