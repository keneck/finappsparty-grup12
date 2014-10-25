package com.example.ale.espaisparaviejos;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rect);
/*
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/

        ListView myListView;
        myListView = (ListView) findViewById(R.id.useCasesList);
        ArrayList<String> useCasesList = new ArrayList<String>();
        String[] items = new String[] { "Detecció de caiguda", "Notificació", "Podòmetre"};
        useCasesList.addAll(Arrays.asList(items));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.usecase_item, useCasesList);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        Intent i = new Intent(getBaseContext(), FallingUseCase.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent ii = new Intent(getBaseContext(), SoundDetectionUseCase.class);
                        startActivity(ii);
                        break;
                    case 2:
                        Intent iii = new Intent(getBaseContext(), StepCounterUseCase.class);
                        startActivity(iii);
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    default:
                        break;
                }

            }

        });


    }
}


