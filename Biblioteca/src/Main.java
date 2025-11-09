import item.*;
import pessoa.Bibliotecario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bibliotecario b = new Bibliotecario("Admin");

        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Menu de Leitores");
            System.out.println("2 - Menu de Itens");
            System.out.println("3 - Menu de Empréstimos");
            System.out.println("4 - Menu de Devoluções");
            System.out.println("0 - Sair");

            opcao = lerOpcao(sc, 0, 4);

            switch (opcao) {
                case 1 -> menuLeitores(b, sc);
                case 2 -> menuItens(b, sc);
                case 3 -> menuEmprestimos(b, sc);
                case 4 -> b.realizarDevolucao();
                case 0 -> System.out.println("Encerrando o sistema...");
            }
        } while (opcao != 0);
    }

    private static void menuLeitores(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE LEITORES ---");
            System.out.println("1 - Cadastrar Leitor");
            System.out.println("2 - Listar Leitores");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 2);

            switch (op) {
                case 1 -> b.cadLeitor();
                case 2 -> b.listLeitores();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static void menuItens(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE ITENS ---");
            System.out.println("1 - Cadastrar Livro");
            System.out.println("2 - Cadastrar Revista");
            System.out.println("3 - Listar Itens");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 3);

            switch (op) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Autor: ");
                    String autor = sc.nextLine();
                    int qtd = lerInteiro(sc, "Quantidade: ");
                    b.addItem(new Livro(titulo, autor, qtd));
                }
                case 2 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Editora: ");
                    String editora = sc.nextLine();
                    int qtd = lerInteiro(sc, "Quantidade: ");
                    b.addItem(new Revista(titulo, qtd, editora));
                }
                case 3 -> b.listarItens();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static void menuEmprestimos(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE EMPRÉSTIMOS ---");
            System.out.println("1 - Realizar Empréstimo");
            System.out.println("2 - Listar Empréstimos");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 2);

            switch (op) {
                case 1 -> b.realizarEmprestimo();
                case 2 -> b.listarEmprestimos();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static int lerOpcao(Scanner sc, int min, int max) {
        int opcao;
        while (true) {
            System.out.print("Escolha: ");
            try {
                opcao = sc.nextInt();
                sc.nextLine();
                if (opcao >= min && opcao <= max) return opcao;
                System.out.println("Opção inválida! Digite um número entre " + min + " e " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                sc.nextLine();
            }
        }
    }

    private static int lerInteiro(Scanner sc, String mensagem) {
        int numero;
        while (true) {
            System.out.print(mensagem);
            try {
                numero = sc.nextInt();
                sc.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                sc.nextLine();
            }
        }
    }
}
