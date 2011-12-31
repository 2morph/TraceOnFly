package palisade.particles;


import java.nio.FloatBuffer;

public class Vector3D {

	protected float x;
	protected float y;
	protected float z;

	public float[] toArray() {
		return new float[]{x,y,z};
	}
	
	public FloatBuffer toBuffer() {
		FloatBuffer b = BufferUtils.createFloatBuffer(3);
		b.put(toArray());
		b.rewind();
		return b;
	}
	
	public Vector3D() {
	}
	
	//public Vector3D(Vector4D v) {
	//	this.x = v.getX();
	//	this.y = v.getY();
	//	this.z = v.getZ();
	//}
	
	public Vector3D(Vector3D v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}

	public Vector3D(float n) {
		x = y = z = n;
	}
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//public Vector3D(Coordinate c) {
	//	this.x = (float)c.getX();
	//	this.y = (float)c.getY();
	//	this.z = (float)c.getZ();
	//}
	
	public Vector3D(float[] row) {
		x = row[0];
		y = row[1];
		z = row[2];
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3D v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	public void multiply(float n) {
		this.x *= n;
		this.y *= n;
		this.z *= n;
	}
	
	public void multiply(Vector3D v) {
		this.x *= v.getX();
		this.y *= v.getY();
		this.z *= v.getZ();
	}
	
	public Vector3D multiply(Matrix m) {
		return new Vector3D(x * m._11 + y * m._21 + z * m._31 + m._41,
							x * m._12 + y * m._22 + z * m._32 + m._42,
							x * m._13 + y * m._23 + z * m._33 + m._43);
	}
	
	public void divide(float n) {
		float r = 1.0f / n;
		x *= r; y *= r; z *= r;
	}
	
	public void add(Vector3D v) {
		this.x += v.getX();
		this.y += v.getY();
		this.z += v.getZ();
	}
	
	public void subtract(Vector3D v) {
		this.x -= v.getX();
		this.y -= v.getY();
		this.z -= v.getZ();
	}
	
	public float dot(Vector3D v) {
		return	(this.x * v.getX()) +
				(this.y * v.getY()) +
				(this.z * v.getZ());
	}
	
	public void cross(Vector3D v) {
		this.x = this.y * v.getZ() - this.z * v.getY();
		this.y = this.x * v.getZ() - this.z * v.getX();
		this.z = this.x * v.getY() - this.y * v.getX();
	}
	
	public float length() {
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public float fastLength() {
		return x*x + y*y + z*z;
	}
	
	public void scale(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	public void mask(Vector3D v0, Vector3D v1) {
		x = v0.getX() != 0.0f ? v1.getX() : x;
		y = v0.getY() != 0.0f ? v1.getY() : y;
		z = v0.getZ() != 0.0f ? v1.getZ() : z;
	}
	
	// TODO: interpret vector
	//public void interp(Vector4D v, float n) {
		//return interp(v, n);
	//}

	public void normalize() {
		float ln = length();
		if (ln == 0.0f) return;
		float div = 1.0f / ln;
		x *= div;
		y *= div;
		z *= div;
	}
	
	public Vector3D normal() {
		Vector3D v = new Vector3D(this);
		v.normalize();
		return v;
	}
	
	public Vector3D normTri(Vector3D v0, Vector3D v1) {
		v0.subtract(this);
		v1.subtract(this);
		v0.multiply(v1);
		v0.normalize();
		return v0;
	}
	
	public float angle(Vector3D v) {
		return normal().dot(v.normal());
	}
	
	public float compare(Vector3D v0, Vector3D v1) {
		return Math.signum(dot(v0) - dot(v1));
	}
	
	public float sectPlane(Vector3D p, Vector3D v, Vector3D n) {
		float t = n.dot(this);
		if (t != 0.0f) {
			v.subtract(p);
			return n.dot(v) / t;
		}
		return Float.MAX_VALUE;
	}
	
	public Vector3D project(Vector3D v) {
		Vector3D t = v.normal();
		t.multiply(dot(t));
		return t;
	}
	
	public Vector3D ortho(Vector3D v) {
		Vector3D o = new Vector3D(this);
		o.subtract(project(v));
		return o;
	}
	
	public void reflect(Vector3D n) {
		if (angle(n) < 0.0f) {
			Vector3D v = project(n);
			v.multiply(-2);
			add(v);
		}
	}
	
	//public void fromAngle(Vector3D v) {
		// FWD_VECTOR * RotateTrans(AXIS::X, v.pitch) * RotateTrans(AXIS::Y, v.yaw);
	//}

	//public Vector3D toAngle() {
		//Vector3D t = new Vector3D(x, 0, z);
		//Vector3D r(0, Math.acos(t.angle(this), Math.acos(t.angle(FWD_VECTOR))));
		//if (x < 0.0f) r.yaw *= -1;
		//if (y > 0.0f) r.pitch *= -1;
		//return r;
	//}
	
	public void makeAbsolute() {
		if (x < 0) x = -x;
		if (y < 0) y = -y;
		if (z < 0) z = -z;
	}
	
	public boolean nearlyEqual(Vector3D v, float e) {
		return	Math.abs(x - v.getX()) < e &&
				Math.abs(y - v.getY()) < e &&
				Math.abs(z - v.getZ()) < e;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Vector3D) {
			Vector3D v = (Vector3D)o;
			return x == v.getX() && y == v.getY() && z == v.getZ();
		}
		return false;
	}
	
	public String toString() {
		return "( " + x + ", " + y + ", " + z + " )";
	}
}