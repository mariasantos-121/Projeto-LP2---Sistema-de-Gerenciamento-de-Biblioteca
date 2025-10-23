package pessoa;

public abstract class Pessoa {
    private int id;
    private String nome;

    public Pessoa(){} // construtor padrão

    public Pessoa(int id, String nome){ // construtor
        this.id = id;
        this.nome = nome;
    }

    // métodos getters
    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }

    // métodos setters
    public void setId(int id){
        if(id <= 0){
            throw new IllegalArgumentException("O ID deve ser positivo.");
            // THROW -> palavra-chave que "lança" uma exceção
            // new IllegalArgumentException(...) -> cria uma nova exceção desse tipo
        }
        this.id = id;
    }
    public void setNome(String nome){
        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("O nome deve ser preenchido.");
        }
        this.nome = nome;
    }

    @Override
    public String toString(){
        return id + " - " + nome;
    }
}

