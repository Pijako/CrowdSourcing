package com.example.tpichel.cs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        XYPlot plot = (XYPlot) findViewById(R.id.plot);
        List<SensorRecord> results = new LinkedList();

        SensorRecordRepository recordRepository;
        recordRepository = new SensorRecordRepository(getApplicationContext());

        results = recordRepository.getAll();

        if (results.size() > 0) {

            ArrayList linearAcc = new ArrayList<Double>();

            for (SensorRecord sr : results) {
                    linearAcc.add(Math.sqrt(sr.sx*sr.sx + sr.sy*sr.sy + sr.sz*sr.sz));
            }

            //On corrige la taille du tableau pour avoir des valeurs que sur les 2 dernières minutes
            if(linearAcc.size() > 840){
                for(int i=0;i<linearAcc.size()-840;i++){
                    linearAcc.remove(i);
                }
            }

            // transformer les données en series XY
            XYSeries apsXY = new SimpleXYSeries(linearAcc, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Linear Acceleration");
            // définir le format de la courbe (ligne rouge, marqueurs bleus)
            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLUE, null, null);
            //Définir les bornes de Y
            // ajouter la serie XY au plot:
            plot.addSeries(apsXY, series1Format);
        }
        else
        {
            Log.i("[ACC]","No item in result query ");
        }
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs

        setContentView(R.layout.activity_main);
        Intent back = new Intent(SensorActivity.this, MainActivity.class);
        startActivity(back);
    }

}
