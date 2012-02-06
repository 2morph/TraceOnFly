package palisade.particles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Object3D extends GLShape
{
	public boolean loaded = false;
	private GL10 gl;

	//private ArrayList<float[]> vertexsets = new ArrayList<float[]>();		// Vertex Coordinates
	//private ArrayList<float[]> vertexsetsnorms = new ArrayList<float[]>();	// Vertex Coordinates Normals
	//private ArrayList<float[]> vertexsetstexs = new ArrayList<float[]>();	// Vertex Coordinates Textures
	//private ArrayList<int[]> faces = new ArrayList<int[]>();				// Array of Faces (vertex sets)
	//private ArrayList<int[]> facestexs = new ArrayList<int[]>();			// Array of of Faces textures
	//private ArrayList<int[]> facesnorms = new ArrayList<int[]>();			// Array of Faces normals

	private FloatBuffer vertexlist;
	private ShortBuffer indexlist;
	private int numpolys = 0;
	private int numIndices = 0;
	public float toppoint = 0;      // y+
	public float bottompoint = 0;   // y-
	public float leftpoint = 0;     // x-
	public float rightpoint = 0;    // x+
	public float farpoint = 0;      // z-
	public float nearpoint = 0;     // z+
	
	private int numVert = 0;
	private int numNorm = 0;
	private int numTex = 0;
	private int numFace = 0;
	private int numVertsPerFace = 0;
	//private int numFaceTex = 0;
	//private int numFaceNorm = 0;
	
    public Object3D(GLWorld world, String filepath, boolean centerit) throws Exception {
		super(world);
		LoadFile(filepath, centerit);
	}

	public void LoadFile(String filepath, boolean centerit) throws Exception {
		Log.d("OBJ", "loading mesh");
		countobject(filepath);
		allocbuffers();
		loadobject(filepath);
		Log.d("OBJ", "mesh load done");
		//if (centerit) {
		//	Log.d("OBJ", "centering");
		//	centerit();
		//	Log.d("OBJ", "centering done");
		//}
		//Log.d("OBJ", "creating draw list");
		//opengldrawtolist();
		//Log.d("OBJ", "draw list complete");
		//Log.d("OBJ", "cleaning up data");
		//cleanup();
		//Log.d("OBJ", "done cleaning up");
		
		loaded = true;
    }
	
	//private void cleanup() {
		//vertexsets = null;
		//vertexsetsnorms = null;
		//vertexsetstexs = null;
		//faces = null;
		//facestexs = null;
		//facesnorms = null;
	//}

	private void countobject(String filepath) throws NumberFormatException, IOException {
		int linecounter = 0;
		String newline;
		BufferedReader br;

		FileInputStream stream = null;
		File file = new File(filepath);
		stream = new FileInputStream(file);
		br = new BufferedReader(new InputStreamReader(stream), 8192);
	
		String[] coordstext;
		while (((newline = br.readLine()) != null)) {
			linecounter++;
			newline = newline.trim();
			if (newline.length() > 0) {
				if (newline.charAt(0) == 'v' && newline.charAt(1) == ' ')
					this.numVert++;
				
				if (newline.charAt(0) == 'v' && newline.charAt(1) == 't')
					this.numTex++;
				
				if (newline.charAt(0) == 'v' && newline.charAt(1) == 'n')
					this.numNorm++;
				
				if (newline.charAt(0) == 'f' && newline.charAt(1) == ' ') {
					coordstext = newline.split("\\s+");
					if (numVertsPerFace == 0) numVertsPerFace = coordstext.length - 1;
					this.numFace++;
				}
			}
		}
		if (stream != null) stream.close();
		Log.d("OBJ", "Count complete, " + numVert + " total vertices.");
	}
	
	private void allocbuffers() {
		numIndices = numFace * numVertsPerFace;
		//vertexlist = BufferUtils.createFloatBuffer(numIndices * 3);
		//indexlist = BufferUtils.createShortBuffer(numIndices);
	}
	
	private void loadobject(String filepath) throws NumberFormatException, IOException {
		int linecounter = 0;
		String newline;
		BufferedReader br;
		boolean firstpass = true;

		float[] vcoords = new float[3];
		float[] tcoords = new float[2];
		float[] ncoords = new float[3];
		String[] coordstext;
		
		FileInputStream stream = null;
		File file = new File(filepath);
		stream = new FileInputStream(file);
		br = new BufferedReader(new InputStreamReader(stream), 8192);
	
		//float[] vertex;
		//short index = 0;
		//int w;
		int[] v;
		while (((newline = br.readLine()) != null)) {
			linecounter++;
			newline = newline.trim();
			if (newline.length() > 0) {
				if (newline.charAt(0) == 'v' && newline.charAt(1) == ' ') {
					coordstext = newline.split("\\s+");
					
					for (int i = 1; i < coordstext.length; i++)
						vcoords[i-1] = Float.valueOf(coordstext[i]).floatValue();
					
					if (firstpass) {
						rightpoint = vcoords[0];
						leftpoint = vcoords[0];
						toppoint = vcoords[1];
						bottompoint = vcoords[1];
						nearpoint = vcoords[2];
						farpoint = vcoords[2];
						firstpass = false;
					}
					
					if (vcoords[0] > rightpoint) rightpoint = vcoords[0];
					if (vcoords[0] < leftpoint) leftpoint = vcoords[0];
					if (vcoords[1] > toppoint) toppoint = vcoords[1];
					if (vcoords[1] < bottompoint) bottompoint = vcoords[1];
					if (vcoords[2] > nearpoint) nearpoint = vcoords[2];
					if (vcoords[2] < farpoint) farpoint = vcoords[2];
					
					//vertexsets.add(vcoords);
					addVertex(vcoords[0], vcoords[1], vcoords[2]);
				}
				if (newline.charAt(0) == 'v' && newline.charAt(1) == 't') {
					coordstext = newline.split("\\s+");
					tcoords[0] = Float.valueOf(coordstext[1]).floatValue();
					tcoords[1] = Float.valueOf(coordstext[2]).floatValue();
					//vertexsetstexs.add(tcoords);
				}
				if (newline.charAt(0) == 'v' && newline.charAt(1) == 'n') {
					coordstext = newline.split("\\s+");
					ncoords[0] = Float.valueOf(coordstext[1]).floatValue();
					ncoords[1] = Float.valueOf(coordstext[2]).floatValue();
					//vertexsetsnorms.add(ncoords);
				}
				if (newline.charAt(0) == 'f' && newline.charAt(1) == ' ') {
					coordstext = newline.split("\\s+");
					v = new int[coordstext.length - 1];
					//int[] vt = new int[coordstext.length - 1];
					//int[] vn = new int[coordstext.length - 1];
					
					for (int i=1; i < coordstext.length; i++) {
						String fixstring = coordstext[i].replaceAll("//","/0/");
						String[] tempstring = fixstring.split("/");
						v[i-1] = Integer.valueOf(tempstring[0]).intValue();
						//if (tempstring.length > 1) {
						//	vt[i-1] = Integer.valueOf(tempstring[1]).intValue();
						//} else {
						//	vt[i-1] = 0;
						//}
						//if (tempstring.length > 2) {
						//	vn[i-1] = Integer.valueOf(tempstring[2]).intValue();
						//} else {
						//	vn[i-1] = 0;
						//}
					}
					//faces.add(v);
					//facestexs.add(vt);
					//facesnorms.add(vn);
					
					try {
						if (v.length == 3)
							addFace(new GLFace( mVertexList.get(v[0]-1), mVertexList.get(v[1]-1), mVertexList.get(v[2]-1) ));
						else if (v.length == 4)
							addFace(new GLFace( mVertexList.get(v[0]-1), mVertexList.get(v[1]-1), mVertexList.get(v[2]-1), mVertexList.get(v[3]-1) ));
						
						//for (w=0; w < v.length; w++) {
							//vertex = vertexsets.get(v[w] - 1);
							//vertexlist.put(vertex);
							//indexlist.put(index++);
						//}
					} catch (Exception e) {
						Log.d("OBJ", e.toString());
					}
				}
			}
		}
		
		if (stream != null) {
			stream.close();
		}
		Log.d("OBJ", "File read complete, " + mVertexList.size() + " vertices read.");
		
		//vertexlist.rewind();
		//indexlist.rewind();
	}

	private void centerit() {
		/*float xshift = (rightpoint-leftpoint) /2f;
		float yshift = (toppoint - bottompoint) /2f;
		float zshift = (nearpoint - farpoint) /2f;

		for (int i=0; i < vertexsets.size(); i++) {
			float[] coords = new float[4];

			coords[0] = (vertexsets.get(i))[0] - leftpoint - xshift;
			coords[1] = (vertexsets.get(i))[1] - bottompoint - yshift;
			coords[2] = (vertexsets.get(i))[2] - farpoint - zshift;

			vertexsets.set(i, coords);
		}*/
	}

	public float getXWidth() {
		float returnval = 0;
		returnval = rightpoint - leftpoint;
		return returnval;
	}

	public float getYHeight() {
		float returnval = 0;
		returnval = toppoint - bottompoint;
		return returnval;
	}

	public float getZDepth() {
		float returnval = 0;
		returnval = nearpoint - farpoint;
		return returnval;
	}

	public int numpolygons() {
		return numpolys;
	}

	public void opengldraw() {
		//long sTime = Cyborg.getTimeMs();
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexlist);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, numIndices , GL10.GL_UNSIGNED_SHORT, indexlist);
		//gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, numverts);
		//long eTime = Cyborg.elapsedMs(sTime);
		//Log.d("OBJ", "drawTime: " + eTime + " ms");
	}
	
	public void opengldrawtolist() throws Exception {
		/*int nFace = faces.get(0).length;
		int nVerts = vertexsets.size();
		objectlist = BufferUtils.createFloatBuffer(nVerts * 3);
		indexlist = BufferUtils.createShortBuffer(nVerts);
		numverts = faces.size() * nFace;
		
		int i, w;
		short index = 0;
		float[] face;
		int[] tempfaces;
		for (i=0; i < faces.size(); i++) {
			tempfaces = faces.get(i);
			//int[] tempfacesnorms = (int[])(facesnorms.get(i));
			//int[] tempfacestexs = (int[])(facestexs.get(i));

			for (w=0; w < tempfaces.length; w++) {
				//if (tempfacesnorms[w] != 0) {
				//   float normtempx = ((float[])vertexsetsnorms.get(tempfacesnorms[w] - 1))[0];
				//   float normtempy = ((float[])vertexsetsnorms.get(tempfacesnorms[w] - 1))[1];
				//   float normtempz = ((float[])vertexsetsnorms.get(tempfacesnorms[w] - 1))[2];
				//   GL10.glNormal3f(normtempx, normtempy, normtempz);
				//}

				//if (tempfacestexs[w] != 0) {
				//   float textempx = ((float[])vertexsetstexs.get(tempfacestexs[w] - 1))[0];
				//   float textempy = ((float[])vertexsetstexs.get(tempfacestexs[w] - 1))[1];
				//   float textempz = ((float[])vertexsetstexs.get(tempfacestexs[w] - 1))[2];
				//   GL10.glTexCoord3f(textempx,1f-textempy,textempz);
				//}

				face = vertexsets.get(tempfaces[w] - 1);
				objectlist.put(face);
				indexlist.put(index++);
			}
		}*/
		
		//gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexlist);
	}
	
	/*public ArrayList<Polygon> getPolygons() {
		ArrayList<Polygon> scene = new ArrayList<Polygon>();
		for (int i = 0; i < faces.size(); i++) {
			int[] tempfaces = (faces.get(i));

			Polygon poly = new Polygon();
			for (int w = 0; w < tempfaces.length; w++) {
				float tempx = vertexsets.get(tempfaces[w] - 1)[0];
				float tempy = vertexsets.get(tempfaces[w] - 1)[1];
				float tempz = vertexsets.get(tempfaces[w] - 1)[2];

				poly.add(tempx, tempy, tempz);
			}
			scene.add(poly);
		}
		return scene;
	}*/
}