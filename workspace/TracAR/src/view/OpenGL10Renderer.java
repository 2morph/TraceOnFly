package view;


//  import java.nio.ByteBuffer;
//  import java.nio.ByteOrder;
//  import java.nio.FloatBuffer;

 
  import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
  import javax.microedition.khronos.opengles.GL10;

import math.Vector;

import sensors.Orientation;
import utils.FileManager;
import utils.ParseObj;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

  public class OpenGL10Renderer implements Renderer {
        
//    public void onDrawFrame( GL10 gl ) {
//        float c = 1.0f / 256 * ( System.currentTimeMillis() % 256 );
//        gl.glClearColor( c, c, c, 0.5f );
//        gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
//    }
//
//    public void onSurfaceChanged( GL10 gl, int width, int height ) {
//        gl.glViewport( 0, 0, width, height );
//    }
//
//    public void onSurfaceCreated( GL10 gl, EGLConfig config ) {
//        // No need to do anything here.
//    }

  

    int onDrawFrameCounter=1;
    int[] cameraTexture;
    byte[] glCameraFrame=new byte[256*256]; //size of a texture must be a power of 2
    FloatBuffer cubeBuff;


    public void onDrawFrame(GL10 gl) {
      onDrawFrameCounter++;
      
      gl.glEnable(GL10.GL_TEXTURE_2D);
      gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
      gl.glColor4f(0, 1, 0, 1);
      
      gl.glLoadIdentity();
      Vector erg = Orientation.get().getDiff();
      
      
      GLU.gluLookAt(gl, 0, 0, 0f, 0, 0, -5, 0, 1, 0);
      gl.glRotatef(erg.x,0,1,0); //Rotate the camera image
      gl.glTranslatef(0, 0, -20);

      gl.glRotatef(onDrawFrameCounter,1,0,0); //Rotate the camera image
      gl.glRotatef((float)Math.sin(onDrawFrameCounter/20.0f)*40,0,1,0); //Rotate the camera image
      gl.glRotatef((float)Math.cos(onDrawFrameCounter/40.0f)*40,0,0,1); //Rotate the camera image
//     
//      
//      float rotx = (erg.z -90);
//      gl.glRotatef(rotx,1,0,0); //Rotate the camera image
//      //gl.glRotatef((float)Math.cos(onDrawFrameCounter/40.0f)*40,0,0,1); //Rotate the camera image

      gl.glNormal3f(0,0,1);
      gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, camObjCoord.length/3);      
//      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,20, 4);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
      gl.glViewport(0, 0, width, height);

      float ratio = (float) width / height;
      gl.glMatrixMode(GL10.GL_PROJECTION);
      gl.glLoadIdentity();
//      gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
      gl.glFrustumf(-0.5f, 0.5f, -0.5f, 0.5f, 1, 100);
      
      gl.glMatrixMode(GL10.GL_MODELVIEW);
      gl.glLoadIdentity();
      GLU.gluLookAt(gl, 0, 0, 4.2f, 0, 0, 0, 0, 1, 0);    
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
       
       String obj = FileManager.loadObjFromFile("teapot.obj");
       camObjCoord = ParseObj.getLineLoopArray(ParseObj.getVertizesFromSting(obj), 
                                              ParseObj.getTrianglesFromSting(obj));
       
       
      gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

      gl.glClearColor(0, 0, 0, 0);
      gl.glLineWidth(2);
      gl.glEnable(GL10.GL_CULL_FACE);
      gl.glShadeModel(GL10.GL_SMOOTH);
      gl.glEnable(GL10.GL_DEPTH_TEST);
      
      cubeBuff = makeFloatBuffer(camObjCoord);
      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeBuff);
      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    } 
      
    FloatBuffer makeFloatBuffer(float[] arr) {
      ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
      bb.order(ByteOrder.nativeOrder());
      FloatBuffer fb = bb.asFloatBuffer();
      fb.put(arr);
      fb.position(0);
      return fb;
    }
    
//    final static float camObjCoord[] = new float[] {
//       -2.0f, -1.5f,  2.0f,
//        2.0f, -1.5f,  2.0f,
//       -1.0f,  1.5f,  2.0f,
//        1.0f,  1.5f,  2.0f
//    };
//    
      static float camObjCoord[] = ParseObj.getLinesArray(ParseObj.getVertizesFromSting(TestModel.getDiamond()), 
                                                              ParseObj.getTrianglesFromSting(TestModel.getDiamond()));
//    final static float camObjCoord[] = new float[] {
//          // FRONT
//           -2.0f, -1.5f,  2.0f,
//            2.0f, -1.5f,  2.0f,
//           -2.0f,  1.5f,  2.0f,
//            2.0f,  1.5f,  2.0f,
//           // BACK
//           -2.0f, -1.5f, -2.0f,
//           -2.0f,  1.5f, -2.0f,
//            2.0f, -1.5f, -2.0f,
//            2.0f,  1.5f, -2.0f,
//           // LEFT
//           -2.0f, -1.5f,  2.0f,
//           -2.0f,  1.5f,  2.0f,
//           -2.0f, -1.5f, -2.0f,
//           -2.0f,  1.5f, -2.0f,
//           // RIGHT
//            2.0f, -1.5f, -2.0f,
//            2.0f,  1.5f, -2.0f,
//            2.0f, -1.5f,  2.0f,
//            2.0f,  1.5f,  2.0f,
//           // TOP
//           -2.0f,  1.5f,  2.0f,
//            2.0f,  1.5f,  2.0f,
//           -2.0f,  1.5f, -2.0f,
//            2.0f,  1.5f, -2.0f,
//           // BOTTOM
//           -2.0f, -1.5f,  2.0f,
//           -2.0f, -1.5f, -2.0f,
//            2.0f, -1.5f,  2.0f,
//            2.0f, -1.5f, -2.0f,
//        };
//    
 
  }