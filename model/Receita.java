package model;

public class Receita {
    private int id;
    private String descricao;
    private double valor;
    private String data;
    private int categoriaId;

    public Receita() {
        // Construtor vazio exigido pelo JavaBeans e frameworks
    }

    public Receita(int id, String descricao, double valor, String data, int categoriaId) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoriaId = categoriaId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}
