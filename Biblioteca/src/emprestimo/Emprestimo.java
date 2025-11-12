package emprestimo;
import java.io.Serializable;
import item.Item;
import pessoa.Leitor;

public class Emprestimo implements Serializable{
    private static int contadorID = 0;
    private final int id;
    private Leitor leitor;
    private Item item;
    private boolean devolvido;

    public Emprestimo(Leitor leitor, Item item) {
        this.id = ++contadorID;
        this.leitor = leitor;
        this.item = item;
        this.devolvido = false;

        item.setQuantidadeExemplares(item.getQuantidadeExemplares() - 1);
    }

    public static int getContadorID() {return contadorID;}

    public static void setContadorID(int c) {
        contadorID = c;
    }

    public int getId() {
        return id;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Item getItem() {
        return item;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void devolver() {
        if (!devolvido) {
            devolvido = true;
            item.setQuantidadeExemplares(item.getQuantidadeExemplares() + 1);
            System.out.println("Item devolvido com sucesso!");
        } else {
            System.out.println("Este item já foi devolvido.");
        }
    }

    public void exibirInfo() {
        System.out.println(
                "Empréstimo ID: " + id +
                        " | Leitor: " + leitor.getNome() +
                        " | Item: " + item.getTitulo() +
                        " | Devolvido: " + (devolvido ? "Sim" : "Não")
        );
    }
}
