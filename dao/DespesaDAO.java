package dao;

import model.Despesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaDAO {
    private Connection conn;

    public DespesaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Despesa despesa) throws SQLException {
        String sql = "INSERT INTO despesa (descricao, valor, data, categoria_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, despesa.getDescricao());
            stmt.setDouble(2, despesa.getValor());
            
            // Converte a data do formato dd/MM/yyyy para yyyy-MM-dd
            LocalDate data = LocalDate.parse(despesa.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setDate(3, java.sql.Date.valueOf(data));
            
            stmt.setInt(4, despesa.getCategoriaId());
            stmt.executeUpdate();
        }
    }

    public List<Despesa> listar() throws SQLException {
        List<Despesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM despesa";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Despesa(
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

    public void atualizar(Despesa d) throws SQLException {
        String sql = "UPDATE despesa SET descricao = ?, valor = ?, data = ?, categoria_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getDescricao());
            stmt.setDouble(2, d.getValor());
            stmt.setDate(3, Date.valueOf(d.getData()));
            stmt.setInt(4, d.getCategoriaId());
            stmt.setInt(5, d.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM despesa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Despesa buscarPorDescricaoEData(String descricao, String data) throws SQLException {
        String sql = "SELECT * FROM despesa WHERE descricao = ? AND data = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.setDate(2, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Despesa(
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
