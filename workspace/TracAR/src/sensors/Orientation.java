package sensors;


import math.Vector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Orientation implements SensorEventListener{

  private static Orientation INSTANCE;
  private SensorManager _mySensorManager = null;
  private Context _myContext;
  private Vector diff;
  
  public final static int ACCELEROMETER = Sensor.TYPE_ACCELEROMETER;
  public final static int GRAVITY = Sensor.TYPE_GRAVITY;
  public final static int GYROSCOPE = Sensor.TYPE_GYROSCOPE;
  public final static int LIGHT = Sensor.TYPE_LIGHT;
  public final static int LINEAR_ACCELERATION = Sensor.TYPE_LINEAR_ACCELERATION;
  public final static int MAGNETIC_FIELD = Sensor.TYPE_MAGNETIC_FIELD;
  public final static int ORIENTATION = Sensor.TYPE_ORIENTATION;
  public final static int PRESSURE = Sensor.TYPE_PRESSURE;
  public final static int PROXIMITY = Sensor.TYPE_PROXIMITY;
  public final static int ROTATION_VECTOR = Sensor.TYPE_ROTATION_VECTOR;
  public final static int TEMPERATURE = Sensor.TYPE_TEMPERATURE;
  
  public static Orientation init(Context context) {
    if(INSTANCE!=null) return INSTANCE;
    return new Orientation(context);
  }
  //-------------------------------------------------------------------------
  public static Orientation get() {
    return INSTANCE;
  }
  //------CONSTRUCTOR--------------------------------------------------------
  private Orientation(Context context) {
    if(INSTANCE!=null)INSTANCE.disableAll();
    _myContext = context;
    _mySensorManager = (SensorManager) _myContext.getSystemService(Context.SENSOR_SERVICE);
    INSTANCE = this;
  }
  //-------MEMBER------------------------------------------------------------
  public void enable(int sensorType) {
    _mySensorManager.registerListener(this,_mySensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_UI);
  }
  //-------------------------------------------------------------------------
  public void disable(int sensorType) {
    _mySensorManager.unregisterListener(this, _mySensorManager.getDefaultSensor(sensorType));
  }
  //-------------------------------------------------------------------------
  public void disableAll() {
    _mySensorManager.unregisterListener(this);
  }
  //-------------------------------------------------------------------------
  public void onAccuracyChanged(Sensor sensor, int accuracy) { }
  //-------------------------------------------------------------------------
  public void onSensorChanged(SensorEvent event) {
    diff = new Vector(event.values[0], event.values[1], event.values[2]); 
  }
  //-------------------------------------------------------------------------
  public Vector getDiff() {
   if (diff!=null) return diff;
   return new Vector(0,0,0);
  }
}
