package item;

import java.io.Serializable;
import util.Identificavel;
import util.Exibivel;

public class Prateleira implements Serializable, Identificavel, Exibivel {

    private static int contadorID = 0;
    private final int id;
    private String localizacao; // Ex: "Corredor A", "Estante 3"

    public Prateleira(String localizacao) {
        this.id = ++contadorID;
        this.localizacao = localizacao;
    }

    // --- Interfaces ---
    @Override
    public int getId() { return id; }

    @Override
    public String getNome() { return localizacao; } // O nome é a localização

    @Override
    public void exibirInfo() {
        System.out.println("ID: " + id + " | Local: " + localizacao);
    }
    // ------------------

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public static int getContadorID() { return contadorID; }
    public static void setContadorID(int c) { contadorID = c; }
}