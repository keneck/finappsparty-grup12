package com.example.ale.espaisparaviejos;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FallingUseCase extends Activity {

    private Timer myTimer;
    private boolean falling = false;
    private int currentTime = 10;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fallingusecase);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        /*Button button = (Button)findViewById(R.id.button);
        final TextView textField = (TextView)findViewById(R.id.text);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("rip");
                    }
                }
        );*/
        /*stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/

        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
                txtInfo.setText("Sacseja el dispositiu per simular una caiguda");
                btnCancel.setVisibility(View.GONE);
                myTimer.cancel();
                TextView txtTime = (TextView) findViewById(R.id.txtTime);
                txtTime.setVisibility(View.INVISIBLE);
                falling = false;
                currentTime = 10;
                vibrator.cancel();
            }
        });
        SensorManager sensorManager;
        Sensor accelerometerSensor = null;
        boolean accelerometerPresent;

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensorList.size() > 0){
            accelerometerPresent = true;
            accelerometerSensor = sensorList.get(0);
        }
        else{
            accelerometerPresent = false;
        }

        SensorEventListener accelerometerListener = new SensorEventListener(){

            float lastx, lasty, lastz;
            long lasttime;
            boolean firsttime = true;

            @Override
            public void onAccuracyChanged(Sensor arg0, int arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSensorChanged(SensorEvent arg0) {
                // TODO Auto-generated method stub
                float x_value = arg0.values[0];
                float y_value = arg0.values[1];
                float z_value = arg0.values[2];
                /*text_x.setText(String.valueOf(x_value));
                text_y.setText(String.valueOf(y_value));
                text_z.setText(String.valueOf(z_value));*/

                long currenttime = System.currentTimeMillis();
                float SensitiveX, SensitiveY, SensitiveZ;
                SensitiveX = 250.2f;
                SensitiveY = 250.2f;
                SensitiveZ = 250.2f;

                if(!firsttime){
                    long deltatime = currenttime - lasttime;
                    float xrate = Math.abs(x_value - lastx) * 10000/deltatime;
                    float yrate = Math.abs(y_value - lasty) * 10000/deltatime;
                    float zrate = Math.abs(z_value - lastz) * 10000/deltatime;
                    /*text_xrate.setText(String.valueOf(xrate));
                    text_yrate.setText(String.valueOf(yrate));
                    text_zrate.setText(String.valueOf(zrate));*/

                    if (xrate>SensitiveX){
                        //System.out.println("Shaking");
                        //textField.setText("SHAKING");
                        if (!falling) simulateFall();
                    }else{
                        //System.out.println("NOT Shaking");
                        //textField.setText("NOT SHAKING");
                    }

                    if (yrate>SensitiveY){
                        //System.out.println("Shaking");
                        if (!falling) simulateFall();
                    }else{
                        //System.out.println("NOT Shaking");
                        //textField.setText("NOT SHAKING");
                    }

                    if (zrate>SensitiveZ){
                        //System.out.println("Shaking");
                        if (!falling) simulateFall();
                    }else{
                        //System.out.println("NOT Shaking");
                        //textField.setText("NOT SHAKING");
                    }
                }
                lasttime = currenttime;
                lastx = x_value;
                lasty = y_value;
                lastz = z_value;
                firsttime = false;
            }
        };

        if(accelerometerPresent){
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    private void simulateFall() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 500, 500, 500};
        //-1 - don't repeat
        final int indexInPatternToRepeat = 1;
        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);

        falling = true;
        TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtInfo.setText("Caiguda detectada! Avisant al contacte en");
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        txtTime.setVisibility(View.VISIBLE);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setVisibility(View.VISIBLE);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {
                        TimerMethod();
                    }
                });
            }

            private void TimerMethod() {

                TextView txtTime = (TextView) findViewById(R.id.txtTime);
                if (currentTime != 1) txtTime.setText(currentTime + " segons");
                else txtTime.setText(currentTime + " segon");
                if (currentTime == 0) {
                    myTimer.cancel();
                    //falling = false;
                    currentTime = 10;
                    TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
                    txtInfo.setText("Trucant al Voluntari 114...");
                    txtTime.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.GONE);
                    ImageView imgCall = (ImageView) findViewById(R.id.imgCall);
                    imgCall.setVisibility(View.VISIBLE);
                    vibrator.cancel();
                }
                --currentTime;

            }

        }, 0, 1000);
    }

}

