package com.example.tpichel.cs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tpichel on 18/01/19.
 */

@Entity
public class SensorRecord {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "sx")
    public double sx;

    @ColumnInfo(name = "sy")
    public double sy;

    @ColumnInfo(name = "sz")
    public double sz;

    @ColumnInfo(name = "timestamp")
    public String timestamp;
}
