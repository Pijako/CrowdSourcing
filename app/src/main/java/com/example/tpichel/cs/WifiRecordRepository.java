package com.example.tpichel.cs;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by tpichel on 18/01/19.
 */

public class WifiRecordRepository {

    private String DB_NAME = "db";

    private AppDatabase recordDatabase;

    public void update(WifiRecord... records){
        recordDatabase.recordDao().update(records);
    }

    public WifiRecordRepository(Context context) {
        recordDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
    }

    public void insertAll(WifiRecord... records) {
        recordDatabase.recordDao().insertAll(records);
    }

    public List<WifiRecord> getAll() {
        return recordDatabase.recordDao().getAll();
    }

    public List<WifiRecord> getBySSID(String bssids) {
        return recordDatabase.recordDao().findByBSSID(bssids);
    }

    public void dropTable(){
        recordDatabase.recordDao().drop();
    }

}