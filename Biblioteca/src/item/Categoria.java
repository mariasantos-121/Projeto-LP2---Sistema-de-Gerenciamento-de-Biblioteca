package item;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static int contadorID = 0;
    private final int id;
    private String nome;

    public Categoria(String nome) {
        this.id = ++contadorID;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static int getContadorID() {
        return contadorID;
    }

    public static void setContadorID(int c) {
        contadorID = c;
    }
}