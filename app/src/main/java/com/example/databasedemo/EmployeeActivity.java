package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    DataBaseHelper mDatabase;
   // SQLiteDatabase mDatabase;
    List<Employee> employeeList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        listView = findViewById(R.id.lvEmployee);
        employeeList = new ArrayList<>();
      //  mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);
        mDatabase = new DataBaseHelper(this);
        loadEmployees();



    }

    private void loadEmployees() {
//        String sql = "SELECT * FROM employees";
//        Cursor cursor = mDatabase.rawQuery(sql,null);

        Cursor cursor = mDatabase.getAllEmployee();
        if (cursor.moveToFirst()){

            do {
                employeeList.add(new Employee(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)));


            }while (cursor.moveToNext());

            cursor.close();
        }
        //in order to view in list,we use custom adapter ;

EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,R.layout.list_layout_employee,employeeList,mDatabase);
        listView.setAdapter(employeeAdapter);

    }
}
