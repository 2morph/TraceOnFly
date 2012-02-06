package view;

import java.io.IOException;

import utils.FileManager;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements Callback {
  private Camera camera;
  static final int FOTO_MODE = 0;
  boolean isPreviewOn = false;
  

public CameraPreview(Context context, AttributeSet attrs) {
      super( context, attrs );
      getHolder().addCallback( this );
      getHolder().setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
  }

  public void surfaceCreated( SurfaceHolder holder ) {
      camera = Camera.open();
  }

  public void surfaceChanged( SurfaceHolder holder, int format, int w, int h ) {
     if (isPreviewOn) camera.stopPreview();
     Camera.Parameters p = camera.getParameters();
     p.setPreviewSize(w, h);
     camera.setParameters(p);
     try {
        camera.setPreviewDisplay(holder);
     } catch (IOException e) {
        e.printStackTrace();
     }
     camera.startPreview();
     isPreviewOn = true;
  }

  public void surfaceDestroyed( SurfaceHolder holder ) {
     camera.stopPreview();
     isPreviewOn = false;
     camera.release();
  }
  
  public void getPicture() {
        camera.takePicture(null, mPictureCallback, mPictureCallback);
  }
  
  Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

     public void onPictureTaken(byte[] imageData, Camera c) {

        if (imageData != null) {
           System.out.println("__________________ length: " + imageData.length);
           FileManager.savePicture(imageData);
           camera.startPreview();
        }
     }

     };
  
}