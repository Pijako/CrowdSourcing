package com.example.tpichel.cs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //private Random rn = new Random();
    //private int JOB_ID = rn.nextInt();
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        startService(new Intent(context, ForegroundService.class));
    }

    /*
    public void scheduleJobWifi(){
        ComponentName serviceName = new ComponentName(this, WifiJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName).setPeriodic(1000).build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            System.out.println("[APP][JOB] Job Lancé !");
            Toast.makeText(this, "scheduled job successfully", Toast.LENGTH_LONG).show();
        }
    }
    */

    //Listener qui récupère le bouton cliqué
    public void onClick(View v) throws IOException {
        Button b_clique = findViewById(v.getId());

        //Si le bouton SCAN est cliqué
        if(v.getId()== R.id.button) {
            System.out.println("[APP][VIEW] Bouton cliqué !");
            /*
            try {
                persistence_init();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            setContentView(R.layout.activity_scan);
            Intent scanActivity = new Intent(MainActivity.this, ScanActivity.class);

            startActivity(scanActivity);
        }
    }

    public void persistence_init() throws IOException {

        Log.i("[DATA]", "[INIT]" + "Persistance");
    }

    public Context getAppContext() {
        return this.context;
    }
}
