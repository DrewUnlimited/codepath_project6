package com.example.bitfitpart2;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HealthMetricDao_Impl implements HealthMetricDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HealthMetricEntity> __insertionAdapterOfHealthMetricEntity;

  public HealthMetricDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHealthMetricEntity = new EntityInsertionAdapter<HealthMetricEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `health_metrics` (`id`,`name`,`calorieCount`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthMetricEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getCalories());
      }
    };
  }

  @Override
  public void insert(final HealthMetricEntity metric) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHealthMetricEntity.insert(metric);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<List<HealthMetricEntity>> getAllMetrics() {
    final String _sql = "SELECT * FROM health_metrics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"health_metrics"}, new Callable<List<HealthMetricEntity>>() {
      @Override
      public List<HealthMetricEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "calorieCount");
          final List<HealthMetricEntity> _result = new ArrayList<HealthMetricEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final HealthMetricEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpCalories;
            _tmpCalories = _cursor.getInt(_cursorIndexOfCalories);
            _item = new HealthMetricEntity(_tmpId,_tmpName,_tmpCalories);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
