package ru.mirea.elkinasa.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String superpower;
    public int strengthLevel;

    public Employee(String name, String superpower, int strengthLevel) {
        this.name = name;
        this.superpower = superpower;
        this.strengthLevel = strengthLevel;
    }
}