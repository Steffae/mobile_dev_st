package ru.mirea.elkinasa.employeedb;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmployeeDao_Impl implements EmployeeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Employee> __insertionAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter<Employee> __deletionAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter<Employee> __updateAdapterOfEmployee;

  public EmployeeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmployee = new EntityInsertionAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `employees` (`id`,`name`,`superpower`,`strengthLevel`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.superpower == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.superpower);
        }
        stmt.bindLong(4, value.strengthLevel);
      }
    };
    this.__deletionAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `employees` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `employees` SET `id` = ?,`name` = ?,`superpower` = ?,`strengthLevel` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.superpower == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.superpower);
        }
        stmt.bindLong(4, value.strengthLevel);
        stmt.bindLong(5, value.id);
      }
    };
  }

  @Override
  public void insert(final Employee employee) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEmployee.insert(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Employee employee) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEmployee.handle(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Employee employee) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEmployee.handle(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Employee> getAll() {
    final String _sql = "SELECT * FROM employees";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSuperpower = CursorUtil.getColumnIndexOrThrow(_cursor, "superpower");
      final int _cursorIndexOfStrengthLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "strengthLevel");
      final List<Employee> _result = new ArrayList<Employee>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Employee _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSuperpower;
        if (_cursor.isNull(_cursorIndexOfSuperpower)) {
          _tmpSuperpower = null;
        } else {
          _tmpSuperpower = _cursor.getString(_cursorIndexOfSuperpower);
        }
        final int _tmpStrengthLevel;
        _tmpStrengthLevel = _cursor.getInt(_cursorIndexOfStrengthLevel);
        _item = new Employee(_tmpName,_tmpSuperpower,_tmpStrengthLevel);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Employee getById(final long id) {
    final String _sql = "SELECT * FROM employees WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSuperpower = CursorUtil.getColumnIndexOrThrow(_cursor, "superpower");
      final int _cursorIndexOfStrengthLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "strengthLevel");
      final Employee _result;
      if(_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSuperpower;
        if (_cursor.isNull(_cursorIndexOfSuperpower)) {
          _tmpSuperpower = null;
        } else {
          _tmpSuperpower = _cursor.getString(_cursorIndexOfSuperpower);
        }
        final int _tmpStrengthLevel;
        _tmpStrengthLevel = _cursor.getInt(_cursorIndexOfStrengthLevel);
        _result = new Employee(_tmpName,_tmpSuperpower,_tmpStrengthLevel);
        _result.id = _cursor.getLong(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
