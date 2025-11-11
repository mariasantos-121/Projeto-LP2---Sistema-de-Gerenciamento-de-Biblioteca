package item;
import pessoa.Autor;

public class Livro extends Item {
    private Autor autor;

    public Livro(String titulo, Autor autor, int quantidadeExemplares, Categoria categoria) {
        super(titulo, quantidadeExemplares, categoria); // Passa categoria para o pai
        this.autor = autor;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public void exibirInfo() {
        System.out.println(
                "ID: " + getId() + " | " +
                        "Livro: " + getTitulo() +
                        " | Autor: " + autor.getNome() +
                        " | Categoria: " + getCategoria().getNome() + // NOVO
                        " | Quantidade: " + getQuantidadeExemplares() +
                        " | Disponível: " + (isDisponivel() ? "Sim" : "Não")
        );
    }
}
