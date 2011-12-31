package view;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements Callback {
  private Camera camera;

public CameraPreview(Context context, AttributeSet attrs) {
      super( context );
      // We're implementing the Callback interface and want to get notified
      // about certain surface events.
      getHolder().addCallback( this );
      // We're changing the surface to a PUSH surface, meaning we're receiving
      // all buffer data from another component - the camera, in this case.
      getHolder().setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
  }

  public void surfaceCreated( SurfaceHolder holder ) {
      // Once the surface is created, simply open a handle to the camera hardware.
      camera = Camera.open();
  }

  public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {
      // This method is called when the surface changes, e.g. when it's size is set.
      // We use the opportunity to initialize the camera preview display dimensions.
      Camera.Parameters p = camera.getParameters();
      p.setPreviewSize( width, height );
      camera.setParameters( p );

      // We also assign the preview display to this surface...
      try {
          camera.setPreviewDisplay( holder );
      } catch( IOException e ) {
          e.printStackTrace();
      }
      // ...and start previewing. From now on, the camera keeps pushing preview
      // images to the surface.
      camera.startPreview();
  }

  public void surfaceDestroyed( SurfaceHolder holder ) {
      // Once the surface gets destroyed, we stop the preview mode and release
      // the whole camera since we no longer need it.
      camera.stopPreview();
      camera.release();
      camera = null;
  }
}


//
//import java.io.IOException;
//
//import android.content.Context;
//import android.hardware.Camera;
//import android.hardware.Camera.PreviewCallback;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {
//  
//  Camera myCamera;
//  boolean isPreviewRunning = false;
//  Camera.PreviewCallback callback;
//
//  public CameraPreview(Context context, AttributeSet attrs) {
//    super(context);
//    //this.callback=callback;
//    SurfaceHolder holder = getHolder();
//    holder.addCallback(this);
//    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//  }
//  
//  public void surfaceCreated(SurfaceHolder holder) {
//    synchronized(this) {
//      myCamera = Camera.open();
//      //Camera.Parameters camParas = myCamera.getParameters();  
//      //camParas.setPreviewSize(240, 160);
//      //myCamera.setParameters(camParas);
//      try {
//          myCamera.setPreviewDisplay(holder);
//      } catch (IOException e) {
//          Log.e("CameraPreview", "myCamera.setPreviewDisplay(holder); failed");
//      }
//      myCamera.startPreview();
//      myCamera.setPreviewCallback(this);
//    }
//  }
//
//  public void surfaceDestroyed(SurfaceHolder holder) {
//    synchronized(this) {
//      try {
//        if(myCamera!=null) {
//          myCamera.stopPreview();  
//          isPreviewRunning=false;
//          myCamera.release();
//        }
//      } catch (Exception e) {
//        Log.e("Camera", e.getMessage());
//      }
//    }
//  }
//
//  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//  }
//
//  public void onPreviewFrame(byte[] arg0, Camera arg1) {
//    if (callback!=null) callback.onPreviewFrame(arg0, arg1);        
//  }
//}
//
