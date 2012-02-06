package palisade.particles;

import java.nio.FloatBuffer;

public class Matrix {

	public float _11, _12, _13, _14,
				 _21, _22, _23, _24,
				 _31, _32, _33, _34,
				 _41, _42, _43, _44;
	
	public FloatBuffer toBuffer() {
		FloatBuffer b = BufferUtils.createFloatBuffer(16);
		b.put(toSingleArray());
		b.rewind();
		return b;
	}
	
	public float[] toSingleArray() {
		return new float[] {	_11, _12, _13, _14,
				  				_21, _22, _23, _24,
						  		_31, _32, _33, _34,
						  		_41, _42, _43, _44};
	}
	
	public float[][] toArray() {
		return new float[][] {	{_11, _12, _13, _14,},
				  				{_21, _22, _23, _24,},
						  		{_31, _32, _33, _34,},
						  		{_41, _42, _43, _44}};
	}

	public void setIdentity() {
		_11 = 1.0f; _12 = 0.0f; _13 = 0.0f; _14 = 0.0f;
		_21 = 0.0f; _22 = 1.0f; _23 = 0.0f; _24 = 0.0f;
		_31 = 0.0f; _32 = 0.0f; _33 = 1.0f; _34 = 0.0f;
		_41 = 0.0f; _42 = 0.0f; _43 = 0.0f; _44 = 1.0f;
	}
	
	public void set(float _11, float _12, float _13, float _14,
					float _21, float _22, float _23, float _24,
					float _31, float _32, float _33, float _34,
					float _41, float _42, float _43, float _44)
	{
		this._11 = _11; this._12 = _12; this._13 = _13; this._14 = _14;
		this._21 = _21; this._22 = _22; this._23 = _23; this._24 = _24;
		this._31 = _31; this._32 = _32; this._33 = _33; this._34 = _34;
		this._41 = _41; this._42 = _42; this._43 = _43; this._44 = _44;
	}
	
	public void set(float[][] arr) {
		set(arr[0][0], arr[0][1], arr[0][2], arr[0][3],
			arr[1][0], arr[1][1], arr[1][2], arr[1][3],
			arr[2][0], arr[2][1], arr[2][2], arr[2][3],
			arr[3][0], arr[3][1], arr[3][2], arr[3][3]);
	}
	
	public void set(float[] arr) {
		set(arr[0], arr[1], arr[2], arr[3],
			arr[4], arr[5], arr[6], arr[7],
			arr[8], arr[9], arr[10], arr[11],
			arr[12], arr[13], arr[14], arr[15]);
	}
	
	public float[] getRow(int row) {
		if (row == 0)
			return new float[]{_11, _12, _13, _14};
		else if (row == 1)
			return new float[]{_21, _22, _23, _24};
		else if (row == 2)
			return new float[]{_31, _32, _33, _34};
		return new float[]{_41, _42, _43, _44};
	}
	
	public void setRow(float[] v, int row) {
		if (row == 0) {
			_11 = v[0]; _12 = v[1]; _13 = v[2];
		} else if (row == 1) {
			_21 = v[0]; _22 = v[1]; _23 = v[2];
		} else if (row == 2) {
			_31 = v[0]; _32 = v[1]; _33 = v[2];
		}
		_41 = v[0]; _42 = v[1]; _43 = v[2];
	}
	
	public void setRow(Vector3D v, int i) {
		setRow(v.toArray(), i);
	}
	
	public Matrix() {
	}
	
	public Matrix(float[][] arr) {
		set(arr);
	}
	
	public Matrix(float[] arr) {
		set(arr);
	}
	
	public Matrix(Matrix m) {
		set(m._11, m._12, m._13, m._14,
			m._21, m._22, m._23, m._24,
			m._31, m._32, m._33, m._34,
			m._41, m._42, m._43, m._44);
	}
	
	public Matrix(float n) {
		set(n, n, n, n,
			n, n, n, n,
			n, n, n, n,
			n, n, n, n);
	}
	
	public Matrix(	float _11, float _12, float _13, float _14,
					float _21, float _22, float _23, float _24,
					float _31, float _32, float _33, float _34,
					float _41, float _42, float _43, float _44)
	{
		this._11 = _11; this._12 = _12; this._13 = _13; this._14 = _14;
		this._21 = _21; this._22 = _22; this._23 = _23; this._24 = _24;
		this._31 = _31; this._32 = _32; this._33 = _33; this._34 = _34;
		this._41 = _41; this._42 = _42; this._43 = _43; this._44 = _44;
	}
	
