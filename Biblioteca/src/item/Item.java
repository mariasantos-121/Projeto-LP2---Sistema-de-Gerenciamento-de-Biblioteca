package item;

public abstract class Item{
    private static int contadorID = 0;
    private final int id;
    private String titulo;
    private int quantidadeExemplares;
    private boolean disponivel;

    public Item(String titulo, int quantidadeExemplares) {
        this.id = contadorID++;
        this.titulo = titulo;
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = true;
    }

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