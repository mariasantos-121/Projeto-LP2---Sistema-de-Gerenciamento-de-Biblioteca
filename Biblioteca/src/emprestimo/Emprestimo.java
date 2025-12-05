package emprestimo;
import java.io.Serializable;
import item.Item;
import pessoa.Leitor;
import util.Identificavel;
import util.Exibivel;

public class Emprestimo implements Serializable, Identificavel, Exibivel {
    private static int contadorID = 0;
    private final int id;
    private Leitor leitor;
    private Item item;
    private boolean devolvido;
    private String dataPrevista;

    public Emprestimo(Leitor leitor, Item item, String dataPrevista) {
        this.id = ++contadorID;
        this.leitor = leitor;
        this.item = item;
        this.devolvido = false;
        this.dataPrevista = dataPrevista;

        item.setQuantidadeExemplares(item.getQuantidadeExemplares() - 1);
    }

    public static int getContadorID() {return contadorID;}

    public static void setContadorID(int c) {
        contadorID = c;
    }

    @Override
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

    public String getDataPrevista() {return dataPrevista;}

    public void setDataPrevista(String dataPrevista) {this.dataPrevista = dataPrevista;}

    @Override
    public String getNome() {
        return "Empréstimo #" + id + " (" + item.getTitulo() + ")";
    }

    @Override
    public void exibirInfo() {
        System.out.println(
                "Empréstimo ID: " + id +
                        " | Leitor: " + leitor.getNome() +
                        " | Item: " + item.getTitulo() +
                        " | Devolvido: " + (devolvido ? "Sim" : "Não")
        );
    }
}
