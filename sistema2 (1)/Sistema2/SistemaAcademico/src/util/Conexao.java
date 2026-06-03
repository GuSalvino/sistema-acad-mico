package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private static final String USER = "root";
    private static final String PASS = "zetek123";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro na conexão: " + e.getMessage());
        }
    }
}