package com.example.registrodeponto;

import java.util.ArrayList;

public class Registro {
    private String nome;
    private String matricula;
    private String lotacao;
    private String cargo;
    private ArrayList<Integer> pontos = new ArrayList<>();

    public Registro(String nome, String matricula, String lotacao, String cargo) {
        this.nome = nome;
        this.matricula = matricula;
        this.lotacao = lotacao;
        this.cargo = cargo;
    }

    public void adicionarRegistro(int horario){
        pontos.add(horario);
    }

    public int getSize(){
        return pontos.size();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
