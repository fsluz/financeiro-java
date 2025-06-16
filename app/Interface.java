package app;

// O nome deste arquivo deve ser Interface.java
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import dao.DespesaDAO;
import dao.ReceitaDAO;
import dao.CategoriaDAO;
import db.ConnectionFactory;
import model.Despesa;
import model.Receita;
import model.Categoria;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe principal da aplicação que cria a interface gráfica de usuário (GUI)
 * para o controle financeiro.
 * O nome da classe foi alterado para "Interface" conforme solicitado.
 */
public class Interface extends JFrame {

    // 🟦 ALTERAÇÃO: Nova paleta de cores (tema claro e moderno)
    private static final Color FUNDO_PRINCIPAL = new Color(245, 247, 250);
    private static final Color FUNDO_SECUNDARIO = new Color(224, 231, 255);
    private static final Color COR_PRIMARIA = new Color(59, 130, 246);
    private static final Color COR_SECUNDARIA = new Color(37, 99, 235);
    private static final Color TEXTO_PRINCIPAL = new Color(17, 24, 39);
    private static final Color TEXTO_SECUNDARIO = new Color(107, 114, 128);
    private static final Color COR_SUCESSO = new Color(16, 185, 129);
    private static final Color COR_ERRO = new Color(239, 68, 68);

    // Componentes da interface
    private JTextField campoDescricao;
    private JTextField campoValor;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboCategoria;
    private JTextField campoData;
    private JLabel labelSaldo;
    private DefaultTableModel tabelaModelo;
    private JTable tabelaLancamentos;

    // Estruturas de dados para armazenar as finanças
    private double saldo = 0.0;
    private Map<String, Double> categoriasTotais = new HashMap<>();
    private Map<String, Double> saldoPorData = new TreeMap<>();
    private final String[] categoriasReceita = {"Salário", "Freelance", "Investimentos", "Venda de Itens", "Reembolso", "Prêmios", "Outros"};
    private final String[] categoriasDespesa = {"Alimentação", "Transporte", "Moradia", "Contas", "Lazer", "Saúde", "Educação", "Compras", "Impostos", "Outros"};

    // Painéis que contêm os gráficos
    private ChartPanel painelGraficoPizza;
    private ChartPanel painelGraficoLinha;

    // Adicionar os DAOs como atributos da classe
    private DespesaDAO despesaDAO;
    private ReceitaDAO receitaDAO;
    private CategoriaDAO categoriaDAO;
    private Connection conn;

    /**
     * Construtor da classe Interface.
     * Configura a janela principal e inicializa os componentes.
     * O nome do construtor foi alterado de "ControleFinanceiro" para "Interface".
     */
    public Interface() {
        setTitle("Controle Financeiro Pessoal");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define a cor de fundo principal da janela
        this.getContentPane().setBackground(FUNDO_PRINCIPAL);

        // Inicializa os componentes antes de tentar usar
        criarComponentes();

        try {
            // Inicializa a conexão e os DAOs usando o ConnectionFactory
            conn = ConnectionFactory.getConnection();
            despesaDAO = new DespesaDAO(conn);
            receitaDAO = new ReceitaDAO(conn);
            categoriaDAO = new CategoriaDAO(conn);
            
            // Carrega os dados iniciais
            carregarDados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.exit(1);
        }

        setVisible(true);
    }

    /**
     * Cria e organiza todos os componentes da interface gráfica (botões, campos, tabelas, etc.).
     */
    private void criarComponentes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(FUNDO_PRINCIPAL);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inicializa o modelo da tabela antes de usá-lo
        String[] colunas = {"Data", "Descrição", "Tipo", "Categoria", "Valor (R$)"};
        tabelaModelo = new DefaultTableModel(colunas, 0);
        tabelaLancamentos = new JTable(tabelaModelo);

