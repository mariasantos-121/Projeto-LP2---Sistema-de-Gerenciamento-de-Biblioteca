package pessoa;

public class Pessoa {
    //atributos
    private String nome;
    private String cpf;

    //construtor
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf; //corrigir com a função setCpf
    }

    //métodos
    public String getNome() {
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCpf() {
        return nome;
    }
    public void setCpf(String nome){
        this.nome = nome;
    }
}
