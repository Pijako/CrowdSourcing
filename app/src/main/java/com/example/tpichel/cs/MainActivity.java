package com.example.tpichel.cs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        startService(new Intent(context, ForegroundService.class));

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

    }

    public void onClick1(View v) {

        System.out.println("[APP][VIEW] Bouton WIFI cliqué !");

        setContentView(R.layout.activity_scan);
        Intent scanActivity = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(scanActivity);

    }

    public void onClick2(View v) {

        System.out.println("[APP][VIEW] Bouton ACC cliqué !");

        setContentView(R.layout.activity_sensor);
        Intent sensorActivity = new Intent(MainActivity.this, SensorActivity.class);
        startActivity(sensorActivity);

    }

    public Context getAppContext() {
        return this.context;
    }
}
