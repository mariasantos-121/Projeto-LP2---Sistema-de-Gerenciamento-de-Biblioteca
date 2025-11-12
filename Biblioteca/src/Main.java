import item.*;
import pessoa.Autor;
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
            System.out.println("4 - Menu de Autores");
            System.out.println("5 - Menu de Categorias");
            System.out.println("6 - [PERIGO] Resetar Todos os Dados");
            System.out.println("0 - Sair");

            opcao = lerOpcao(sc, 0, 6);

            switch (opcao) {
                case 1 -> menuLeitores(b, sc);
                case 2 -> menuItens(b, sc);
                case 3 -> menuEmprestimos(b, sc);
                case 4 -> menuAutores(b, sc);
                case 5 -> menuCategorias(b, sc);
                case 6 -> b.resetarDados();
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

            op = lerOpcao(sc, 0, 4);

            switch (op) {
                case 1 -> b.cadLeitor();
                case 2 -> b.listLeitores();
                case 3 -> b.editarLeitor();
                case 4 -> b.deletarLeitor();
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
            System.out.println("4 - Editar Item");
            System.out.println("5 - Deletar Item");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 5);

            switch (op) {
                case 1 -> cadastrarNovoLivro(b, sc);
                case 2 -> cadastrarNovaRevista(b, sc);
                case 3 -> b.listarItens();
                case 4 -> b.editarItem();
                case 5 -> b.deletarItem();
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    private static void cadastrarNovoLivro(Bibliotecario b, Scanner sc) {
        System.out.print("Título (ou 'c' para cancelar): ");
        String titulo = sc.nextLine();
        if (titulo.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Categoria cat = null;
        while (cat == null) {
            System.out.println("\n--- Selecione a Categoria ---");
            b.listarCategorias();
            System.out.println("Digite o ID, '0' para CADASTRAR NOVA, ou 'c' para CANCELAR.");

            Integer idCat = lerInteiroCancelavel(sc, "Escolha: ");

            if (idCat == null) {
                System.out.println("Cadastro cancelado.");
                return;
            } else if (idCat == 0) {
                b.cadastrarCategoria();
            } else {
                cat = b.buscarCategoriaPorId(idCat);
                if (cat == null) {
                    System.out.println("ID inválido. Tente novamente.");
                }
            }
        }
        System.out.println("Categoria selecionada: " + cat.getNome());

        Autor autor = null;
        while (autor == null) {
            System.out.println("\n--- Selecione o Autor ---");
            b.listarAutores();
            System.out.println("Digite o ID, '0' para CADASTRAR NOVO, ou 'c' para CANCELAR.");

            Integer idAut = lerInteiroCancelavel(sc, "Escolha: ");

            if (idAut == null) {
                System.out.println("Cadastro cancelado.");
                return;
            } else if (idAut == 0) {
                b.cadastrarAutor();
            } else {
                autor = b.buscarAutorPorId(idAut);
                if (autor == null) {
                    System.out.println("ID inválido. Tente novamente.");
                }
            }
        }
        System.out.println("Autor selecionado: " + autor.getNome());


        Integer qtd = lerInteiroCancelavel(sc, "Quantidade (ou 'c' para cancelar): ");
        if (qtd == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        b.addItem(new Livro(titulo, autor, qtd, cat));
    }

    private static void cadastrarNovaRevista(Bibliotecario b, Scanner sc) {
        System.out.print("Título (ou 'c' para cancelar): ");
        String titulo = sc.nextLine();
        if (titulo.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Categoria cat = null;
        while (cat == null) {
            System.out.println("\n--- Selecione a Categoria ---");
            b.listarCategorias();
            System.out.println("Digite o ID, '0' para CADASTRAR NOVA, ou 'c' para CANCELAR.");

            Integer idCat = lerInteiroCancelavel(sc, "Escolha: ");

            if (idCat == null) { // Usuário digitou 'c'
                System.out.println("Cadastro cancelado.");
                return;
            } else if (idCat == 0) { // Usuário digitou '0'
                b.cadastrarCategoria();
            } else {
                cat = b.buscarCategoriaPorId(idCat);
                if (cat == null) {
                    System.out.println("ID inválido. Tente novamente.");
                }
            }
        }
        System.out.println("Categoria selecionada: " + cat.getNome());


        System.out.print("Editora (ou 'c' para cancelar): ");
        String editora = sc.nextLine();
        if (editora.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Integer qtd = lerInteiroCancelavel(sc, "Quantidade (ou 'c' para cancelar): ");
        if (qtd == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        b.addItem(new Revista(titulo, qtd, editora, cat));
    }

    private static void menuEmprestimos(Bibliotecario b, Scanner sc) {
        int op;
        do {
            System.out.println("\n--- MENU DE EMPRÉSTIMOS ---");
            System.out.println("1 - Realizar Empréstimo");
            System.out.println("2 - Listar Empréstimos");
            System.out.println("3 - Realizar Devolução");
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 3);

            switch (op) {
                case 1 -> b.realizarEmprestimo();
                case 2 -> b.listarEmprestimos();
                case 3 -> b.realizarDevolucao();
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

    private static Integer lerInteiroCancelavel(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("c")) {
                return null;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número ou 'c' para cancelar.");
            }
        }
    }
}