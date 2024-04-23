package com.example.registrodeponto;

import java.util.ArrayList;

public class Register {
    private String name;
    private String enrollment;
    private String department;
    private String position;
    private ArrayList<Integer> workedHours = new ArrayList<>();


    public Register(String name, String enrollment, String department, String position) {
        this.name = name;
        this.enrollment = enrollment;
        this.department = department;
        this.position = position;
    }

    public ArrayList<Integer> getWorkedHours() {
        return workedHours;
    }
    public void clearRecords (){
        workedHours.clear();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkedHours(ArrayList<Integer> workedHours) {
        this.workedHours = workedHours;
    }

    public void addWorkedHourRecord(int hour){
        workedHours.add(hour);
    }

    public int getSize(){
        return workedHours.size();
    }
    public String getName() {
        return name;
    }

    public String getEnrollment() {
        return enrollment;
    }
}
