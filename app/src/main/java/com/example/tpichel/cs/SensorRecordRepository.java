package com.example.tpichel.cs;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by tpichel on 18/01/19.
 */

public class SensorRecordRepository {

    private String DB_NAME = "db";

    private AppDatabase sensorRecordDatabase;

    public void update(SensorRecord... records){
        sensorRecordDatabase.sensorRecordDao().update(records);
    }

    public SensorRecordRepository(Context context) {
        sensorRecordDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void insertAll(SensorRecord... records) {
        sensorRecordDatabase.sensorRecordDao().insertAll(records);
    }

    public List<SensorRecord> getAll() {
        return sensorRecordDatabase.sensorRecordDao().getAll();
    }

    public List<SensorRecord> getByTIMESTAMP(String bssids) {
        return sensorRecordDatabase.sensorRecordDao().findByTIMESTAMP(bssids);
    }

    public void dropTable(){
        sensorRecordDatabase.recordDao().drop();
    }

}