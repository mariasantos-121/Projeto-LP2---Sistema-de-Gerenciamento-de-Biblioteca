import item.*;
import pessoa.*;
import emprestimo.Emprestimo;
import evento.Evento;
import util.Configuracao;
import java.util.Scanner;
import java.util.ArrayList;
import static util.ConsoleUI.*;

public class Main {

    // --- PONTO DE ENTRADA E MENU PRINCIPAL ---

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bibliotecario b = new Bibliotecario("Admin");

        b.carregarDados();

        int opcao;
        do {
            exibirCabecalho(b.getConfig());
            exibirMenuPrincipal();
            opcao = lerOpcao(sc, 0, 10);

            switch (opcao) {
                // --- MENUS GENÉRICOS (Padronizados) ---
                case 1 -> menuEntidade(b, sc, "LEITORES");
                case 4 -> menuEntidade(b, sc, "AUTORES");
                case 5 -> menuEntidade(b, sc, "CATEGORIAS");
                case 6 -> menuEntidade(b, sc, "EVENTOS");
                case 7 -> menuEntidade(b, sc, "EDITORAS");
                case 8 -> menuEntidade(b, sc, "PRATELEIRAS");

                // --- MENUS ESPECÍFICOS (Lógica Complexa) ---
                case 2 -> menuItens(b, sc);
                case 3 -> menuEmprestimos(b, sc);

                // --- SISTEMA ---
                case 9 -> menuConfiguracao(b, sc);
                case 10 -> resetarSistema(b, sc);
                case 0 -> System.out.println("Encerrando o sistema...");
            }
        } while (opcao != 0);