	public Matrix(Vector3D v0, Vector3D v1, Vector3D v2) {
		set(v0.x, v0.y, v0.z, 0.0f,
			v1.x, v1.y, v1.z, 0.0f,
			v2.x, v2.y, v2.z, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Matrix multiply(Matrix m) {
		Matrix t = new Matrix();
		t._11 = _11 * m._11 + _12 * m._21 + _13 * m._31 + _14 * m._41;
		t._21 = _21 * m._11 + _22 * m._21 + _23 * m._31 + _24 * m._41;
		t._31 = _31 * m._11 + _32 * m._21 + _33 * m._31 + _34 * m._41;
		t._41 = _41 * m._11 + _42 * m._21 + _43 * m._31 + _44 * m._41;
		t._12 = _11 * m._12 + _12 * m._22 + _13 * m._32 + _14 * m._42;
		t._22 = _21 * m._12 + _22 * m._22 + _23 * m._32 + _24 * m._42;
		t._32 = _31 * m._12 + _32 * m._22 + _33 * m._32 + _34 * m._42;
		t._42 = _41 * m._12 + _42 * m._22 + _43 * m._32 + _44 * m._42;
		t._13 = _11 * m._13 + _12 * m._23 + _13 * m._33 + _14 * m._43;
		t._23 = _21 * m._13 + _22 * m._23 + _23 * m._33 + _24 * m._43;
		t._33 = _31 * m._13 + _32 * m._23 + _33 * m._33 + _34 * m._43;
		t._43 = _41 * m._13 + _42 * m._23 + _43 * m._33 + _44 * m._43;
		t._14 = _11 * m._14 + _12 * m._24 + _13 * m._34 + _14 * m._44;
		t._24 = _21 * m._14 + _22 * m._24 + _23 * m._34 + _24 * m._44;
		t._34 = _31 * m._14 + _32 * m._24 + _33 * m._34 + _34 * m._44;
		t._44 = _41 * m._14 + _42 * m._24 + _43 * m._34 + _44 * m._44;

		return new Matrix(t);
	}
	
	public Matrix multiply(Matrix m0, Matrix m1) {
		return m0.multiply(m1);
	}
	
	public Matrix scale(float n) {
		return multiply(new Matrix(n));
	}
	
	public Matrix scale(Matrix m, float n) {
		return m.scale(n);
	}
	
	public Matrix divide(float n) {
		return scale(1 / n);
	}
	
	public void minor(float[][] r, float[][] m, int nrows, int ncols, int row, int col) {
		int row2 = 0;
		for(int i=0; i < nrows; i++) {
			if (i != row) {
				int col2 = 0;
				for(int j= 0; j < ncols; j++) {
					if (j != col) {
						r[row2][col2] = m[i][j];
						col2++;
					}
				}
				row2++;
			}
		}
	}
	
	public float cofact(float[][] t, float[][] m, int size, int row, int col) {
		minor(t, m, size, size, row, col);
		return ((float)Math.pow(-1.0, (double)(row + col))) * det(t, size - 1);
	}
	
	public float det(float[][] m, int size) {
		float result = 0;
		if (size == 1) {
			result = m[0][0];
		} else {
			float[][] t = new float[size - 1][size - 1];
			for(int i=0; i < size; i++) {
				result += m[0][i] * cofact(t, m, size, 0, i);
			}
		}
		return result;
	}
	
	public float[][] flip() {
		float[][] m = toArray();
		float[][] r = new float[4][4];
		for(int i=0; i < 4; i++ ) {
			for(int j=0; j < 4; j++) {
				r[i][j] = m[j][i];
			}
		}
		return r;
	}
	
	public Matrix invert() {
		float[][] t = new float[3][3];
		float[][] r = new float[4][4];
		float[][] m = toArray();
		for(int i=0; i < 4; i++) {
			for(int j=0; j < 4; j++) {
				r[i][j] = cofact(t, m, 4, i, j);
			}
		}
		float d = det(r, 4);
		set(r);
		
		if (d != 0.0f) {
			Matrix flipped = new Matrix();
			flipped.set(flip());
			flipped.divide(d);
			return flipped;
		}
		
		return new Matrix();
	}
	
	public Matrix inverse() {
		return new Matrix(this).invert();
	}

	public void rotateZ(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._11 = (float)Math.cos(angle);
		t_mat._12 = (float)(Math.sin(angle) * -1);
		t_mat._21 = (float)Math.sin(angle);
		t_mat._22 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		this.set(t_mat.toArray());
	}
	
	public Matrix getRotateZ(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._11 = (float)Math.cos(angle);
		t_mat._12 = (float)(Math.sin(angle) * -1);
		t_mat._21 = (float)Math.sin(angle);
		t_mat._22 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		return t_mat;
	}
	
	public void rotateY(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._11 = (float)Math.cos(angle);
		t_mat._13 = (float)Math.sin(angle);
		t_mat._31 = (float)(Math.sin(angle) * -1);
		t_mat._33 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		this.set(t_mat.toArray());
	}
	
	public Matrix getRotateY(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._11 = (float)Math.cos(angle);
		t_mat._13 = (float)Math.sin(angle);
		t_mat._31 = (float)(Math.sin(angle) * -1);
		t_mat._33 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		return t_mat;
	}
	
	public void rotateX(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._22 = (float)Math.cos(angle);
		t_mat._23 = (float)(Math.sin(angle) * -1);
		t_mat._32 = (float)Math.sin(angle);
		t_mat._33 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		this.set(t_mat.toArray());
	}
	
	public Matrix getRotateX(float angle){
		Matrix t_mat = new Matrix();
		t_mat.setIdentity();
		t_mat._22 = (float)Math.cos(angle);
		t_mat._23 = (float)(Math.sin(angle) * -1);
		t_mat._32 = (float)Math.sin(angle);
		t_mat._33 = (float)Math.cos(angle);
		t_mat = this.multiply(t_mat);
		return t_mat;
	}
	
	public String toString() {
		return "| " + _11 + ", " + _12 + ", " + _13 + ", " + _14 + ", |\n| " +
		 			_21 + ", " + _22 + ", " + _23 + ", " + _24 + ", |\n| " +
		 			_31 + ", " + _32 + ", " + _33 + ", " + _34 + ", |\n| " +
		 			_41 + ", " + _42 + ", " + _43 + ", " + _44 + "  |\n| ";
	}
}