package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final String DATABASE_NAME = "myDatabase";
    //SQLiteDatabase mDatabase;

    DataBaseHelper mDatabase;

    EditText editTextName ,editTextSalary;
    Spinner spinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.edittextName);
        editTextSalary = findViewById(R.id.editTextSalary);
        spinnerData =findViewById(R.id.spinnerDept);

        findViewById(R.id.btnAddEmployee).setOnClickListener(this);
        findViewById(R.id.tvViewEmp).setOnClickListener(this);



        //use of database give name to databse
        
        //open or creatae new database
        
//        mDatabase = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
//        createTable();


        mDatabase = new DataBaseHelper(this);

    }
/*
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees(" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT ,"+
                "name VARCHAR(200) NOT NULL,"+"department VARCHAR(200) NOT NULL,"+"joindate DATETIME NOT NULL,"+
                "salary DOUBLE NOT NULL);";
        mDatabase.execSQL(sql);
    
    
    }
   */
    


    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btnAddEmployee:
             addEmp();
             break;
             
         case R.id.tvViewEmp:
             Intent intent = new Intent(this,EmployeeActivity.class);
             startActivity(intent);
             break;
             
     }
    }

    private void addEmp() {
        String name = editTextName.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String dept = spinnerData.getSelectedItem().toString().trim();
        
        //usiong calander object to get current time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joinDate = simpleDateFormat.format(calendar.getTime());
        
        if (name.isEmpty()){
            editTextName.setError("name field is mandadory");
            editTextName.requestFocus();
            return;
        }
        if (salary.isEmpty()){
            editTextSalary.setError("slary cant be emopyu");
            editTextSalary.requestFocus();
            return;
        }
        
      /*
        String sql = "INSERT INTO employees (name,department,joindate,salary) VALUES(?,?,?,?)";
        mDatabase.execSQL(sql,new String[]{name,dept,joinDate,salary});
        Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        */

      if (mDatabase.addEmployee(name,dept,joinDate,Double.parseDouble(salary))){
          Toast.makeText(this, "Employee addaed", Toast.LENGTH_SHORT).show();
      }else {
          Toast.makeText(this, "Employee is not addaed", Toast.LENGTH_SHORT).show();
      }
    }
}
