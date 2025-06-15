package app;

import db.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão bem-sucedida com o banco!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM receita");

            System.out.println("Receitas encontradas:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String data = rs.getString("data");
                int categoriaId = rs.getInt("categoria_id");

                System.out.println(id + " | " + descricao + " | R$" + valor + " | " + data + " | Categoria " + categoriaId);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão ou consulta: " + e.getMessage());
        }
    }
}