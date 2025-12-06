package pessoa;

import java.io.Serializable;
import util.Identificavel; // Importar
import util.Exibivel;      // Importar

public class Autor implements Serializable, Identificavel, Exibivel {

    private static int contadorID = 0;
    private final int id;
    private String nome;

    public Autor(String nome) {
        this.id = ++contadorID; // Começa de 1
        this.nome = nome;
    }

    // --- Implementação de Identificavel ---
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }
    // --------------------------------------

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static int getContadorID() {
        return contadorID;
    }

    public static void setContadorID(int c) {
        contadorID = c;
    }

    // --- Implementação de Exibivel (O que você pediu) ---
    @Override
    public void exibirInfo() {
        System.out.println("ID: " + id + " | Autor: " + nome);
    }
}