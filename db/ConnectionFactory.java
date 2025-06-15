package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/financas_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sua senha aqui"; // coloque sua senha real aqui

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}