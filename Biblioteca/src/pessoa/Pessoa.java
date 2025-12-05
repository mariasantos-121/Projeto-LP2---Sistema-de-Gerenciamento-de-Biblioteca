package pessoa;
import java.io.Serializable;
import util.Identificavel;
import util.Exibivel;

public abstract class Pessoa implements Serializable, Identificavel, Exibivel {
    private String nome;
    private final int id;

    @Override
    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    @Override
    public int getId() {return id;}

    public Pessoa(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    @Override
    public abstract void exibirInfo();
}
