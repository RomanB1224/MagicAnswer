package edu.orangecoastcollege.cs273.magicanswer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MagicAnswerActivity extends AppCompatActivity {

    MagicAnswer magicAnswer;
    private TextView answerTextView;
    private EditText questionEditText;

    //references to the SensorManager (manages all sensors on device)
    private SensorManager sensorManager;

    // reference to accelerometer
    private Sensor acceleromter;

    // reference to shake detector
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_answer);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        questionEditText = (EditText) findViewById(R.id.questionEditText);
        // TASK 2: CREATE A NEW MAGIC ANSWER OBJECT
        magicAnswer = new MagicAnswer(this);

        // TASK 3: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleromter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeDetector = new ShakeDetector(new ShakeDetector.onShakeListener() {
            @Override
            public void onShake() {
                //get a random answer put it in the text view;
                String answer = magicAnswer.getRandomAnswer();
                answerTextView.setText(answer);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //start accelerometer
        sensorManager.registerListener(shakeDetector, acceleromter, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }
}
