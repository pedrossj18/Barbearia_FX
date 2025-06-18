package project_model;

public class Barbeiro {
    private int id;
    private String nome;
    private String especialidade;
    private String telefone;

    // Construtor vazio
    public Barbeiro() {}
    
    public Barbeiro(int id) {
        this.id = id;
    }

    // Construtor com campos principais
    public Barbeiro(String nome, String especialidade, String telefone) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // ✅ Adicionar estes dois métodos que estão faltando:
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
