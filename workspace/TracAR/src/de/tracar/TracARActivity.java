package de.tracar;


import math.Vector;
import sensors.Orientation;
import utils.FileManager;
import utils.ServerConnection;
import view.CameraPreview;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TracARActivity extends Activity {
   public GLSurfaceView glView;  
   public CameraPreview cameraView; 
   public TextView textView;
   private Handler mHandler;
   private String message="no message";
   
   @Override
   public void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      FileManager myFileManager = new FileManager(this);
      setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
      requestWindowFeature(Window.FEATURE_NO_TITLE );
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
      
      setContentView( R.layout.main );
      textView = (TextView)findViewById(R.id.textOrientation);
      mHandler = new Handler();
      mHandler.post(mUpdate);
      Orientation orientation = Orientation.init(this);
      orientation.enable(Orientation.ORIENTATION);
      Button sendButton = (Button)this.findViewById(R.id.sendButton);
      sendButton.setOnClickListener(new OnClickListener() {
         public void onClick(View view) { onSendButtonClicked();}
      });
      Button shotButton = (Button)this.findViewById(R.id.shotPictureButton);
      shotButton.setOnClickListener(new OnClickListener() {
         public void onClick(View view) { onShotButtonClicked();}
      });
      Button exitButton = (Button)this.findViewById(R.id.exitButton);
      exitButton.setOnClickListener(new OnClickListener() {
         public void onClick(View view) { finish();}
      });
      
   }

   private void onSendButtonClicked() {
      ServerConnection connection = new ServerConnection();
      connection.sendRequest();
   }
   
   private void onShotButtonClicked() {
      cameraView = (CameraPreview)findViewById(R.id.camPreview);
      cameraView.getPicture();
   }
   
   private Runnable mUpdate = new Runnable() {
      public void run() {
         Vector erg = Orientation.get().getDiff();
         message = (int)erg.x +"  " + (int)erg.y + "   " + (int)erg.z;
         if (textView != null) textView.setText(message);
         mHandler.postDelayed(this, 100); // 5 seconds
      }
   };

}





//import math.Vector;
//import sensors.Orientation;
//import view.CameraPreview;
//import view.OpenGL10Renderer;
//import view.OpenGLOverlay;
//import view.OpenGLRenderer;
//import android.app.Activity;
//import android.content.pm.ActivityInfo;
//import android.graphics.PixelFormat;
//import android.opengl.GLSurfaceView;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//import android.view.ViewGroup.LayoutParams;
//
//
//public class TracARActivity extends Activity {
//  
//  private Handler mHandler;
//  public TextView textView;
//  private CameraPreview myPreview;
//  private OpenGLRenderer myGLView;
//  private String message="nix";
//
//  //-------------------------------------------------------------------------
//  /** Called when the activity is first created. */
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//    final Window window = getWindow(); 
//    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    requestWindowFeature(Window.FEATURE_NO_TITLE);
//    setContentView(R.layout.main);
//
//    textView = (TextView)findViewById(R.id.textOrientation);
//    mHandler = new Handler();
//    mHandler.post(mUpdate);
//    Orientation orientation = Orientation.init(this);
//    orientation.enable(Orientation.ORIENTATION);
//    //Vector diff = Orientation.get().getDiff();
//    
//   
//
//
//  }
//  //-------------------------------------------------------------------------
//  @Override
//  public void onResume() {
//    super.onResume();
//    //myGLView = new OpenGLRenderer(this);
//    //myPreview = new CameraPreview(this);
//    //setContentView(myGLView);
//    //addContentView(myPreview,  new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//    
//  }
//  //-------------------------------------------------------------------------
//  @Override
//  protected void onPause() {
//    super.onPause();
//  }
//  
//  private Runnable mUpdate = new Runnable() {
//    public void run() {
//        textView.setText(message);
//        mHandler.postDelayed(this, 100); // 5 seconds
//    }
//  };
//  
//  public void onFrame(int counter) {
//      Log.d("main", (String) textView.getText());
//      Vector erg = Orientation.get().getDiff();
//      message = (int)erg.x +"  " + (int)erg.y + "   " + (int)erg.z;
//  }
//}