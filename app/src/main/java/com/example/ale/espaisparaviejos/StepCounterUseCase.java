package com.example.ale.espaisparaviejos;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;

public class StepCounterUseCase extends Activity implements SensorEventListener {

    private TextView textView;

    private SensorManager mSensorManager;

    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;

    private float initialSteps = -1;

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepcountercase);

        //textView = (TextView) findViewById(R.id.textview);

        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        final Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = true;
                TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
                txtInfo.setText("En marxa!");
                btnStart.setEnabled(false);
                btnStart.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //System.out.println("SENSOR CHANGED");
        //System.out.println(event.values[0]);
        if (started) {
            if (initialSteps == -1) initialSteps = event.values[0];
            float currentSteps = event.values[0];
            if (currentSteps - initialSteps >= 10) {
                TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
                txtInfo.setText("Enhorabona, has arribat als 10 passos!");
            }

            Sensor sensor = event.sensor;
            float[] values = event.values;
            int value = -1;

            if (values.length > 0) {
                value = (int) values[0];
            }
            //textView.setText("Step Counter Detected : " + value);
            //System.out.println(currentSteps - initialSteps);

            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                //textView.setText("Step Counter Detected : " + value);
                //System.out.println(currentSteps - initialSteps);
            } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                // For test only. Only allowed value is 1.0 i.e. for step taken
                //textView.setText("Step Detector Detected : " + value);
                //System.out.println(currentSteps - initialSteps);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,

                SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }
}

