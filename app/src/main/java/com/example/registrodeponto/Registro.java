package com.example.registrodeponto;

import java.util.ArrayList;

public class Registro {
    private String name;
    private String enrollment;
    private String department;
    private String position;
    private ArrayList<Integer> workedHours = new ArrayList<>();


    public Registro(String nome, String matricula, String lotacao, String cargo) {
        this.name = nome;
        this.enrollment = matricula;
        this.department = lotacao;
        this.position = cargo;
    }

    public ArrayList<Integer> getWorkedHours() {
        return workedHours;
    }
    public void clearRecords (){
        workedHours.clear();
    }

    public void addWorkedHourRecord(int horario){
        workedHours.add(horario);
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
