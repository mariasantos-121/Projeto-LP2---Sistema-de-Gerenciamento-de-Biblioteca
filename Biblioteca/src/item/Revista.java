package item;

public class Revista extends Item {
    private Editora editora;

    public Revista(String titulo, int qtd, Editora editora, Categoria cat, Prateleira prat) {
        super(titulo, qtd, cat, prat);
        this.editora = editora;
    }

    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }

    @Override
    public void exibirInfo() {
        System.out.println(
                "ID: " + getId() + " | Revista: " + getTitulo() +
                        " | Editora: " + editora.getNome() +
                        " | Categoria: " + getCategoria().getNome() +
                        " | Local: " + getPrateleira().getNome() +
                        " | Qtd: " + getQuantidadeExemplares() +
                        " | Disp: " + (isDisponivel() ? "Sim" : "Não")
        );
    }
}
