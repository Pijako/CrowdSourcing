package com.example.tpichel.cs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tpichel on 11/01/19.
 */

@Entity
public class WifiRecord {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "bssid")
    public String bssid;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

}
