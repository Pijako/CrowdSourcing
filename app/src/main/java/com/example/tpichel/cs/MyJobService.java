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
    SensorMeasureTask sensorMeasureTask = new SensorMeasureTask();

    @Override
    public boolean onStartJob(JobParameters params){
        Log.d("[JOB]", "onStartJob id = " + params.getJobId());
        //Mesure par AsyncTask
        wifiMeasureTask.execute();
        sensorMeasureTask.execute();

        return true;
    }

    //Méthode appelée quand la tâche est arrêtée par le sheduler
    //Retourne vrai si le scheduler doit relancer la tâche
    @Override
    public boolean onStopJob(JobParameters params){
        Log.d("[JOB]", "onStopJob id = " + params.getJobId());
        //Arrêter l'AsyncTask
        wifiMeasureTask.cancel(true);
        //sensorMeasureTask.cancel(true);

        return true;
    }

    private class WifiMeasureTask extends AsyncTask<Context, Void, List<ScanResult>> {

        private Context context;

        @Override
        protected List<ScanResult> doInBackground(Context... contexts) {

            this.context = getApplicationContext();
            //Début
            WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            wifiManager.getScanResults();
            System.out.println("[SCAN] " + "[ASYNC]" + " AsyncTask Started");

            WifiRecordRepository recordRepository = new WifiRecordRepository(getApplicationContext());

            Long tsLong = System.currentTimeMillis()/1000;
            String timestamp = tsLong.toString();

            List<ScanResult> results = wifiManager.getScanResults();

            for (ScanResult r : results){
                System.out.println("[SCAN] " + "[RESULT]" + " " + r.SSID);

                WifiRecord record = new WifiRecord();
                record.bssid = r.SSID;
                record.timestamp = timestamp;
                record.uid = rn.nextInt();

                //if(recordRepository.getBySSID(record.bssid).isEmpty()){
                recordRepository.insertAll(record);
                //}
                //else{
                    //recordRepository.update(record);
                //}
            }
            recordRepository.close();

            return results;
        }

        protected void onPostExecute() {
            jobFinished(null, true);
        }

    }

    private class SensorMeasureTask extends AsyncTask<SensorEvent, Void, Void> implements SensorEventListener {

        private AppDatabase db;

        private SensorManager mSensorManager;
        private Sensor mAccelerometer;
        private double alpha = 0.8;
        private double gravity[] = {0.0, 0.0, 0.0};
        private double linear_acceleration[] = {0.0, 0.0, 0.0};
        private int samplingPeriodUs = 10000000;
        //private Sensor magnetometre = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];
            Long tsLong = System.currentTimeMillis()/1000;
            String timestamp = tsLong.toString();

            linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
            linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
            linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

            //System.out.println("[DATA] "+"[ACC] " + linear_acceleration[0] + " " + linear_acceleration[1] + " " + linear_acceleration[2]);
            SensorRecordRepository recordRepository = new SensorRecordRepository(getApplicationContext());
            SensorRecord record = new SensorRecord();
            record.sx = linear_acceleration[0];
            record.sy = linear_acceleration[1];
            record.sz = linear_acceleration[2];
            record.timestamp = timestamp;

            List<SensorRecord> records = recordRepository.getAll();
            if(records.size() != 0){
                record.uid = records.get(records.size()-1).uid + 1;
            }
            else{
                record.uid = 0;
            }

            if(records.size() > 840){
                recordRepository.removeRecord(records.get(0));
            }
            recordRepository.insertAll(record);
            //System.out.println("[DATA] "+"[WRITE] " + " NEW_RECORD "
              //      + linear_acceleration[0] + " " + linear_acceleration[1] + " " + linear_acceleration[2]);

            recordRepository.close();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy){
            //empty
        }

        @Override
        protected Void doInBackground(SensorEvent... events) {

            System.out.println("[SENSORS] " + "[ASYNC]" + " Acc AsyncTask Started");
            this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            this.mAccelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            this.mSensorManager.registerListener(this, mAccelerometer, samplingPeriodUs);

            return null;

        }

        protected void onPostExecute() {
            jobFinished(null, true);
        }

    }

}


