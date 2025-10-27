package item;

public class Livro extends Item {
    private String autor;

    public Livro(String titulo, String autor, int quantidadeExemplares) {
        super(titulo, quantidadeExemplares);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public void exibirInfo() {
        System.out.println(
                "Livro: " + getTitulo() +
                        " | Autor: " + autor +
                        " | Quantidade: " + getQuantidadeExemplares() +
                        " | Disponível: " + (isDisponivel() ? "Sim" : "Não") +
                        " | ID: " + getId()
        );
    }
}
