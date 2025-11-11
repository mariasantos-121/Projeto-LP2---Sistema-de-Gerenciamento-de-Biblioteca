import item.*;
import pessoa.Autor; // <-- ADICIONE ESTA LINHA
import pessoa.Bibliotecario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bibliotecario b = new Bibliotecario("Admin");

        b.carregarDados();

        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Menu de Leitores");
            System.out.println("2 - Menu de Itens");
            System.out.println("3 - Menu de Empréstimos");
            System.out.println("4 - Menu de Devoluções");
            System.out.println("5 - [PERIGO] Resetar Todos os Dados");
            System.out.println("6 - Menu de Autores");// <-- MUDANÇA
            System.out.println("7 - Menu de Categorias"); // <-- MUDANÇA
            System.out.println("0 - Sair");

            opcao = lerOpcao(sc, 0, 7); // <-- MUDANÇA (máximo agora é 7)

            switch (opcao) {
                case 1 -> menuLeitores(b, sc);
                case 2 -> menuItens(b, sc);
                case 3 -> menuEmprestimos(b, sc);
                case 4 -> b.realizarDevolucao();
                case 5 -> b.resetarDados();
                case 6 -> menuAutores(b, sc); // <-- MUDANÇA
                case 7 -> menuCategorias(b, sc);// <-- MUDANÇA
                case 0 -> System.out.println("Encerrando o sistema...");
            }
        } while (opcao != 0);

        b.salvarDados();
        sc.close();
    }

    private static void menuLeitores(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE LEITORES ---");
            System.out.println("1 - Cadastrar Leitor");
            System.out.println("2 - Listar Leitores");
            System.out.println("3 - Editar Leitor");
            System.out.println("4 - Deletar Leitor");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 4); // MUDOU DE 3 PARA 4

            switch (op) {
                case 1 -> b.cadLeitor();
                case 2 -> b.listLeitores();
                case 3 -> b.editarLeitor();
                case 4 -> b.deletarLeitor();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    // --- ATUALIZAR menuItens ---
    private static void menuItens(Bibliotecario b, Scanner sc) {
        int op;
        do {

            System.out.println("\n--- MENU DE ITENS ---");

            System.out.println("1 - Cadastrar Livro");
            System.out.println("2 - Cadastrar Revista");
            System.out.println("3 - Listar Itens");
            System.out.println("4 - Editar Item");
            System.out.println("5 - Deletar Item");
            System.out.println("0 - Voltar");
            op = lerOpcao(sc, 0, 5);

            switch (op) {
                case 1 -> { // CADASTRAR LIVRO (MUDOU BASTANTE)
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();

                    // Pedir Categoria
                    System.out.println("--- Selecione a Categoria ---");
                    b.listarCategorias();
                    int idCat = lerInteiro(sc, "ID da Categoria: ");
                    Categoria cat = b.buscarCategoriaPorId(idCat);
                    if (cat == null) {
                        System.out.println("Categoria não encontrada. Operação cancelada.");
                        break;
                    }

                    // Pedir Autor
                    System.out.println("--- Selecione o Autor ---");
                    b.listarAutores();
                    int idAut = lerInteiro(sc, "ID do Autor: ");
                    Autor autor = b.buscarAutorPorId(idAut);
                    if (autor == null) {
                        System.out.println("Autor não encontrado. Operação cancelada.");
                        break;
                    }

                    int qtd = lerInteiro(sc, "Quantidade: ");
                    // Usar o novo construtor
                    b.addItem(new Livro(titulo, autor, qtd, cat));
                }
                case 2 -> { // CADASTRAR REVISTA (MUDOU)
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();

                    // Pedir Categoria
                    System.out.println("--- Selecione a Categoria ---");
                    b.listarCategorias();
                    int idCat = lerInteiro(sc, "ID da Categoria: ");
                    Categoria cat = b.buscarCategoriaPorId(idCat);
                    if (cat == null) {
                        System.out.println("Categoria não encontrada. Operação cancelada.");
                        break;
                    }

                    System.out.print("Editora: ");
                    String editora = sc.nextLine();
                    int qtd = lerInteiro(sc, "Quantidade: ");

                    // Usar o novo construtor
                    b.addItem(new Revista(titulo, qtd, editora, cat));
                }
                case 3 -> b.listarItens();
                case 4 -> b.editarItem();
                case 5 -> b.deletarItem();
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

    private static void menuAutores(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE AUTORES ---");
            System.out.println("1 - Cadastrar Autor");
            System.out.println("2 - Listar Autores");
            System.out.println("3 - Editar Autor");
            System.out.println("4 - Deletar Autor");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 4);

            switch (op) {
                case 1 -> b.cadastrarAutor();
                case 2 -> b.listarAutores();
                case 3 -> b.editarAutor();
                case 4 -> b.deletarAutor();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static void menuCategorias(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE CATEGORIAS ---");
            System.out.println("1 - Cadastrar Categoria");
            System.out.println("2 - Listar Categorias");
            System.out.println("3 - Editar Categoria");
            System.out.println("4 - Deletar Categoria");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 4);

            switch (op) {
                case 1 -> b.cadastrarCategoria();
                case 2 -> b.listarCategorias();
                case 3 -> b.editarCategoria();
                case 4 -> b.deletarCategoria();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static void menuCadastrosGerais(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- CADASTROS GERAIS ---");
            System.out.println("1 - Cadastrar Autor");
            System.out.println("2 - Listar Autores");
            System.out.println("3 - Editar Autor");        // NOVO
            System.out.println("4 - Deletar Autor");       // NOVO
            System.out.println("5 - Cadastrar Categoria");
            System.out.println("6 - Listar Categorias");
            System.out.println("7 - Editar Categoria");    // NOVO
            System.out.println("8 - Deletar Categoria");   // NOVO
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 8); // ATUALIZAR O MÁXIMO PARA 8

            switch (op) {
                case 1 -> b.cadastrarAutor();
                case 2 -> b.listarAutores();
                case 3 -> b.editarAutor();        // NOVO
                case 4 -> b.deletarAutor();       // NOVO
                case 5 -> b.cadastrarCategoria();
                case 6 -> b.listarCategorias();
                case 7 -> b.editarCategoria();    // NOVO
                case 8 -> b.deletarCategoria();   // NOVO
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
