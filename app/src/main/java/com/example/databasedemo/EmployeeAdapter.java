package com.example.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.ListIterator;

public class EmployeeAdapter extends ArrayAdapter {

    Context  mContext;
    int layoutRes;
    List<Employee> employees;
    DataBaseHelper mDatabase;

    public EmployeeAdapter(@NonNull Context mContext, int layoutRes, List<Employee> employees, DataBaseHelper mDatabase) {
        super(mContext, layoutRes,employees);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.employees = employees;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes,null);
        TextView tvname = v.findViewById(R.id.tv_name);
        TextView tvsalary = v.findViewById(R.id.tv_salary);
        TextView tvdept = v.findViewById(R.id.tv_department);
        TextView tvjoinDate = v.findViewById(R.id.tv_joindate);


        final Employee employee = employees.get(position);
        tvname.setText(employee.getName());
        tvsalary.setText(String.valueOf(employee.getSalary()));
        tvdept.setText(employee.getDept());
        tvjoinDate.setText(employee.getJoinDtae());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmoloyee(employee);
            }
        });

        return  v;

    }

    private void deleteEmoloyee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  String sql = "DELETE FROM employees WHERE id=?";
               // mDatabase.execSQL(sql,new Integer[]{employee.getId()});


               if( mDatabase.deleteEmployee(employee.getId())){
                   loadEmployyees();
               }


            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateEmployee(final Employee employee) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Update Empoyee");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View customLayout = inflater.inflate(R.layout.update_employe, null);
        builder.setView(customLayout);

        final EditText updateName = customLayout.findViewById(R.id.update_name);
        final EditText updateSlary = customLayout.findViewById(R.id.update_salary);
        final Spinner spinner = customLayout.findViewById(R.id.spinnerDept);

        updateName.setText(employee.getName());
        updateSlary.setText(String.valueOf(employee.getSalary()));

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        customLayout.findViewById(R.id.btn_update_emp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = updateName.getText().toString().trim();
                String salary = updateSlary.getText().toString().trim();
                String depart = spinner.getSelectedItem().toString();

                if (name.isEmpty()){
                    updateName.setError("Name is Mandadory");
                    updateName.requestFocus();
                    return;

                }
                if (salary.isEmpty()){
                    updateSlary.setError("Salary is mandaor");
                    updateSlary.requestFocus();
                    return;
                }

               // String sql = "UPDATE employees SET name = ?, department= ?,salary = ? WHERE id = ?";
               // mDatabase.execSQL(sql,new String[]{name,depart,salary,String.valueOf(employee.getId())});
                if (mDatabase.updateEmployee(employee.getId(),name,depart,Double.parseDouble(salary))){
                    Toast.makeText(mContext, "EMPLOYEE UPDATED", Toast.LENGTH_SHORT).show();
                    loadEmployyees();
                }else {
                    Toast.makeText(mContext, "EMPLOYEE NOT UPDATED", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });



    }

    private void loadEmployyees() {

        String sql = "SELECT * FROM employees";
       // Cursor c = mDatabase.rawQuery(sql,null);
        Cursor c = mDatabase.getAllEmployee();
        employees.clear();
        if (c.moveToFirst()){


            do {
                employees.add(new Employee(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getDouble(4)));

            }while (c.moveToNext());
            c.close();

        }
        notifyDataSetChanged();
    }

}
