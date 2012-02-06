package palisade.particles;

import java.util.Vector;

public class Polygon
{
	public Vector<Vector3D> vertices = new Vector<Vector3D>();
	public Vector<TexCoord> texcoords = new Vector<TexCoord>();
	public Vector<Vector3D> normals = new Vector<Vector3D>();
	
	public Polygon() {
	}
	
	public Polygon(float[] verts) {
		for(int i=0; i < verts.length; i+=3) {
			add(verts[i], verts[i+1], verts[i+2]);
		}
	}

	public void add(float x, float y, float z) {
		vertices.add(new Vector3D(x, y, z));
	}
	
	public void setNormal(float x, float y, float z) {
		normals.add(new Vector3D(x, y, z));
	}
	
	public void setTexCoord(float u, float v) {
		texcoords.add(new TexCoord(u, v));
	}
	
	public Vector3D get(int i) {
		if (vertices != null && i < vertices.size())
			return vertices.get(i);
		return null;
	}
	
	public TexCoord getTexCoord(int i) {
		if (texcoords != null && i < texcoords.size())
			return texcoords.get(i);
		return null;
	}
	
	public Vector3D getNormal(int i) {
		if (normals != null && i < normals.size())
			return normals.get(i);
		return null;
	}
	
	public int size() {
		return vertices.size();
	}
}