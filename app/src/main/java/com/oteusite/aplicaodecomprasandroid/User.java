package com.oteusite.aplicaodecomprasandroid;
public class User {
    private int id;
    private String username;
    private String nome;
    private String email;
    private String morada;
    private String password;

    public User(int id, String username, String nome, String email, String morada, String password) {
        this.id = id;
        this.username = username;
        this.nome = nome;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMorada() {return  morada; }
    public String getUsername() {
        return username;
    }
}
