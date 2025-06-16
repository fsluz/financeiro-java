package dao;

import model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitaDAO {
    private Connection conn;

    public ReceitaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Receita receita) throws SQLException {
        String sql = "INSERT INTO receita (descricao, valor, data, categoria_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, receita.getDescricao());
            stmt.setDouble(2, receita.getValor());
            
            // Converte a data do formato dd/MM/yyyy para yyyy-MM-dd
            LocalDate data = LocalDate.parse(receita.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setDate(3, java.sql.Date.valueOf(data));
            
            stmt.setInt(4, receita.getCategoriaId());
            stmt.executeUpdate();
        }
    }

    public List<Receita> listar() throws SQLException {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM receita";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Receita(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getDate("data").toString(),
                    rs.getInt("categoria_id")
                ));
            }
        }
        return lista;
    }

    public void atualizar(Receita r) throws SQLException {
        String sql = "UPDATE receita SET descricao = ?, valor = ?, data = ?, categoria_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getDescricao());
            stmt.setDouble(2, r.getValor());
            stmt.setDate(3, Date.valueOf(r.getData()));
            stmt.setInt(4, r.getCategoriaId());
            stmt.setInt(5, r.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Receita buscarPorDescricaoEData(String descricao, String data) throws SQLException {
        String sql = "SELECT * FROM receita WHERE descricao = ? AND data = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Receita(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getDate("data").toString(),
                    rs.getInt("categoria_id")
                );
            }
        }
        return null;
    }
}
