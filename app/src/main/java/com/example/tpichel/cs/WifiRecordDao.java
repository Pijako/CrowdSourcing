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
public interface WifiRecordDao {

    @Query("SELECT * FROM WifiRecord")
    List<WifiRecord> getAll();

    @Query("SELECT * FROM WifiRecord WHERE uid IN (:recordIds)")
    List<WifiRecord> loadAllByIds(int[] recordIds);

    @Query("SELECT * FROM WifiRecord WHERE bssid LIKE :bssid AND " +
            "timestamp LIKE :timestamp LIMIT 1")
    List<WifiRecord> findByBSSIDnTIMESTAMP(String bssid, int timestamp);

    @Query("SELECT * FROM WifiRecord WHERE bssid LIKE :bssid ")
    List<WifiRecord> findByBSSID(String bssid);

    @Update
    void update(WifiRecord... records);

    @Insert
    void insertAll(WifiRecord... records);

    @Delete
    void delete(WifiRecord record);

    @Query("DELETE FROM WifiRecord WHERE 1=1")
    void drop();
}
