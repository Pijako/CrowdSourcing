package com.example.tpichel.cs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;


public class ScanActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ListView mListView;
        mListView = (ListView) findViewById(R.id.mlv);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, readRawData());

        mListView.setAdapter(adapter);

    }

    private List<String> readRawData() {

        List<String> results = new LinkedList();

        WifiRecordRepository recordRepository;
        recordRepository = new WifiRecordRepository(getApplicationContext());

        for(WifiRecord r : recordRepository.getAll() ) {
            System.out.println("-----------------------");
            System.out.println("[DATA] " + "[READ]" + r.bssid);
            results.add(r.bssid);
        }

        return results;

    }
}
