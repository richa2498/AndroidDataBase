package com.example.databasedemo;

public class Employee {
    int id;
    String name,dept,joinDtae;
    double salary;

    public Employee(int id, String name, String dept, String joinDtae, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.joinDtae = joinDtae;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getJoinDtae() {
        return joinDtae;
    }

    public double getSalary() {
        return salary;
    }
}
