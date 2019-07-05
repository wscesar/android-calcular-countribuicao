package com.example.appcontribuite.model;

public class Contribuinte {
    private int id;
    private String nome;
    private String cpf;
    private String sexo;
    private float salario;
    private int contribuicao;

    public Contribuinte() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public int getContribuicao() {
        return contribuicao;
    }

    public void setContribuicao(int contribuicao) {
        this.contribuicao = contribuicao;
    }
}
