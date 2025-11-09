package pessoa;

import emprestimo.Emprestimo;
import item.Item;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import dados.BaseDeDados;


public class Bibliotecario extends Pessoa {
    private static int contadorBibliotecario = 0;
    private ArrayList<Leitor> leitores;
    private ArrayList<Item> itens;
    private ArrayList<Emprestimo> emprestimos;
    private Scanner sc = new Scanner(System.in);

    public Bibliotecario(String nome) {
        super(nome, contadorBibliotecario++);
        this.leitores = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.emprestimos = new ArrayList<>();

        this.itens.addAll(BaseDeDados.carregarItensIniciais());
        this.leitores.addAll(BaseDeDados.carregarLeitoresIniciais());
    }

    // -----------------------
    // CADASTRO DE LEITOR
    // -----------------------
    public void cadLeitor() {
        System.out.print("Digite o nome do Leitor: ");
        String nome = sc.nextLine();

        String cpf;
        while (true) {
            System.out.print("Digite o CPF (somente números): ");
            cpf = sc.nextLine();

            if (validarCPF(cpf)) {
                break;
            } else {
                System.out.println("CPF inválido! Digite um CPF válido (com 11 dígitos corretos).");
            }
        }

        Leitor leitor = new Leitor(nome, cpf);
        leitores.add(leitor);
        System.out.println("Leitor cadastrado com sucesso! ID: " + leitor.getId());
    }

    public void listLeitores() {
        if (leitores.isEmpty()) {
            System.out.println("Ainda não há leitores cadastrados.");
            return;
        }
        for (Leitor l : leitores) {
            System.out.println("ID: " + l.getId() + " | Nome: " + l.getNome());
        }
    }

    public Leitor buscaID(int id) {
        for (Leitor leitor : leitores) {
            if (leitor.getId() == id) {
                return leitor;
            }
        }
        return null;
    }

    // -----------------------
    // AUTENTICAÇÃO DE LEITOR
    // -----------------------
    public Leitor autenticarLeitor() {
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado ainda.");
            return null;
        }

        Leitor leitor = null;
        while (leitor == null) {
            int idLeitor = lerInteiro("Informe o ID do leitor: ");
            leitor = buscaID(idLeitor);

            if (leitor == null) {
                System.out.println("ID não encontrado. Tente novamente.");
                continue;
            }

            String cpf;
            while (true) {
                System.out.print("Digite o CPF para confirmação: ");
                cpf = sc.nextLine();

                if (!validarCPF(cpf)) {
                    System.out.println("CPF inválido! Digite um CPF real com 11 dígitos válidos.");
                    continue;
                }

                if (cpf.equals(leitor.getCpf())) {
                    System.out.println("Olá, " + leitor.getNome() + "!");
                    return leitor;
                } else {
                    System.out.println("CPF não corresponde ao cadastro. Tente novamente.");
                }
            }
        }
        return leitor;
    }

    // -----------------------
    // MENU DE ITENS
    // -----------------------
    public void addItem(Item item) {
        itens.add(item);
        System.out.println("Item adicionado com sucesso! ID: " + item.getId());
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }
        for (Item i : itens) {
            i.exibirInfo();
        }
    }

    public Item buscarItemPorId(int id) {
        for (Item item : itens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    // -----------------------
    // MENU DE EMPRÉSTIMOS
    // -----------------------
    public void realizarEmprestimo() {
        Leitor leitor = autenticarLeitor();
        if (leitor == null) return;

        int emprestimosAtivos = 0;
        for (Emprestimo emp : emprestimos) {
            if (emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                emprestimosAtivos++;
            }
        }

        if (emprestimosAtivos >= 3) {
            System.out.println("O leitor \"" + leitor.getNome() + "\" já possui 3 empréstimos ativos.");
            return;
        }

        listarItens();

        Item itemEscolhido = null;
        while (itemEscolhido == null) {
            int idItem = lerInteiro("Digite o ID do item que deseja emprestar: ");
            itemEscolhido = buscarItemPorId(idItem);
            if (itemEscolhido == null || !itemEscolhido.isDisponivel()) {
                System.out.println("Item inválido ou indisponível. Tente novamente.");
                itemEscolhido = null;
            }
        }

        Emprestimo emp = new Emprestimo(leitor, itemEscolhido);
        emprestimos.add(emp);

        System.out.println("Empréstimo realizado com sucesso!");
        emp.exibirInfo();
    }

    public void listarEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }

        for (Emprestimo emp : emprestimos) {
            emp.exibirInfo();
        }
    }

    // -----------------------
    // MENU DE DEVOLUÇÕES
    // -----------------------
    public void realizarDevolucao() {
        Leitor leitor = autenticarLeitor();
        if (leitor == null) return;

        System.out.println("Empréstimos ativos do leitor:");
        boolean encontrou = false;

        for (Emprestimo emp : emprestimos) {
            if (emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                emp.exibirInfo();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum empréstimo ativo encontrado.");
            return;
        }

        Emprestimo emprestimo = null;
        while (emprestimo == null) {
            int idEmp = lerInteiro("Digite o ID do empréstimo a devolver: ");
            for (Emprestimo emp : emprestimos) {
                if (emp.getId() == idEmp && !emp.isDevolvido()) {
                    emprestimo = emp;
                    break;
                }
            }

            if (emprestimo == null) {
                System.out.println("Empréstimo não encontrado ou já devolvido. Tente novamente.");
            }
        }

        emprestimo.devolver();
    }

    // -----------------------
    // MÉTODO AUXILIAR PARA LER INTEIROS
    // -----------------------
    private int lerInteiro(String mensagem) {
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

    // -----------------------
    // VERIFICAÇÃO COMPLETA DE CPF
    // -----------------------
    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // remove pontos e traços

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

            return digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    public void carregarItensIniciais(ArrayList<Item> listaItens) {
        this.itens.addAll(listaItens);
        System.out.println(listaItens.size() + " itens pré-carregados no sistema.");
    }
}
