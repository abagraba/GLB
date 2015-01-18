package lwjgl.core;

public class V4f {
	
	public float x, y, z, w;
	
	public V4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public String toString() {
		return String.format("(%.4f, %.4f %.4f, %.4f)", x, y, z, w);
	}
	
}
