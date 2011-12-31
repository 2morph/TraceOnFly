package view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLOverlay extends GLSurfaceView {

  public OpenGLOverlay(Context context, AttributeSet attrs){
      super(context);

    this.setEGLConfigChooser( 8, 8, 8, 8, 16, 0 );
    this.getHolder().setFormat( PixelFormat.TRANSLUCENT );
    // The renderer will be implemented in a separate class, GLView, which I'll show next.
    this.setRenderer( new OpenGL10Renderer() );

  }
}