        b.salvarDados();
        sc.close();
    }

    // ==================================================================================
    // 2. SISTEMA DE MENUS GENÉRICO (A "Faxina")
    // ==================================================================================

    private static void menuEntidade(Bibliotecario b, Scanner sc, String tipo) {
        int op;
        do {
            System.out.println("\n--- MENU DE " + tipo + " ---");
            System.out.println("1 - Cadastrar " + formatarTitulo(tipo));
            System.out.println("2 - Listar " + tipo);
            System.out.println("3 - Editar " + formatarTitulo(tipo));
            System.out.println("4 - Deletar " + formatarTitulo(tipo));
            System.out.println("0 - Voltar");

            op = lerOpcao(sc, 0, 4);

            switch (op) {
                case 1 -> rotearCadastro(b, sc, tipo);
                case 2 -> rotearListagem(b, tipo);
                case 3 -> rotearEdicao(b, sc, tipo);
                case 4 -> rotearDelecao(b, sc, tipo);
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    // --- ROTEADORES ---

    private static void rotearCadastro(Bibliotecario b, Scanner sc, String tipo) {
        switch (tipo) {
            case "LEITORES" -> cadastrarNovoLeitor(b, sc);
            case "AUTORES" -> cadastrarNovoAutor(b, sc);
            case "CATEGORIAS" -> cadastrarNovaCategoria(b, sc);
            case "EVENTOS" -> cadastrarNovoEvento(b, sc);
            case "EDITORAS" -> {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                if (!checkCancel(nome)) b.cadastrarEditora(nome);
            }
            case "PRATELEIRAS" -> {
                System.out.print("Localização: ");
                String local = sc.nextLine();
                if (!checkCancel(local)) b.cadastrarPrateleira(local);
            }
        }
    }

    private static void rotearListagem(Bibliotecario b, String tipo) {
        System.out.println("--- LISTA DE " + tipo + " ---");
        switch (tipo) {
            case "LEITORES" -> b.listarRegistros(b.getLeitores());
            case "AUTORES" -> b.listarRegistros(b.getAutores());
            case "CATEGORIAS" -> b.listarRegistros(b.getCategorias());
            case "EVENTOS" -> b.listarRegistros(b.getEventos());
            case "EDITORAS" -> b.listarRegistros(b.getEditoras());
            case "PRATELEIRAS" -> b.listarRegistros(b.getPrateleiras());
        }
    }

    private static void rotearEdicao(Bibliotecario b, Scanner sc, String tipo) {
        rotearListagem(b, tipo); // Mostra a lista antes
        switch (tipo) {
            case "LEITORES" -> editarLeitor(b, sc);
            case "AUTORES" -> editarAutor(b, sc);
            case "CATEGORIAS" -> editarCategoria(b, sc);
            case "EVENTOS" -> editarEvento(b, sc);
            case "EDITORAS" -> {
                Integer id = lerInteiroCancelavel(sc, "ID para editar: ");
                if (id == null) return;
                Editora e = b.buscarPorId(b.getEditoras(), id);
                if (e != null) {
                    System.out.print("Novo nome: ");
                    b.editarEditora(e, sc.nextLine());
                } else System.out.println("Não encontrado.");
            }
            case "PRATELEIRAS" -> {
                Integer id = lerInteiroCancelavel(sc, "ID para editar: ");
                if (id == null) return;
                Prateleira p = b.buscarPorId(b.getPrateleiras(), id);
                if (p != null) {
                    System.out.print("Novo local: ");
                    b.editarPrateleira(p, sc.nextLine());
                } else System.out.println("Não encontrado.");
            }
        }
    }

    private static void rotearDelecao(Bibliotecario b, Scanner sc, String tipo) {
        rotearListagem(b, tipo);
        switch (tipo) {
            case "LEITORES" -> deletarLeitor(b, sc);
            case "AUTORES" -> deletarAutor(b, sc);
            case "CATEGORIAS" -> deletarCategoria(b, sc);
            case "EVENTOS" -> deletarEvento(b, sc);
            case "EDITORAS" -> {
                Integer id = lerInteiroCancelavel(sc, "ID para deletar: ");
                if (id == null) return;
                Editora e = b.buscarPorId(b.getEditoras(), id);
                if (e != null) b.deletarEditora(e);
            }
            case "PRATELEIRAS" -> {
                Integer id = lerInteiroCancelavel(sc, "ID para deletar: ");
                if (id == null) return;
                Prateleira p = b.buscarPorId(b.getPrateleiras(), id);
                if (p != null) b.deletarPrateleira(p);
            }
        }
    }

    // ==================================================================================
    // 3. MENUS ESPECÍFICOS (Itens e Empréstimos)
    // ==================================================================================

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
                case 3 -> {
                    System.out.println("--- LISTA DE ITENS ---");
                    b.listarRegistros(b.getItens());
                }
                case 4 -> editarItem(b, sc);
                case 5 -> deletarItem(b, sc);
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
            System.out.println("4 - Renovar Empréstimo");
            System.out.println("0 - Voltar");
            op = lerOpcao(sc, 0, 4);
            switch (op) {
                case 1 -> realizarNovoEmprestimo(b, sc);
                case 2 -> {
                    System.out.println("--- LISTA DE EMPRESTIMOS ---");
                    b.listarRegistros(b.getEmprestimos()); // Emprestimo precisa ser Exibivel
                }
                case 3 -> realizarNovaDevolucao(b, sc);
                case 4 -> renovarEmprestimo(b, sc);
                case 0 -> System.out.println("↩ Voltando...");
            }
        } while (op != 0);
    }

    // ==================================================================================
    // 4. LÓGICAS DE CADASTRO (UI)
    // ==================================================================================

    private static void cadastrarNovoLeitor(Bibliotecario b, Scanner sc) {
        System.out.print("Nome (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (checkCancel(nome)) return;

        while (true) {
            System.out.print("CPF (ou 'c' para cancelar): ");
            String cpf = sc.nextLine();
            if (checkCancel(cpf)) return;
            // Tratamento de Exceção (Req 6.b) - Bibliotecario retorna false se falhar validação
            if (b.cadLeitor(nome, cpf)) break;
            else System.out.println("Tente novamente.");
        }
    }

    private static void cadastrarNovoAutor(Bibliotecario b, Scanner sc) {
        System.out.print("Nome (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (!checkCancel(nome)) b.cadastrarAutor(nome);
    }

    private static void cadastrarNovaCategoria(Bibliotecario b, Scanner sc) {
        System.out.print("Nome (ou 'c' para cancelar): ");
        String nome = sc.nextLine();
        if (!checkCancel(nome)) b.cadastrarCategoria(nome);
    }

    private static void cadastrarNovoEvento(Bibliotecario b, Scanner sc) {
        System.out.print("Nome do Evento (ou 'c' para cancelar): ");
        String nome = sc.nextLine(); if (checkCancel(nome)) return;
        System.out.print("Data (ou 'c' para cancelar): ");
        String data = sc.nextLine(); if (checkCancel(data)) return;
        System.out.print("Local (ou 'c' para cancelar): ");
        String local = sc.nextLine(); if (checkCancel(local)) return;
        b.cadastrarEvento(nome, data, local);
    }

    private static void cadastrarNovoLivro(Bibliotecario b, Scanner sc) {
        System.out.print("Título (ou 'c' para cancelar): ");
        String titulo = sc.nextLine(); if (checkCancel(titulo)) return;

        Categoria cat = selecionarOuCriarCategoria(b, sc);
        if (cat == null) return;

        Autor autor = selecionarOuCriarAutor(b, sc);
        if (autor == null) return;

        Editora editora = selecionarOuCriarEditora(b, sc);
        if (editora == null) return;

        Prateleira prat = selecionarOuCriarPrateleira(b, sc);
        if (prat == null) return;

        Integer qtd = lerInteiroCancelavel(sc, "Quantidade (ou 'c' para cancelar): ");
        if (qtd == null) return;

        b.addItem(new Livro(titulo, autor, qtd, cat, prat, editora));
    }

    private static void cadastrarNovaRevista(Bibliotecario b, Scanner sc) {
        System.out.print("Título (ou 'c' para cancelar): ");
        String titulo = sc.nextLine(); if (checkCancel(titulo)) return;

        Categoria cat = selecionarOuCriarCategoria(b, sc);
        if (cat == null) return;

        Editora editora = selecionarOuCriarEditora(b, sc);
        if (editora == null) return;

        Prateleira prat = selecionarOuCriarPrateleira(b, sc);
        if (prat == null) return;

        Integer qtd = lerInteiroCancelavel(sc, "Quantidade (ou 'c' para cancelar): ");
        if (qtd == null) return;

        b.addItem(new Revista(titulo, qtd, editora, cat, prat));
    }

    private static <T extends util.Identificavel & util.Exibivel> T selecionarGenerico(
            Bibliotecario b, Scanner sc, ArrayList<T> lista, String nomeTipo, Runnable acaoCadastro) {
        T selecionado = null;
        while (selecionado == null) {
            System.out.println("\n--- SELECIONE " + nomeTipo + " ---");
            b.listarRegistros(lista);
            System.out.println("Digite o ID, '0' para NOVO(A), ou 'c' para CANCELAR.");
            Integer id = lerInteiroCancelavel(sc, "Escolha: ");
            if (id == null) return null;

            if (id == 0) {
                acaoCadastro.run();
            } else {
                selecionado = b.buscarPorId(lista, id);
                if (selecionado == null) System.out.println("ID inválido.");
            }
        }
        return selecionado;
    }

    private static Categoria selecionarOuCriarCategoria(Bibliotecario b, Scanner sc) {
        return selecionarGenerico(b, sc, b.getCategorias(), "CATEGORIA", () -> cadastrarNovaCategoria(b, sc));
    }

    private static Autor selecionarOuCriarAutor(Bibliotecario b, Scanner sc) {
        return selecionarGenerico(b, sc, b.getAutores(), "AUTOR", () -> cadastrarNovoAutor(b, sc));
    }

    private static Editora selecionarOuCriarEditora(Bibliotecario b, Scanner sc) {
        Editora editora = null;
        while (editora == null) {
            System.out.println("\n--- SELECIONE A EDITORA ---");
            b.listarRegistros(b.getEditoras());
            System.out.println("Digite o ID, '0' para NOVA, ou 'c' para CANCELAR.");
            Integer id = lerInteiroCancelavel(sc, "Escolha: ");
            if (id == null) return null;
            if (id == 0) {
                System.out.print("Nome da Nova Editora: ");
                String nome = sc.nextLine();
                if (!checkCancel(nome)) b.cadastrarEditora(nome);
            } else {
                editora = b.buscarPorId(b.getEditoras(), id);
                if (editora == null) System.out.println("ID inválido.");
            }
        }
        return editora;
    }

    private static Prateleira selecionarOuCriarPrateleira(Bibliotecario b, Scanner sc) {
        Prateleira prat = null;
        while (prat == null) {
            System.out.println("\n--- SELECIONE A PRATELEIRA ---");
            b.listarRegistros(b.getPrateleiras());
            System.out.println("Digite o ID, '0' para NOVA, ou 'c' para CANCELAR.");
            Integer id = lerInteiroCancelavel(sc, "Escolha: ");
            if (id == null) return null;
            if (id == 0) {
                System.out.print("Local da Nova Prateleira: ");
                String local = sc.nextLine();
                if (!checkCancel(local)) b.cadastrarPrateleira(local);
            } else {
                prat = b.buscarPorId(b.getPrateleiras(), id);
                if (prat == null) System.out.println("ID inválido.");
            }
        }
        return prat;
    }

    // --- LÓGICAS DE EDIÇÃO ---

    private static void editarLeitor(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do leitor a editar (ou 'c' para cancelar): ");
        if (id == null) return;
        Leitor leitor = b.buscarPorId(b.getLeitores(), id);
        if (leitor == null) { System.out.println("Não encontrado."); return; }

        System.out.println("Editando Leitor: " + leitor.getNome());
        System.out.println("1 - Nome | 2 - CPF | 0 - Cancelar");
        int op = lerOpcao(sc, 0, 2);
        switch (op) {
            case 1 -> {
                System.out.print("Novo nome: ");
                b.editarLeitorNome(leitor, sc.nextLine());
            }
            case 2 -> {
                System.out.print("Novo CPF: ");
                b.editarLeitorCPF(leitor, sc.nextLine());
            }
        }
    }

    private static void editarAutor(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do autor: ");
        if (id == null) return;
        Autor autor = b.buscarPorId(b.getAutores(), id);
        if (autor != null) {
            System.out.print("Novo nome: ");
            b.editarAutor(autor, sc.nextLine());
        }
    }

    private static void editarCategoria(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID da categoria: ");
        if (id == null) return;
        Categoria cat = b.buscarPorId(b.getCategorias(), id);
        if (cat != null) {
            System.out.print("Novo nome: ");
            b.editarCategoria(cat, sc.nextLine());
        }
    }

    private static void editarEvento(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do evento: ");
        if (id == null) return;
        Evento ev = b.buscarPorId(b.getEventos(), id);
        if (ev == null) return;

        System.out.println("1-Nome, 2-Data, 3-Local, 0-Sair");
        int op = lerOpcao(sc, 0, 3);
        if (op != 0) {
            System.out.print("Novo valor: ");
            b.editarEvento(ev, op, sc.nextLine());
        }
    }

    private static void editarItem(Bibliotecario b, Scanner sc) {
        System.out.println("--- LISTA DE ITENS ---");
        b.listarRegistros(b.getItens());
        Integer id = lerInteiroCancelavel(sc, "ID p/ editar: ");
        if (id == null) return;

        Item item = b.buscarPorId(b.getItens(), id);
        if (item == null) return;

        System.out.println("--- Editando: " + item.getTitulo() + " ---");
        System.out.println("1-Título, 2-Qtd, 3-Categoria, 4-Autor/Editora, 5-Prateleira, 0-Cancelar");
        int op = lerOpcao(sc, 0, 5);

        switch (op) {
            case 1 -> { System.out.print("Novo título: "); b.editarItem(item, 1, sc.nextLine(), null); }
            case 2 -> {
                Integer qtd = lerInteiroCancelavel(sc, "Nova qtd: ");
                if (qtd != null) b.editarItem(item, 2, qtd.toString(), null);
            }
            case 3 -> {
                Categoria c = selecionarOuCriarCategoria(b, sc);
                if (c != null) b.editarItem(item, 3, null, c);
            }
            case 4 -> {
                if (item instanceof Livro) {
                    Autor a = selecionarOuCriarAutor(b, sc);
                    if (a != null) b.editarItem(item, 4, null, a);
                } else if (item instanceof Revista) {
                    Editora e = selecionarOuCriarEditora(b, sc);
                    if (e != null) b.editarItem(item, 4, null, e);
                }
            }
            case 5 -> {
                Prateleira p = selecionarOuCriarPrateleira(b, sc);
                if (p != null) b.editarItem(item, 5, null, p);
            }
        }
    }

    // ==================================================================================
    // 7. LÓGICAS DE DELEÇÃO
    // ==================================================================================

    private static void deletarLeitor(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do leitor: ");
        if (id == null) return;
        Leitor l = b.buscarPorId(b.getLeitores(), id);
        if (l != null && confirmar(sc)) b.deletarLeitor(l);
    }

    private static void deletarAutor(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do autor: ");
        if (id == null) return;
        Autor a = b.buscarPorId(b.getAutores(), id);
        if (a != null && confirmar(sc)) b.deletarAutor(a);
    }

    private static void deletarCategoria(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID da categoria: ");
        if (id == null) return;
        Categoria c = b.buscarPorId(b.getCategorias(), id);
        if (c != null && confirmar(sc)) b.deletarCategoria(c);
    }

    private static void deletarItem(Bibliotecario b, Scanner sc) {
        System.out.println("--- LISTA DE ITENS ---");
        b.listarRegistros(b.getItens());
        Integer id = lerInteiroCancelavel(sc, "ID do item: ");
        if (id == null) return;
        Item i = b.buscarPorId(b.getItens(), id);
        if (i != null && confirmar(sc)) b.deletarItem(i);
    }

    private static void deletarEvento(Bibliotecario b, Scanner sc) {
        Integer id = lerInteiroCancelavel(sc, "ID do evento: ");
        if (id == null) return;
        Evento e = b.buscarPorId(b.getEventos(), id);
        if (e != null && confirmar(sc)) b.deletarEvento(e);
    }

    // ==================================================================================
    // 8. LÓGICAS DE EMPRÉSTIMO
    // ==================================================================================

    private static void realizarNovoEmprestimo(Bibliotecario b, Scanner sc) {
        Leitor leitor = autenticarLeitor(b, sc);
        if (leitor == null) return;

        // Verifica limite (Regra de Negócio na Main apenas para UI, validação final no controller seria ideal)
        if (b.getEmprestimosAtivos(leitor).size() >= 3) {
            System.out.println("Leitor já possui 3 empréstimos ativos.");
            return;
        }

        System.out.println("\n--- SELECIONE O ITEM ---");
        b.listarRegistros(b.getItens());
        Integer idItem = lerInteiroCancelavel(sc, "ID do Item: ");
        if (idItem == null) return;

        Item item = b.buscarPorId(b.getItens(), idItem);
        if (item == null || !item.isDisponivel()) {
            System.out.println("Item indisponível ou inválido.");
            return;
        }

        System.out.print("Data Devolução (ou 'c'): ");
        String data = sc.nextLine();
        if (checkCancel(data)) return;

        b.realizarEmprestimo(leitor, item, data);
    }

    private static void realizarNovaDevolucao(Bibliotecario b, Scanner sc) {
        Leitor leitor = autenticarLeitor(b, sc);
        if (leitor == null) return;

        ArrayList<Emprestimo> ativos = b.getEmprestimosAtivos(leitor);
        if (ativos.isEmpty()) { System.out.println("Sem empréstimos ativos."); return; }

        // Como Emprestimo não é 'Exibivel' genericamente, usamos o loop manual aqui ou tornamos Exibivel
        for (Emprestimo e : ativos) e.exibirInfo();

        Integer idEmp = lerInteiroCancelavel(sc, "ID do empréstimo: ");
        if (idEmp == null) return;

        Emprestimo emp = b.getEmprestimoAtivoPorId(idEmp, leitor);
        if (emp != null) b.realizarDevolucao(emp);
        else System.out.println("Empréstimo não encontrado.");
    }

    private static void renovarEmprestimo(Bibliotecario b, Scanner sc) {
        Leitor leitor = autenticarLeitor(b, sc);
        if (leitor == null) return;

        ArrayList<Emprestimo> ativos = b.getEmprestimosAtivos(leitor);
        if (ativos.isEmpty()) { System.out.println("Sem empréstimos ativos."); return; }

        for (Emprestimo e : ativos) e.exibirInfo();

        Integer idEmp = lerInteiroCancelavel(sc, "ID do empréstimo: ");
        if (idEmp == null) return;

        Emprestimo emp = b.getEmprestimoAtivoPorId(idEmp, leitor);
        if (emp != null) {
            System.out.print("Nova data: ");
            String data = sc.nextLine();
            if (!checkCancel(data)) b.renovarEmprestimo(emp, data);
        } else System.out.println("Não encontrado.");
    }

    private static Leitor autenticarLeitor(Bibliotecario b, Scanner sc) {
        System.out.println("--- Autenticação ---");
        Integer id = lerInteiroCancelavel(sc, "ID do Leitor: ");
        if (id == null) return null;

        Leitor leitor = b.buscarPorId(b.getLeitores(), id);
        if (leitor == null) { System.out.println("Leitor não encontrado."); return null; }

        while (true) {
            System.out.print("CPF de " + leitor.getNome() + ": ");
            String cpf = sc.nextLine();
            if (checkCancel(cpf)) return null;
            if (cpf.equals(leitor.getCpf())) return leitor;
            System.out.println("CPF incorreto.");
        }
    }

    // ==================================================================================
    // 9. CONFIGURAÇÃO E UTILITÁRIOS
    // ==================================================================================

    private static void menuConfiguracao(Bibliotecario b, Scanner sc) {
        System.out.println("\n--- CONFIGURAÇÕES ---");
        System.out.print("Novo Nome de Exibição (Enter p/ manter): ");
        String nome = sc.nextLine();
        if (nome.isEmpty()) nome = b.getConfig().getNomeExibicao();

        System.out.println("Novo Tema: 1-Claro, 2-Escuro");
        int op = lerOpcao(sc, 1, 2);
        String tema = (op == 1) ? "CLARO" : "ESCURO";

        b.atualizarConfig(nome, tema);
        System.out.println("Salvo!");
    }

    private static void resetarSistema(Bibliotecario b, Scanner sc) {
        if (confirmar(sc)) b.resetarDados();
    }

}