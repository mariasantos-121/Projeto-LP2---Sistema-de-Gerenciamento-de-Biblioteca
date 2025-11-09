package dados;

import item.*;
import pessoa.Leitor;

import java.util.ArrayList;

public class BaseDeDados {

    public static ArrayList<Item> carregarItensIniciais() {
        ArrayList<Item> itens = new ArrayList<>();

        itens.add(new Livro("Dom Casmurro", "Machado de Assis", 3));
        itens.add(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 5));
        itens.add(new Livro("1984", "George Orwell", 4));
        itens.add(new Revista("Superinteressante", 10, "Abril"));
        itens.add(new Revista("National Geographic", 6, "NG Media"));

        return itens;
    }

    public static ArrayList<Leitor> carregarLeitoresIniciais() {
        ArrayList<Leitor> leitores = new ArrayList<>();

        leitores.add(new Leitor("Maria Silva", "12345678901"));
        leitores.add(new Leitor("João Pereira", "98765432100"));
        leitores.add(new Leitor("Ana Souza", "45612378909"));

        return leitores;
    }
}
