package app;

import dao.ReceitaDAO;
import db.ConnectionFactory;
import model.Receita;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            ReceitaDAO receitaDAO = new ReceitaDAO(conn);

            // 1. Inserir
            Receita nova = new Receita(0, "Venda online", 550.0, "2025-06-15", 1);
            receitaDAO.inserir(nova);
            System.out.println("✅ Receita inserida!");

            // 2. Listar
            List<Receita> receitas = receitaDAO.listar();
            System.out.println("\n📋 Todas as receitas:");
            for (Receita r : receitas) {
                System.out.printf("%d | %s | R$%.2f | %s | Categoria %d%n",
                        r.getId(), r.getDescricao(), r.getValor(), r.getData(), r.getCategoriaId());
            }

            // 3. Atualizar
            if (!receitas.isEmpty()) {
                Receita r = receitas.get(0);
                r.setDescricao("Pagamento atualizado");
                r.setValor(999.99);
                receitaDAO.atualizar(r);
                System.out.println("🔁 Receita atualizada: " + r.getId());
            }

            // 4. Excluir
            if (receitas.size() > 1) {
                int idParaExcluir = receitas.get(1).getId();
                receitaDAO.excluir(idParaExcluir);
                System.out.println("❌ Receita excluída: " + idParaExcluir);
            }

        } catch (Exception e) {
            System.out.println("🚨 Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
