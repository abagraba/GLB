package globj.core.utils;

/**
 * 
 * Quaternion q = a + bi + cj + dk.
 *
 */
public class UnitQuaternion {
	
	public float s, i, j, k;
	
	public UnitQuaternion(float a, float b, float c, float d) {
		this.s = a;
		this.i = b;
		this.j = c;
		this.k = d;
	}
	
	public UnitQuaternion(UnitQuaternion q) {
		this(q.s, q.i, q.j, q.k);
	}
	
	public UnitQuaternion() {
		this(1, 0, 0, 0);
	}
	
	public UnitQuaternion inverse() {
		return new UnitQuaternion(s, -i, -j, -k);
	}
	
	public UnitQuaternion product(UnitQuaternion q) {
		UnitQuaternion uq = new UnitQuaternion(s * q.s - i * q.i - j * q.j - k * q.k, 
				s * q.i + i * q.s + j * q.k - k * q.j, 
				s * q.j + j * q.s + k * q.i - i * q.k, 
				s * q.k + k * q.s + i * q.j - j * q.i).normalize();
		return uq;
	}
	
	public float dot(UnitQuaternion q) {
		return s * q.s + i * q.i + j * q.j + k * q.k;
	}
	
	public float angle(UnitQuaternion q) {
		return (float) Math.acos(dot(q));
	}
	
	public static UnitQuaternion rotation(V3f axis, float theta) {
		float s = (float) Math.sin(theta / 2);
		float c = (float) Math.cos(theta / 2);
		axis = axis.unit();
		return new UnitQuaternion(c, s * axis.x, s * axis.y, s * axis.z).normalize();
	}
	
	public UnitQuaternion rotate(UnitQuaternion rotation) {
		return rotation.product(this);
	}
	
	public UnitQuaternion slerp(UnitQuaternion target, float factor) {
		float angle = angle(target);
		factor = Math.max(0, Math.min(1, factor));
		if (angle == 0 || factor == 0)
			return new UnitQuaternion(this);
		if (factor == 1)
			return new UnitQuaternion(target);
		float sa = (float) Math.sin(angle);
		float s1 = (float) Math.sin(angle * (1 - factor));
		float s2 = (float) Math.sin(angle * factor);
		return new UnitQuaternion(this).multiply(s1 / sa).add(new UnitQuaternion(target).multiply(s2 / sa)).normalize();
	}
	
	private UnitQuaternion add(UnitQuaternion q) {
		s += q.s;
		i += q.i;
		j += q.j;
		k += q.k;
		return this;
	}
	
	private UnitQuaternion multiply(float f) {
		s *= f;
		i *= f;
		j *= f;
		k *= f;
		return this;
	}
	
	private UnitQuaternion normalize() {
		float norm = (float) Math.sqrt(s * s + i * i + j * j + k * k);
		float inorm = norm == 0 ? 0 : 1 / norm;
		return multiply(inorm);
	}

	public void set(UnitQuaternion q) {
		s = q.s;
		i = q.i;
		j = q.j;
		k = q.k;
		normalize();
	}
	
	public String toString(){
		return String.format("(%.2f, <%.2f, %.2f, %.2f>)", s, i, j, k); 
	}
	
}