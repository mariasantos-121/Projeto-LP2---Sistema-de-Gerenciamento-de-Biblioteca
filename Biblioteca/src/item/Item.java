package item;
import java.io.Serializable;

public abstract class Item implements Serializable{

    private static int contadorID = 0;
    private final int id;
    private String titulo;
    private int quantidadeExemplares;
    private boolean disponivel;
    private Categoria categoria;

    public Item(String titulo, int quantidadeExemplares, Categoria categoria) {
        this.id = ++contadorID;
        this.titulo = titulo;
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = true;
        this.categoria = categoria;
    }

    public static int getContadorID() {return contadorID;}

    public static void setContadorID(int c) {contadorID = c;}

    public Categoria getCategoria() {return categoria;}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    public int getId(){
        return this.id;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public int getQuantidadeExemplares(){
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(int quantidadeExemplares){
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = quantidadeExemplares > 0;
    }

    public boolean isDisponivel(){
        return disponivel;
    }

    public abstract void exibirInfo();

}