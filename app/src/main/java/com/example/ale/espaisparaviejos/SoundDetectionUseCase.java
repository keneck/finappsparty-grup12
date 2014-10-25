package com.example.ale.espaisparaviejos;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class SoundDetectionUseCase extends Activity  {
    private Timer myTimer;
    private TextView mTextView;
    private SoundMeter sm;
    private TextView txtNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sounddetectionusecase);




        txtNotification = (TextView) findViewById(R.id.txtNotification);
        txtNotification.setX(0.0f);
        txtNotification.setY(320.0f);

/*
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.label_animation);

        animation.setAnimationListener(this);

        View animatedView = findViewById(R.id.txtNotification);

        animatedView.startAnimation(animation);*/

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
                //System.out.println(txtNotification.getY());
                txtNotification.setY(txtNotification.getY()-5);
                if (txtNotification.getY() <= 280-txtNotification.getHeight()) myTimer.cancel();


            }

        }, 1000, 25);
    }


}

