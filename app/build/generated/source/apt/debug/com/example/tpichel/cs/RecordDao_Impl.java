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
public class RecordDao_Impl implements RecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRecord;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRecord;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRecord;

  private final SharedSQLiteStatement __preparedStmtOfDrop;

  public RecordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecord = new EntityInsertionAdapter<Record>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Record`(`uid`,`bssid`,`timestamp`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Record value) {
        stmt.bindLong(1, value.uid);
        if (value.bssid == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.bssid);
        }
        if (value.timestamp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.timestamp);
        }
      }
    };
    this.__deletionAdapterOfRecord = new EntityDeletionOrUpdateAdapter<Record>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Record` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Record value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__updateAdapterOfRecord = new EntityDeletionOrUpdateAdapter<Record>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Record` SET `uid` = ?,`bssid` = ?,`timestamp` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Record value) {
        stmt.bindLong(1, value.uid);
        if (value.bssid == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.bssid);
        }
        if (value.timestamp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.timestamp);
        }
        stmt.bindLong(4, value.uid);
      }
    };
    this.__preparedStmtOfDrop = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM record WHERE 1=1";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(Record... records) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecord.insert(records);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Record record) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfRecord.handle(record);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Record... records) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRecord.handleMultiple(records);
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
  public List<Record> getAll() {
    final String _sql = "SELECT * FROM record";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBssid = _cursor.getColumnIndexOrThrow("bssid");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<Record> _result = new ArrayList<Record>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Record _item;
        _item = new Record();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        _item.bssid = _cursor.getString(_cursorIndexOfBssid);
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
  public List<Record> loadAllByIds(int[] recordIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM record WHERE uid IN (");
    final int _inputSize = recordIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : recordIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBssid = _cursor.getColumnIndexOrThrow("bssid");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<Record> _result = new ArrayList<Record>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Record _item_1;
        _item_1 = new Record();
        _item_1.uid = _cursor.getInt(_cursorIndexOfUid);
        _item_1.bssid = _cursor.getString(_cursorIndexOfBssid);
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
  public Record findByBSSIDnTIMESTAMP(String bssid, int timestamp) {
    final String _sql = "SELECT * FROM record WHERE bssid LIKE ? AND timestamp LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (bssid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bssid);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, timestamp);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBssid = _cursor.getColumnIndexOrThrow("bssid");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final Record _result;
      if(_cursor.moveToFirst()) {
        _result = new Record();
        _result.uid = _cursor.getInt(_cursorIndexOfUid);
        _result.bssid = _cursor.getString(_cursorIndexOfBssid);
        _result.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Record> findByBSSID(String bssid) {
    final String _sql = "SELECT * FROM record WHERE bssid LIKE ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bssid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bssid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfBssid = _cursor.getColumnIndexOrThrow("bssid");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final List<Record> _result = new ArrayList<Record>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Record _item;
        _item = new Record();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        _item.bssid = _cursor.getString(_cursorIndexOfBssid);
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
