package com.example.tpichel.cs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by tpichel on 08/01/19.
 */

public class MyJobService extends JobService {
    //Méthode appelée quand la tâche est lancée

    private Random rn = new Random();
    WifiMeasureTask wifiMeasureTask = new WifiMeasureTask();

    @Override
    public boolean onStartJob(JobParameters params){
        Log.d("[JOB]", "onStartJob id = " + params.getJobId());
        //Mesure par AsyncTask
        wifiMeasureTask.execute();

        return true;
    }

    //Méthode appelée quand la tâche est arrêtée par le sheduler
    //Retourne vrai si le scheduler doit relancer la tâche
    @Override
    public boolean onStopJob(JobParameters params){
        Log.d("[JOB]", "onStopJob id = " + params.getJobId());
        //Arrêter l'AsyncTask
        wifiMeasureTask.cancel(true);

        return true;
    }

    private class WifiMeasureTask extends AsyncTask<Context, Void, List<ScanResult>> {

        private Context context;
        private AppDatabase db;

        @Override
        protected List<ScanResult> doInBackground(Context... contexts) {

            this.context = getApplicationContext();
            //Début
            WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            wifiManager.getScanResults();
            System.out.println("[SCAN] " + "[ASYNC]" + " AsyncTask Started");

            WifiRecordRepository recordRepository = new WifiRecordRepository(getApplicationContext());
            //recordRepository.dropTable();

            List<ScanResult> results = wifiManager.getScanResults();
            for (ScanResult r : results){
                System.out.println("[SCAN] " + "[RESULT]" + " " + r.SSID);

                Long tsLong = System.currentTimeMillis()/1000;
                String timestamp = tsLong.toString();

                WifiRecord record = new WifiRecord();
                record.bssid = r.SSID;
                record.timestamp = timestamp;
                record.uid = rn.nextInt();

                if(recordRepository.getBySSID(record.bssid).isEmpty()){
                    recordRepository.insertAll(record);
                }
                else{
                    recordRepository.update(record);
                }

            }

            return results;
        }

        protected void onPostExecute() {
            jobFinished(null, true);
        }

    }

    private class SensorMesureTask extends AsyncTask<Context, Void, List<ScanResult>> {

        private Context context;
        private AppDatabase db;

        private SensorManager mSensorManager = null;
        private Sensor mAccelerometer = null;

        final SensorEventListener mSensorEventListener = new SensorEventListener() {

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Que faire en cas de changement de précision ?

            }


            public void onSensorChanged(SensorEvent sensorEvent) {
                // Que faire en cas d'évènements sur le capteur ?

            }

        };

        @Override
        protected List<ScanResult> doInBackground(Context... contexts) {

            this.context = getApplicationContext();
            List<ScanResult> results = null;
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /*
            for (ScanResult r : results){
                System.out.println("[SCAN] " + "[RESULT]" + " " + r.SSID);

                Long tsLong = System.currentTimeMillis()/1000;
                String timestamp = tsLong.toString();

                SensorRecord record = new SensorRecord();

                if(recordRepository.getBySSID(record.bssid).isEmpty()){
                    recordRepository.insertAll(record);
                }
                else{
                    recordRepository.update(record);
                }

            }
        */
            return results;
        }

        protected void onPostExecute() {
            jobFinished(null, true);
        }

    }

}


