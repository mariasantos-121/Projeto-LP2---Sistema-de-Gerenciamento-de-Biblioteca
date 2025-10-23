package pessoa;
import java.util.ArrayList;
import java.util.List;

public class Leitor extends Pessoa {
    // array para armazenar livros emprestados
    private List<Livro> livrosEmprestados;

    //construtor padrão
    public Leitor() {
        super();
        this.livrosEmprestados = new ArrayList<>();// chama o construtor da classe pessoa.Pessoa
    }

    /



}
