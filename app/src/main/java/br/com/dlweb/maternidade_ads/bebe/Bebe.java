package br.com.dlweb.maternidade_ads.bebe;

public class Bebe implements java.io.Serializable {

    private int id;
    private int id_mae;
    private int id_medico;
    private String nome;
    private String data_nascimento;
    private float peso;
    private int altura;

    public Bebe() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_mae() {
        return id_mae;
    }

    public void setId_mae(int id_mae) {
        this.id_mae = id_mae;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
