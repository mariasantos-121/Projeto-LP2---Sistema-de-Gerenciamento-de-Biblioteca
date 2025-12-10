package item;
import pessoa.Autor;

public class Livro extends Item {
    private Autor autor;
    private Editora editora;

    public Livro(String titulo, Autor autor, int qtd, Categoria cat, Prateleira prat, Editora editora) {
        super(titulo, qtd, cat, prat);
        this.autor = autor;
        this.editora = editora;
    }

    public Autor getAutor() {return autor;}

    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }

    public void setAutor(Autor autor) {this.autor = autor;}

    @Override
    public void exibirInfo() {
        System.out.println(
                "ID: " + getId() + " | Livro: " + getTitulo() +
                        " | Autor: " + autor.getNome() +
                        " | Editora: " + editora.getNome() +
                        " | Categoria: " + getCategoria().getNome() +
                        " | Local: " + getPrateleira().getNome() +
                        " | Qtd: " + getQuantidadeExemplares() +
                        " | Disp: " + (isDisponivel() ? "Sim" : "Não")
        );
    }
}
