package com.oteusite.aplicaodecomprasandroid;

import java.sql.*;

public class ConnectionManager {
    //Dados para a conexão
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String connectionURL = "jdbc:mysql://108.167.172.196/oteusite_aplicacaomovel";
    private final String userName = "oteusite_IP";
    private final String password = "mK4He_k*WUno";

    private Connection con = null;
    //Permite o uso
    public ConnectionManager(){
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    //Cria a conexão
    public Connection createConnection() {
        try {
            con = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    //Fecha a conexão
    public void closeConnection() {
        try {
            this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
