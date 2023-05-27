package com.oteusite.aplicaodecomprasandroid;
public class User {
    private int id;
    private String nome;
    private String apelido;
    private String email;
    private String password;
    private  String morada;
    public User(int id, String nome, String apelido, String email, String password, String morada) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.morada = morada;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMorada() {return  morada; }
}
