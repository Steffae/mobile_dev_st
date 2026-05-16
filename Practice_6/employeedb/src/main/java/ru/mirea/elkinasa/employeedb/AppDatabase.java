package ru.mirea.elkinasa.employeedb;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract EmployeeDao employeeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "employee_database")
                    .allowMainThreadQueries() // для простоты, в реальном проекте так не делают
                    .build();
        }
        return instance;
    }
}