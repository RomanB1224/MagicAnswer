package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by rbarron11 on 10/27/2016.
 */

public class ShakeDetector implements SensorEventListener {

    //represent shake threshold
    private static final float SHAKE_THRESHOLD = 25f;
    // constant to represent how long between shakes (milliseconds)
    private static final int SHAKE_TIME_LAPS = 2000;
    //what was the last time the event occurred:
    private long timeOfLastShake;
    //define a listener to register onShake events
    private onShakeListener shakeListener;

    //Constructor to create new ShakeDetector passing in an onShakeListener as an argument
    public ShakeDetector (onShakeListener listener)
    {
        shakeListener = listener;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //determine first if the event is an accelerometer
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER )
        {
            //get the x, y , and z values when this event occurs
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            //use distance formula
            //square root of (x - gravity) mod 2
            //compare all 3 values against gravity
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            //Compute sum of squares
            double vector = Math.pow(gForceX, 2.0) + Math.pow(gForceY, 2.0) +Math.pow(gForceZ, 2.0);

            //Compute G-Force
            float gForce = (float) Math.sqrt(vector);

            //Compare gForce against the threshold
            if(gForce > SHAKE_THRESHOLD)
            {
                //get the current time
                long now = System.currentTimeMillis();
                //compare to see if the current time is atleast 2000 ms greater than the time of the
                //last shake
                if (now - timeOfLastShake >= SHAKE_TIME_LAPS)
                {
                    timeOfLastShake = now;
                    //register the shake event
                    shakeListener.onShake();
                }
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //define our own interface (method for other classes to implement)
    //called onShake()
    //responsibility of the MagicAnswerActivity to do this
    public interface onShakeListener {
        void onShake();
    }
}