        // Painel para a entrada de novos lançamentos (receitas/despesas)
        JPanel painelEntrada = new JPanel(new GridLayout(2, 7, 10, 8));
        painelEntrada.setBackground(FUNDO_SECUNDARIO);
        painelEntrada.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 210, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        campoDescricao = new JTextField();
        campoValor = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"Receita", "Despesa"});
        comboCategoria = new JComboBox<>();
        campoData = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Estiliza os campos de entrada com o novo tema
        JComponent[] campos = {campoDescricao, campoValor, comboTipo, comboCategoria, campoData};
        for(JComponent c : campos) {
            c.setBackground(Color.WHITE);
            c.setForeground(TEXTO_PRINCIPAL);
            c.setBorder(new LineBorder(new Color(200, 210, 230)));
        }
        ((JTextField)campoData).setCaretColor(COR_PRIMARIA);
        ((JTextField)campoDescricao).setCaretColor(COR_PRIMARIA);
        ((JTextField)campoValor).setCaretColor(COR_PRIMARIA);
        
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoRemover = new JButton("Remover");

        // Estiliza os botões com o novo tema
        botaoAdicionar.setBackground(COR_PRIMARIA);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setBorderPainted(false);
        
        botaoRemover.setBackground(TEXTO_SECUNDARIO);
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.setBorderPainted(false);
        
        // Adiciona os rótulos (labels) para os campos de entrada
        JLabel[] rotulos = {new JLabel("Data"), new JLabel("Descrição"), new JLabel("Tipo"), new JLabel("Categoria"), new JLabel("Valor (R$)"), new JLabel(""), new JLabel("")};
        for(JLabel r : rotulos) {
            r.setForeground(TEXTO_SECUNDARIO);
            painelEntrada.add(r);
        }

        // Adiciona os componentes ao painel de entrada
        painelEntrada.add(campoData);
        painelEntrada.add(campoDescricao);
        painelEntrada.add(comboTipo);
        painelEntrada.add(comboCategoria);
        painelEntrada.add(campoValor);
        painelEntrada.add(botaoAdicionar);
        painelEntrada.add(botaoRemover);
        
        // Estiliza a tabela com o novo tema
        tabelaLancamentos.setBackground(Color.WHITE);
        tabelaLancamentos.setForeground(TEXTO_PRINCIPAL);
        tabelaLancamentos.setGridColor(new Color(229, 231, 235));
        tabelaLancamentos.setSelectionBackground(FUNDO_SECUNDARIO);
        tabelaLancamentos.setSelectionForeground(COR_SECUNDARIA);
        tabelaLancamentos.setRowHeight(24);
        JTableHeader header = tabelaLancamentos.getTableHeader();
        header.setBackground(FUNDO_PRINCIPAL);
        header.setForeground(TEXTO_PRINCIPAL);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        
        JScrollPane scrollTabela = new JScrollPane(tabelaLancamentos);
        scrollTabela.setBorder(new LineBorder(new Color(200, 210, 230)));

        // Painel para exibir o saldo total
        JPanel painelSaldo = new JPanel();
        painelSaldo.setBackground(FUNDO_PRINCIPAL);
        labelSaldo = new JLabel("Saldo Atual: R$ 0.00");
        labelSaldo.setFont(new Font("Arial", Font.BOLD, 18));
        labelSaldo.setForeground(TEXTO_PRINCIPAL);
        painelSaldo.add(labelSaldo);
        
        // Cria os painéis para os gráficos
        painelGraficoPizza = new ChartPanel(criarGraficoPizza());
        painelGraficoLinha = new ChartPanel(criarGraficoLinha());
        
        JPanel painelGraficos = new JPanel(new GridLayout(1, 2, 10, 10));
        painelGraficos.setBackground(FUNDO_PRINCIPAL);
        painelGraficos.add(painelGraficoPizza);
        painelGraficos.add(painelGraficoLinha);
        
        // Painel central que agrupa os gráficos e a tabela
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        painelCentro.setBackground(FUNDO_PRINCIPAL);
        painelCentro.add(painelGraficos, BorderLayout.NORTH);
        painelCentro.add(scrollTabela, BorderLayout.CENTER);
        
        // Montagem final da janela, adicionando todos os painéis
        painel.add(painelEntrada, BorderLayout.NORTH);
        painel.add(painelCentro, BorderLayout.CENTER);
        painel.add(painelSaldo, BorderLayout.SOUTH);
        add(painel);
        
        // Configura as ações dos botões e componentes
        botaoAdicionar.addActionListener(e -> adicionarLancamento());
        botaoRemover.addActionListener(e -> removerLancamento());
        comboTipo.addActionListener(e -> atualizarCategorias());
        atualizarCategorias();
    }
    
    /**
     * Cria um gráfico de pizza para mostrar a distribuição de gastos/ganhos por categoria.
     * @return Um objeto JFreeChart do tipo gráfico de pizza.
     */
    private JFreeChart criarGraficoPizza() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        categoriasTotais.forEach(dataset::setValue);
        JFreeChart chart = ChartFactory.createPieChart("Distribuição por Categoria", dataset, true, true, false);

        // Estiliza o gráfico de pizza com o novo tema
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(TEXTO_PRINCIPAL);
        if(chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setItemPaint(TEXTO_SECUNDARIO);
        }
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelPaint(TEXTO_PRINCIPAL);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        // Define cores específicas para algumas categorias
        plot.setSectionPaint("Salário", COR_SUCESSO);
        plot.setSectionPaint("Alimentação", COR_ERRO);
        plot.setSectionPaint("Transporte", new Color(251, 146, 60));
        plot.setSectionPaint("Moradia", COR_PRIMARIA);

        return chart;
    }

    /**
     * Cria um gráfico de linha para mostrar a evolução do saldo ao longo do tempo.
     * @return Um objeto JFreeChart do tipo gráfico de linha.
     */
    private JFreeChart criarGraficoLinha() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        saldoPorData.forEach((data, saldo) -> dataset.addValue(saldo, "Saldo", data));
        JFreeChart chart = ChartFactory.createLineChart("Evolução do Saldo", "Data", "Saldo (R$)", dataset);
        
        // Estiliza o gráfico de linha com o novo tema
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(TEXTO_PRINCIPAL);
        if(chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setItemPaint(TEXTO_SECUNDARIO);
        }
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(FUNDO_PRINCIPAL);
        plot.setRangeGridlinePaint(new Color(200, 210, 230));
        plot.getDomainAxis().setTickLabelPaint(TEXTO_SECUNDARIO);
        plot.getRangeAxis().setTickLabelPaint(TEXTO_SECUNDARIO);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, COR_SECUNDARIA);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        
        return chart;
    }

    // --- MÉTODOS DE LÓGICA (Nenhuma alteração aqui) ---

    private void atualizarCategorias() { comboCategoria.removeAllItems(); if ("Receita".equals(comboTipo.getSelectedItem())) { for (String cat : categoriasReceita) { comboCategoria.addItem(cat); } } else { for (String cat : categoriasDespesa) { comboCategoria.addItem(cat); } } }
    private void adicionarLancamento() {
        String descricao = campoDescricao.getText();
        String categoria = (String) comboCategoria.getSelectedItem();
        String valorTexto = campoValor.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        String data = campoData.getText();

        if (descricao.isEmpty() || valorTexto.isEmpty() || categoria == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try {
            LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
            return;
        }

        try {
            double valor = Double.parseDouble(valorTexto.replace(",", "."));
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser maior que zero.");
                return;
            }

            // Busca ou cria a categoria
            Categoria cat = categoriaDAO.buscarPorNome(categoria);
            if (cat == null) {
                cat = new Categoria(0, categoria);
                categoriaDAO.inserir(cat);
                cat = categoriaDAO.buscarPorNome(categoria);
            }

            if ("Receita".equals(tipo)) {
                Receita receita = new Receita(0, descricao, valor, data, cat.getId());
                receitaDAO.inserir(receita);
            } else {
                Despesa despesa = new Despesa(0, descricao, valor, data, cat.getId());
                despesaDAO.inserir(despesa);
            }

            // Atualiza a interface
            carregarDados();
            campoDescricao.setText("");
            campoValor.setText("");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um valor válido.");
        }
    }
    private void removerLancamento() {
        int linhaSelecionada = tabelaLancamentos.getSelectedRow();
        if (linhaSelecionada != -1) {
            try {
                String tipo = (String) tabelaModelo.getValueAt(linhaSelecionada, 2);
                String descricao = (String) tabelaModelo.getValueAt(linhaSelecionada, 1);
                String data = (String) tabelaModelo.getValueAt(linhaSelecionada, 0);

                if ("Receita".equals(tipo)) {
                    Receita receita = receitaDAO.buscarPorDescricaoEData(descricao, data);
                    if (receita != null) {
                        receitaDAO.excluir(receita.getId());
                    }
                } else {
                    Despesa despesa = despesaDAO.buscarPorDescricaoEData(descricao, data);
                    if (despesa != null) {
                        despesaDAO.excluir(despesa.getId());
                    }
                }

                // Atualiza a interface
                carregarDados();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover do banco de dados: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um lançamento para remover.");
        }
    }
    private void atualizarSaldo() { labelSaldo.setText(String.format("Saldo Atual: R$ %.2f", saldo)); }
    private void atualizarGraficos() { painelGraficoPizza.setChart(criarGraficoPizza()); painelGraficoLinha.setChart(criarGraficoLinha()); }

    private void carregarDados() throws SQLException {
        // Limpa a tabela
        tabelaModelo.setRowCount(0);
        saldo = 0.0;
        categoriasTotais.clear();
        saldoPorData.clear();
        
        // Carrega as despesas
        for (Despesa d : despesaDAO.listar()) {
            Categoria cat = categoriaDAO.buscarPorId(d.getCategoriaId());
            String nomeCategoria = cat != null ? cat.getNome() : "Sem categoria";
            
            tabelaModelo.addRow(new Object[]{
                d.getData(),
                d.getDescricao(),
                "Despesa",
                nomeCategoria,
                String.format("%.2f", d.getValor())
            });
            
            saldo -= d.getValor();
            categoriasTotais.put(nomeCategoria, 
                categoriasTotais.getOrDefault(nomeCategoria, 0.0) + d.getValor());
        }
        
        // Carrega as receitas
        for (Receita r : receitaDAO.listar()) {
            Categoria cat = categoriaDAO.buscarPorId(r.getCategoriaId());
            String nomeCategoria = cat != null ? cat.getNome() : "Sem categoria";
            
            tabelaModelo.addRow(new Object[]{
                r.getData(),
                r.getDescricao(),
                "Receita",
                nomeCategoria,
                String.format("%.2f", r.getValor())
            });
            
            saldo += r.getValor();
            categoriasTotais.put(nomeCategoria, 
                categoriasTotais.getOrDefault(nomeCategoria, 0.0) + r.getValor());
        }
        
        // Atualiza o saldo por data
        double saldoTemp = 0;
        for (int i = 0; i < tabelaModelo.getRowCount(); i++) {
            String d = (String) tabelaModelo.getValueAt(i, 0);
            String t = (String) tabelaModelo.getValueAt(i, 2);
            double v = Double.parseDouble(((String) tabelaModelo.getValueAt(i, 4)).replace(",", "."));
            if ("Receita".equals(t)) saldoTemp += v;
            else saldoTemp -= v;
            saldoPorData.put(d, saldoTemp);
        }
        
        atualizarSaldo();
        atualizarGraficos();
    }

    /**
     * Método principal que inicia a aplicação.
     * A chamada foi atualizada para criar uma instância da nova classe "Interface".
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        try {
            // Configura o look and feel do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Executa a interface na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            new Interface();
        });
    }
}