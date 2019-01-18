package com.example.tpichel.cs;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ForegroundService extends Service {

    //private Random rn = new Random();
    //private int JOB_ID = rn.nextInt();
    private int JOB_ID = 1337;

    public ForegroundService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        /* Put this Service in foreground
           -> Will not be killed by the system if low on memory
         */
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //On créé la BDD
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "db").build();

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("NetMonitor is Running")
                .setContentText("Click to open app")
                .setUsesChronometer(true)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .build();
        //On place le service en premier plan
        startForeground(1337, notif);

        //Lancer ici le job de monitoring
        this.scheduleJobWifi();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void scheduleJobWifi(){
        ComponentName serviceName = new ComponentName(this, MyJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName).setPeriodic(1000).build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            System.out.println("[APP][JOB] Job Lancé !");
            Toast.makeText(this, "scheduled job successfully", Toast.LENGTH_LONG).show();
        }
    }
}
