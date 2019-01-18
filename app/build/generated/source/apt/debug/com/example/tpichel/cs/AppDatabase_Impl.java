package com.example.tpichel.cs;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile WifiRecordDao _wifiRecordDao;

  private volatile SensorRecordDao _sensorRecordDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `WifiRecord` (`uid` INTEGER NOT NULL, `bssid` TEXT, `timestamp` TEXT, PRIMARY KEY(`uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SensorRecord` (`uid` INTEGER NOT NULL, `sx` REAL NOT NULL, `sy` REAL NOT NULL, `sz` REAL NOT NULL, `timestamp` TEXT, PRIMARY KEY(`uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"1d51c88a82c960dc6bb681c19d281b4f\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `WifiRecord`");
        _db.execSQL("DROP TABLE IF EXISTS `SensorRecord`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWifiRecord = new HashMap<String, TableInfo.Column>(3);
        _columnsWifiRecord.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1));
        _columnsWifiRecord.put("bssid", new TableInfo.Column("bssid", "TEXT", false, 0));
        _columnsWifiRecord.put("timestamp", new TableInfo.Column("timestamp", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWifiRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWifiRecord = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWifiRecord = new TableInfo("WifiRecord", _columnsWifiRecord, _foreignKeysWifiRecord, _indicesWifiRecord);
        final TableInfo _existingWifiRecord = TableInfo.read(_db, "WifiRecord");
        if (! _infoWifiRecord.equals(_existingWifiRecord)) {
          throw new IllegalStateException("Migration didn't properly handle WifiRecord(com.example.tpichel.cs.WifiRecord).\n"
                  + " Expected:\n" + _infoWifiRecord + "\n"
                  + " Found:\n" + _existingWifiRecord);
        }
        final HashMap<String, TableInfo.Column> _columnsSensorRecord = new HashMap<String, TableInfo.Column>(5);
        _columnsSensorRecord.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1));
        _columnsSensorRecord.put("sx", new TableInfo.Column("sx", "REAL", true, 0));
        _columnsSensorRecord.put("sy", new TableInfo.Column("sy", "REAL", true, 0));
        _columnsSensorRecord.put("sz", new TableInfo.Column("sz", "REAL", true, 0));
        _columnsSensorRecord.put("timestamp", new TableInfo.Column("timestamp", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSensorRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSensorRecord = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSensorRecord = new TableInfo("SensorRecord", _columnsSensorRecord, _foreignKeysSensorRecord, _indicesSensorRecord);
        final TableInfo _existingSensorRecord = TableInfo.read(_db, "SensorRecord");
        if (! _infoSensorRecord.equals(_existingSensorRecord)) {
          throw new IllegalStateException("Migration didn't properly handle SensorRecord(com.example.tpichel.cs.SensorRecord).\n"
                  + " Expected:\n" + _infoSensorRecord + "\n"
                  + " Found:\n" + _existingSensorRecord);
        }
      }
    }, "1d51c88a82c960dc6bb681c19d281b4f", "5ddd4d1879e47b3361befcf3a0a6b0e5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "WifiRecord","SensorRecord");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `WifiRecord`");
      _db.execSQL("DELETE FROM `SensorRecord`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public WifiRecordDao recordDao() {
    if (_wifiRecordDao != null) {
      return _wifiRecordDao;
    } else {
      synchronized(this) {
        if(_wifiRecordDao == null) {
          _wifiRecordDao = new WifiRecordDao_Impl(this);
        }
        return _wifiRecordDao;
      }
    }
  }

  @Override
  public SensorRecordDao sensorRecordDao() {
    if (_sensorRecordDao != null) {
      return _sensorRecordDao;
    } else {
      synchronized(this) {
        if(_sensorRecordDao == null) {
          _sensorRecordDao = new SensorRecordDao_Impl(this);
        }
        return _sensorRecordDao;
      }
    }
  }
}
