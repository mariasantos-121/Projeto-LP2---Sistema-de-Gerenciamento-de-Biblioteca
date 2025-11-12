import item.*;
import pessoa.Autor;
import pessoa.Bibliotecario;
import pessoa.Leitor;
import emprestimo.Emprestimo;
import java.util.ArrayList;
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
                case 6 -> {
                    System.out.print("TEM CERTEZA? Isso apagará TUDO. (s/n): ");
                    if (sc.nextLine().equalsIgnoreCase("s")) {
                        b.resetarDados();
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                }
                case 0 -> System.out.println("Encerrando o sistema...");
            }
        } while (opcao != 0);
        sc.close();
    }

    // --- 1. MENUS DE ENTIDADES ---

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
                case 1 -> cadastrarNovoLeitor(b, sc);
                case 2 -> b.listLeitores();
                case 3 -> editarLeitor(b, sc);
                case 4 -> deletarLeitor(b, sc);
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
                case 4 -> editarItem(b, sc);
                case 5 -> deletarItem(b, sc);
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
                case 1 -> cadastrarNovoAutor(b, sc);
                case 2 -> b.listarAutores();
                case 3 -> editarAutor(b, sc);
                case 4 -> deletarAutor(b, sc);
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
                case 1 -> cadastrarNovaCategoria(b, sc);
                case 2 -> b.listarCategorias();
                case 3 -> editarCategoria(b, sc);
                case 4 -> deletarCategoria(b, sc);
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
            System.out.println("3 - Realizar Devolução");
            System.out.println("0 - Voltar");
            op = lerOpcao(sc, 0, 3);
            switch (op) {
                case 1 -> realizarNovoEmprestimo(b, sc);
                case 2 -> b.listarEmprestimos();
                case 3 -> realizarNovaDevolucao(b, sc);
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    // --- 2. LÓGICAS DE "CADASTRAR" (UI) ---

    private static void cadastrarNovoLeitor(Bibliotecario b, Scanner sc) {
        System.out.print("Digite o nome do Leitor (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (nome.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        String cpf;
        while (true) {
            System.out.print("Digite o CPF (ou 'c' para cancelar): ");
            cpf = sc.nextLine();
            if (cpf.equalsIgnoreCase("c")) {
                System.out.println("Cadastro cancelado.");
                return;
            }
            if (b.validarCPF(cpf)) {
                break;
            } else {
                System.out.println("CPF inválido! Tente novamente.");
            }
        }
        b.cadLeitor(nome, cpf);
    }

    private static void cadastrarNovoAutor(Bibliotecario b, Scanner sc) {
        System.out.print("Nome do Autor (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (nome.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }
        b.cadastrarAutor(nome);
    }

    private static void cadastrarNovaCategoria(Bibliotecario b, Scanner sc) {
        System.out.print("Nome da Categoria (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (nome.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }
        b.cadastrarCategoria(nome);
    }

    private static void cadastrarNovoLivro(Bibliotecario b, Scanner sc) {
        System.out.print("Título (ou 'c' para cancelar): ");
        String titulo = sc.nextLine();
        if (titulo.equalsIgnoreCase("c")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Categoria cat = selecionarOuCriarCategoria(b, sc);
        if (cat == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        Autor autor = selecionarOuCriarAutor(b, sc);
        if (autor == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

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

        Categoria cat = selecionarOuCriarCategoria(b, sc);
        if (cat == null) {
            System.out.println("Cadastro cancelado.");
            return;
        }

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

    // --- 3. LÓGICAS DE "EDITAR" (UI) ---

    private static void editarLeitor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Leitores ---");
        b.listLeitores();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do leitor a editar (ou 'c' para cancelar): ");
        if (id == null) return;

        Leitor leitor = b.buscaID(id);
        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return;
        }

        System.out.println("Editando Leitor: " + leitor.getNome());
        System.out.println("1 - Editar Nome");
        System.out.println("2 - Editar CPF");
        System.out.println("0 - Voltar");
        int op = lerOpcao(sc, 0, 2);

        switch(op) {
            case 1 -> {
                System.out.print("Digite o novo nome: ");
                String novoNome = sc.nextLine();
                b.editarLeitorNome(leitor, novoNome);
            }
            case 2 -> {
                System.out.print("Digite o novo CPF: ");
                String novoCpf = sc.nextLine();
                b.editarLeitorCPF(leitor, novoCpf);
            }
            case 0 -> System.out.println("Operação cancelada.");
        }
    }

    private static void editarAutor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Autores ---");
        b.listarAutores();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do autor a editar (ou 'c' para cancelar): ");
        if (id == null) return;

        Autor autor = b.buscarAutorPorId(id);
        if (autor == null) {
            System.out.println("Autor não encontrado.");
            return;
        }

        System.out.print("Digite o novo nome para '" + autor.getNome() + "': ");
        String novoNome = sc.nextLine();
        b.editarAutor(autor, novoNome);
    }

    private static void editarCategoria(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Categorias ---");
        b.listarCategorias();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID da categoria a editar (ou 'c' para cancelar): ");
        if (id == null) return;

        Categoria cat = b.buscarCategoriaPorId(id);
        if (cat == null) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        System.out.print("Digite o novo nome para '" + cat.getNome() + "': ");
        String novoNome = sc.nextLine();
        b.editarCategoria(cat, novoNome);
    }

    private static void editarItem(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Itens ---");
        b.listarItens();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do item a editar (ou 'c' para cancelar): ");
        if (id == null) return;

        Item item = b.buscarItemPorId(id);
        if (item == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        System.out.println("--- Editando Item ---");
        item.exibirInfo();
        System.out.println("\nO que deseja editar?");
        System.out.println("1 - Título");
        System.out.println("2 - Quantidade de Exemplares");
        System.out.println("3 - Categoria");
        if (item instanceof Livro) System.out.println("4 - Autor");
        if (item instanceof Revista) System.out.println("4 - Editora");
        System.out.println("0 - Cancelar");

        int op = lerOpcao(sc, 0, 4);

        switch(op) {
            case 0 -> { System.out.println("Edição cancelada."); return; }
            case 1 -> {
                System.out.print("Digite o novo título: ");
                b.editarItem(item, 1, sc.nextLine(), null);
            }
            case 2 -> {
                Integer qtd = lerInteiroCancelavel(sc, "Digite a nova quantidade: ");
                if (qtd != null) b.editarItem(item, 2, qtd.toString(), null);
            }
            case 3 -> {
                Categoria cat = selecionarOuCriarCategoria(b, sc);
                if (cat != null) b.editarItem(item, 3, null, cat);
            }
            case 4 -> {
                if (item instanceof Livro) {
                    Autor autor = selecionarOuCriarAutor(b, sc);
                    if (autor != null) b.editarItem(item, 4, null, autor);
                } else if (item instanceof Revista) {
                    System.out.print("Digite a nova editora: ");
                    b.editarItem(item, 4, sc.nextLine(), null);
                }
            }
        }
    }

    // --- 4. LÓGICAS DE "DELETAR" (UI) ---

    private static void deletarLeitor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Leitores ---");
        b.listLeitores();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do leitor a deletar (ou 'c' para cancelar): ");
        if (id == null) return;

        Leitor leitor = b.buscaID(id);
        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return;
        }

        System.out.print("Tem certeza que quer deletar " + leitor.getNome() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            b.deletarLeitor(leitor);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void deletarAutor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Autores ---");
        b.listarAutores();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do autor a deletar (ou 'c' para cancelar): ");
        if (id == null) return;

        Autor autor = b.buscarAutorPorId(id);
        if (autor == null) {
            System.out.println("Autor não encontrado.");
            return;
        }

        System.out.print("Tem certeza que quer deletar " + autor.getNome() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            b.deletarAutor(autor);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void deletarCategoria(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Categorias ---");
        b.listarCategorias();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID da categoria a deletar (ou 'c' para cancelar): ");
        if (id == null) return;

        Categoria cat = b.buscarCategoriaPorId(id);
        if (cat == null) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        System.out.print("Tem certeza que quer deletar " + cat.getNome() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            b.deletarCategoria(cat);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void deletarItem(Bibliotecario b, Scanner sc) {
        System.out.println("--- Lista de Itens ---");
        b.listarItens();
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do item a deletar (ou 'c' para cancelar): ");
        if (id == null) return;

        Item item = b.buscarItemPorId(id);
        if (item == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        System.out.print("Tem certeza que quer deletar " + item.getTitulo() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            b.deletarItem(item);
        } else {
            System.out.println("Operação cancelada.");
        }
    }


    // --- 5. LÓGICAS DE FLUXO (UI) ---

    private static Leitor autenticarLeitor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Autenticação ---");
        Integer id = lerInteiroCancelavel(sc, "Digite o ID do Leitor (ou 'c' para cancelar): ");
        if (id == null) return null;

        Leitor leitor = b.buscaID(id);
        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return null;
        }

        while(true) {
            System.out.print("Digite o CPF de " + leitor.getNome() + " (ou 'c' para cancelar): ");
            String cpf = sc.nextLine();
            if (cpf.equalsIgnoreCase("c")) return null;

            if (cpf.equals(leitor.getCpf())) {
                System.out.println("Olá, " + leitor.getNome() + "!");
                return leitor;
            } else {
                System.out.println("CPF não corresponde. Tente novamente.");
            }
        }
    }

    private static Categoria selecionarOuCriarCategoria(Bibliotecario b, Scanner sc) {
        Categoria cat = null;
        while (cat == null) {
            System.out.println("\n--- Selecione a Categoria ---");
            b.listarCategorias();
            System.out.println("Digite o ID, '0' para CADASTRAR NOVA, ou 'c' para CANCELAR.");
            Integer idCat = lerInteiroCancelavel(sc, "Escolha: ");
            if (idCat == null) return null;
            if (idCat == 0) {
                cadastrarNovaCategoria(b, sc);
            } else {
                cat = b.buscarCategoriaPorId(idCat);
                if (cat == null) System.out.println("ID inválido.");
            }
        }
        System.out.println("Categoria selecionada: " + cat.getNome());
        return cat;
    }

    private static Autor selecionarOuCriarAutor(Bibliotecario b, Scanner sc) {
        Autor autor = null;
        while (autor == null) {
            System.out.println("\n--- Selecione o Autor ---");
            b.listarAutores();
            System.out.println("Digite o ID, '0' para CADASTRAR NOVO, ou 'c' para CANCELAR.");
            Integer idAut = lerInteiroCancelavel(sc, "Escolha: ");
            if (idAut == null) return null;
            if (idAut == 0) {
                cadastrarNovoAutor(b, sc);
            } else {
                autor = b.buscarAutorPorId(idAut);
                if (autor == null) System.out.println("ID inválido.");
            }
        }
        System.out.println("Autor selecionado: " + autor.getNome());
        return autor;
    }

    private static void realizarNovoEmprestimo(Bibliotecario b, Scanner sc) {
        Leitor leitor = autenticarLeitor(b, sc);
        if (leitor == null) {
            System.out.println("Autenticação falhou. Operação cancelada.");
            return;
        }

        ArrayList<Emprestimo> ativos = b.getEmprestimosAtivos(leitor);
        if (ativos.size() >= 3) {
            System.out.println("O leitor \"" + leitor.getNome() + "\" já possui 3 empréstimos ativos.");
            return;
        }

        System.out.println("\n--- Selecione o Item ---");
        b.listarItens();
        Integer idItem = lerInteiroCancelavel(sc, "Digite o ID do item a emprestar (ou 'c' para cancelar): ");
        if (idItem == null) {
            System.out.println("Operação cancelada.");
            return;
        }

        Item item = b.buscarItemPorId(idItem);
        if (item == null || !item.isDisponivel()) {
            System.out.println("Item inválido ou indisponível.");
            return;
        }

        b.realizarEmprestimo(leitor, item);
    }

    private static void realizarNovaDevolucao(Bibliotecario b, Scanner sc) {
        Leitor leitor = autenticarLeitor(b, sc);
        if (leitor == null) {
            System.out.println("Autenticação falhou. Operação cancelada.");
            return;
        }

        ArrayList<Emprestimo> ativos = b.getEmprestimosAtivos(leitor);
        if (ativos.isEmpty()) {
            System.out.println("O leitor " + leitor.getNome() + " não possui empréstimos ativos.");
            return;
        }

        System.out.println("\n--- Empréstimos Ativos de " + leitor.getNome() + " ---");
        for (Emprestimo emp : ativos) {
            emp.exibirInfo();
        }

        Integer idEmp = lerInteiroCancelavel(sc, "Digite o ID do empréstimo a devolver (ou 'c' para cancelar): ");
        if (idEmp == null) {
            System.out.println("Operação cancelada.");
            return;
        }

        Emprestimo emp = b.getEmprestimoAtivoPorId(idEmp, leitor);
        if (emp == null) {
            System.out.println("ID do empréstimo não encontrado ou não pertence a este leitor.");
            return;
        }

        b.realizarDevolucao(emp);
    }


    // --- 6. MÉTODOS UTILITÁRIOS DA UI ---

    private static int lerOpcao(Scanner sc, int min, int max) {
        int opcao;
        while (true) {
            System.out.print("Escolha: ");
            try {
                opcao = Integer.parseInt(sc.nextLine());
                if (opcao >= min && opcao <= max) return opcao;
                System.out.println("Opção inválida! Digite um número entre " + min + " e " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
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