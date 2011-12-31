package math;

//represents final 3-dim Vector, with v.x, v.y, v.z 
//and vector operations

public class Vector {
 public final float x, y, z;

 public Vector( float x, float y, float z ) {
     this.x = x;
     this.y = y;
     this.z = z;
 }

 public float dot( Vector v ) {
     return x * v.x + y * v.y + z * v.z;
 }

 public float length( ) {
     return ( float )Math.sqrt( x * x + y * y + z * z );
 }

 public Vector add(Vector v) {
     return new Vector(x + v.x, y + v.y, z + v.z);
 }

 public Vector sub( Vector v ) {
     return new Vector( x - v.x, y - v.y, z - v.z );
 }

 public Vector mult(float s) {
		return new Vector(x * s, y * s, z * s);
	}

 public Vector normalize() {
		return mult(1.0f / length());
	}
 
}

