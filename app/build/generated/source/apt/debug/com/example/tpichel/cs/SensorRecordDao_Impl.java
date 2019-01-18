package com.example.tpichel.cs;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.arch.persistence.room.util.StringUtil;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SensorRecordDao_Impl implements SensorRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSensorRecord;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSensorRecord;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfSensorRecord;

  private final SharedSQLiteStatement __preparedStmtOfDrop;

  public SensorRecordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSensorRecord = new EntityInsertionAdapter<SensorRecord>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `SensorRecord`(`uid`,`sx`,`sy`,`sz`,`timestamp`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SensorRecord value) {
        stmt.bindLong(1, value.uid);
        stmt.bindDouble(2, value.sx);
        stmt.bindDouble(3, value.sy);
        stmt.bindDouble(4, value.sz);
        if (value.timestamp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timestamp);
        }
      }
    };
    this.__deletionAdapterOfSensorRecord = new EntityDeletionOrUpdateAdapter<SensorRecord>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `SensorRecord` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SensorRecord value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__updateAdapterOfSensorRecord = new EntityDeletionOrUpdateAdapter<SensorRecord>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `SensorRecord` SET `uid` = ?,`sx` = ?,`sy` = ?,`sz` = ?,`timestamp` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SensorRecord value) {
        stmt.bindLong(1, value.uid);
        stmt.bindDouble(2, value.sx);
        stmt.bindDouble(3, value.sy);
        stmt.bindDouble(4, value.sz);
        if (value.timestamp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timestamp);
        }
        stmt.bindLong(6, value.uid);
      }
    };
    this.__preparedStmtOfDrop = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM sensorRecord WHERE 1=1";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(SensorRecord... sensorRecords) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSensorRecord.insert(sensorRecords);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(SensorRecord sensorRecord) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfSensorRecord.handle(sensorRecord);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(SensorRecord... sensorRecords) {
    __db.beginTransaction();
    try {
      __updateAdapterOfSensorRecord.handleMultiple(sensorRecords);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void drop() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDrop.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDrop.release(_stmt);
    }
  }

  @Override
  public List<SensorRecord> getAll() {
    final String _sql = "SELECT * FROM sensorRecord";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfSx = _cursor.getColumnIndexOrThrow("sx");
      final int _cursorIndexOfSy = _cursor.getColumnIndexOrThrow("sy");
      final int _cursorIndexOfSz = _cursor.getColumnIndexOrThrow("sz");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<SensorRecord> _result = new ArrayList<SensorRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SensorRecord _item;
        _item = new SensorRecord();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        _item.sx = _cursor.getDouble(_cursorIndexOfSx);
        _item.sy = _cursor.getDouble(_cursorIndexOfSy);
        _item.sz = _cursor.getDouble(_cursorIndexOfSz);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SensorRecord> loadAllByIds(int[] sensorRecordIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM sensorRecord WHERE uid IN (");
    final int _inputSize = sensorRecordIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : sensorRecordIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfSx = _cursor.getColumnIndexOrThrow("sx");
      final int _cursorIndexOfSy = _cursor.getColumnIndexOrThrow("sy");
      final int _cursorIndexOfSz = _cursor.getColumnIndexOrThrow("sz");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<SensorRecord> _result = new ArrayList<SensorRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SensorRecord _item_1;
        _item_1 = new SensorRecord();
        _item_1.uid = _cursor.getInt(_cursorIndexOfUid);
        _item_1.sx = _cursor.getDouble(_cursorIndexOfSx);
        _item_1.sy = _cursor.getDouble(_cursorIndexOfSy);
        _item_1.sz = _cursor.getDouble(_cursorIndexOfSz);
        _item_1.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SensorRecord> findByTIMESTAMP(String timestamp) {
    final String _sql = "SELECT * FROM sensorrecord WHERE timestamp LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (timestamp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, timestamp);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfSx = _cursor.getColumnIndexOrThrow("sx");
      final int _cursorIndexOfSy = _cursor.getColumnIndexOrThrow("sy");
      final int _cursorIndexOfSz = _cursor.getColumnIndexOrThrow("sz");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<SensorRecord> _result = new ArrayList<SensorRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SensorRecord _item;
        _item = new SensorRecord();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        _item.sx = _cursor.getDouble(_cursorIndexOfSx);
        _item.sy = _cursor.getDouble(_cursorIndexOfSy);
        _item.sz = _cursor.getDouble(_cursorIndexOfSz);
        _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
