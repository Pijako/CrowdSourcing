package com.example.tpichel.cs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by tpichel on 11/01/19.
 */

@Dao
public interface SensorRecordDao {

    @Query("SELECT * FROM sensorRecord")
    List<SensorRecord> getAll();

    @Query("SELECT * FROM sensorRecord WHERE uid IN (:sensorRecordIds)")
    List<SensorRecord> loadAllByIds(int[] sensorRecordIds);

    @Query("SELECT * FROM sensorrecord WHERE timestamp LIKE :timestamp ")
    List<SensorRecord> findByTIMESTAMP(String timestamp);

    @Update
    void update(SensorRecord... sensorRecords);

    @Insert
    void insertAll(SensorRecord... sensorRecords);

    @Delete
    void delete(SensorRecord sensorRecord);

    @Query("DELETE FROM sensorRecord WHERE 1=1")
    void drop();
}
