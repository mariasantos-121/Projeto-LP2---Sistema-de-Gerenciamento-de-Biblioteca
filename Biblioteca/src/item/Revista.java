package item;

public class Revista extends Item {
    private String editora;

    public Revista(String titulo, int quantidadeExemplares, String editora){
        super(titulo, quantidadeExemplares);
        this.editora = editora;
    }

    public String getEditora() {
        return editora;
    }
    public void setEditora(String editora) {
        this.editora = editora;
    }

    @Override
    public void exibirInfo() {
        System.out.println(
                "Revista: " + getTitulo() +
                        " | Editora: " + editora +
                        " | Quantidade: " + getQuantidadeExemplares() +
                        " | Disponível: " + (isDisponivel() ? "Sim" : "Não") +
                        " | ID: " + getId()
        );
    }
}
