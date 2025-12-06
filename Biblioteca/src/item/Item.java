package item;
import java.io.Serializable;
import util.Identificavel;
import util.Exibivel;

public abstract class Item implements Serializable, Identificavel, Exibivel{

    private static int contadorID = 0;
    private final int id;
    private String titulo;
    private int quantidadeExemplares;
    private boolean disponivel;
    private Categoria categoria;
    private Prateleira prateleira;

    public Item(String titulo, int quantidadeExemplares, Categoria categoria, Prateleira prateleira) {
        this.id = ++contadorID;
        this.titulo = titulo;
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = true;
        this.categoria = categoria;
        this.prateleira = prateleira;
    }

    public static int getContadorID() {return contadorID;}
    public static void setContadorID(int c) {contadorID = c;}

    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    @Override
    public int getId(){return this.id;}

    public String getTitulo(){return titulo;}
    public void setTitulo(String titulo){this.titulo = titulo;}

    @Override
    public String getNome() {
        return this.getTitulo();
    }

    public int getQuantidadeExemplares(){return quantidadeExemplares;}
    public void setQuantidadeExemplares(int quantidadeExemplares){
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = quantidadeExemplares > 0;
    }

    public Prateleira getPrateleira() { return prateleira; }
    public void setPrateleira(Prateleira prateleira) { this.prateleira = prateleira; }

    public boolean isDisponivel(){
        return disponivel;
    }

    @Override
    public abstract void exibirInfo();{

    }

}
