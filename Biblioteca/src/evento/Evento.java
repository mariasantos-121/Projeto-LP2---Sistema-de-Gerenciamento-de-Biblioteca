package evento;

import java.io.Serializable;

public class Evento implements Serializable {
    private static int contadorID = 0;
    private final int id;
    private String nome;
    private String data; // Usar String é o mais simples para "Toda Terça às 19h"
    private String local;

    public Evento(String nome, String data, String local) {
        this.id = ++contadorID; // Começa em 1
        this.nome = nome;
        this.data = data;
        this.local = local;
    }

    public int getId() {return id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getData() {return data;}

    public void setData(String data) {this.data = data;}

    public String getLocal() {return local;}

    public void setLocal(String local) {this.local = local;}

    public static int getContadorID() {return contadorID;}

    public static void setContadorID(int c) {contadorID = c;}

    public void exibirInfo() {
        System.out.println("ID: " + id + " | Evento: " + nome +
                " | Data: " + data + " | Local: " + local);
    }
}