package pessoa;

public class Pessoa {
    //atributos
    private String nome;
    private static int contadorID = 0; // contador que atribui os IDs automaticamente
    private final int id; //valor não pode ser alterado depois do construtor

    //construtor
    public Pessoa(String nome) {
        this.nome = nome;
        this.id = contadorID++; // atribui o ID e incrementa a var contadorID
    }

    //métodos
    public String getNome() {
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    //não vai ter setId() por que id é final
}
