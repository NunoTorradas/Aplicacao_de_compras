package com.oteusite.aplicaodecomprasandroid;
public class User {
    private int id;
    private String nome;
    private String apelido;
    private String email;

    private String password;

    public User(int id, String nome, String apelido, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;

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
}
