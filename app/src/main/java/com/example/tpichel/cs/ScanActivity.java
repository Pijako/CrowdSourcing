package com.example.tpichel.cs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class ScanActivity extends AppCompatActivity {

    private AppDatabase db;
    private List<WifiRecord> results = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        XYPlot plot = (XYPlot) findViewById(R.id.plot2);

        WifiRecordRepository recordRepository;
        recordRepository = new WifiRecordRepository(getApplicationContext());

        this.results = recordRepository.getAll();
        recordRepository.close();

        if (results.size() > 0) {

            List<String> timestamp = new LinkedList<String>();
            List<Integer> nbApp = new LinkedList<Integer>();
            String previousTimestamp = new String(results.get(0).timestamp);
            int count = 0;

            for(WifiRecord wr : results){
                nbApp.add(Collections.frequency(getTimestamps(), wr.timestamp));

            }

            System.out.println(nbApp.toString());

            // transformer les données en series XY
            XYSeries apsXY = new SimpleXYSeries(nbApp, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Nombre APs");
            // définir le format de la courbe (ligne rouge, marqueurs bleus)
            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLUE, null, null);
            // ajouter la serie XY au plot:
            plot.addSeries(apsXY, series1Format);
        }
        else
        {
            Log.i("[APs]","No item in result query ");
        }



        ListView mListView;
        mListView = (ListView) findViewById(R.id.mlv);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getRawData());

        mListView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs

        setContentView(R.layout.activity_main);
        Intent back = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(back);
    }

    private List<String> getRawData() {

        List<String> stringList = new LinkedList<String>();

        for(WifiRecord r : this.results ) {
            //System.out.println("-----------------------");
            //System.out.println("[DATA] " + "[READ]" + r.bssid);
            if(!stringList.contains(r.bssid)) {
                Timestamp t = new Timestamp(Long.parseLong(r.timestamp));
                String heure = new Date(t.getTime()).toString();
                stringList.add(r.bssid + "                        " + heure.substring(0, heure.length()-14));
            }
        }
        return stringList;
    }

    private List<String> getTimestamps() {

        List<String> stringList = new LinkedList<String>();

        for(WifiRecord r : this.results ) {
            stringList.add(r.timestamp);

        }
        return stringList;
    }
}
