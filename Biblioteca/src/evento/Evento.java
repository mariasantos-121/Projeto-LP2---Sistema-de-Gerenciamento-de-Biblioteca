package evento;

import java.io.Serializable;
import util.Identificavel;
import util.Exibivel;

public class Evento implements Serializable, Identificavel, Exibivel {
    private static int contadorID = 0;
    private final int id;
    private String nome;
    private String data;
    private String local;

    public Evento(String nome, String data, String local) {
        this.id = ++contadorID;
        this.nome = nome;
        this.data = data;
        this.local = local;
    }

    @Override
    public int getId() {return id;}

    @Override
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getData() {return data;}
    public void setData(String data) {this.data = data;}

    public String getLocal() {return local;}
    public void setLocal(String local) {this.local = local;}

    public static int getContadorID() {return contadorID;}
    public static void setContadorID(int c) {contadorID = c;}

    @Override
    public void exibirInfo() {
        System.out.println("ID: " + getId() + " | Evento: " + getNome() +
                " | Data: " + getData() + " | Local: " + getLocal());
    }

}