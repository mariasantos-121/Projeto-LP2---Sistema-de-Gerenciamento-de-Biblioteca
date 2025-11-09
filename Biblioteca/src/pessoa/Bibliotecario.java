package pessoa;

import emprestimo.Emprestimo;
import item.Item;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*; // Importa todas as classes de Input/Output

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
    }

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
    /**
     * Limpa todos os dados da memória e salva esse estado "vazio" no arquivo.
     */
    public void resetarDados() {
        // 1. Pede a confirmação
        System.out.print("TEM CERTEZA? Isso apagará TODOS os dados salvos permanentemente. (s/n): ");
        String confirmacao = sc.nextLine(); // Usa o Scanner da classe

        // 2. Verifica a resposta
        if (confirmacao.equalsIgnoreCase("s")) {
            // 3. Se for "s" (ou "S"), executa a lógica original
            this.leitores.clear();
            this.itens.clear();
            this.emprestimos.clear();

            Item.setContadorID(0);
            Leitor.setContadorLeitor(0);
            Emprestimo.setContadorID(0);

            salvarDados();
            System.out.println(">>> DADOS DA BIBLIOTECA RESETADOS COM SUCESSO! <<<");

        } else {
            // 4. Se for qualquer outra coisa, cancela
            System.out.println("Operação de reset CANCELADA.");
        }
    }

    /**
     * Salva o estado atual das listas e contadores em um arquivo.
     */
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            // Salva os contadores estáticos
            oos.writeInt(Item.getContadorID());
            oos.writeInt(Leitor.getContadorLeitor());
            oos.writeInt(Emprestimo.getContadorID());

            // Salva as listas
            oos.writeObject(leitores);
            oos.writeObject(itens);
            oos.writeObject(emprestimos);

            System.out.println("Dados salvos com sucesso em biblioteca.dat");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado das listas e contadores do arquivo.
     */
    @SuppressWarnings("unchecked") // Suprime o aviso do cast das ArrayLists
    public void carregarDados() {
        File arquivo = new File("biblioteca.dat");
        if (!arquivo.exists()) {
            System.out.println("Arquivo de dados não encontrado. Começando com novos dados.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            // Carrega os contadores estáticos (NA MESMA ORDEM que salvou)
            Item.setContadorID(ois.readInt());
            Leitor.setContadorLeitor(ois.readInt());
            Emprestimo.setContadorID(ois.readInt());

            // Carrega as listas (NA MESMA ORDEM que salvou)
            this.leitores = (ArrayList<Leitor>) ois.readObject();
            this.itens = (ArrayList<Item>) ois.readObject();
            this.emprestimos = (ArrayList<Emprestimo>) ois.readObject();

            System.out.println("Dados carregados com sucesso de biblioteca.dat");
        } catch (FileNotFoundException e) {
            // Isso não deve acontecer por causa do 'arquivo.exists()'
            System.out.println("Arquivo de dados não encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            // Se der erro, zera as listas para evitar dados corrompidos
            this.leitores = new ArrayList<>();
            this.itens = new ArrayList<>();
            this.emprestimos = new ArrayList<>();
        }
    }

    // ... (dentro da classe Bibliotecario) ...

    public void deletarLeitor() {
        if (leitores.isEmpty()) {
            System.out.println("Não há leitores para deletar.");
            return;
        }

        System.out.println("--- Lista de Leitores ---");
        listLeitores();

        int idLeitor = lerInteiro("Digite o ID do leitor que deseja DELETAR: ");
        Leitor leitor = buscaID(idLeitor);

        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return;
        }

        // VERIFICAÇÃO DE DEPENDÊNCIA
        for (Emprestimo emp : emprestimos) {
            if (emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                System.out.println("ERRO: Este leitor possui um empréstimo ativo (ID: " + emp.getId() + ").");
                System.out.println("Realize a devolução antes de deletar o leitor.");
                return;
            }
        }

        // CONFIRMAÇÃO
        System.out.print("Tem certeza que deseja deletar o leitor: " + leitor.getNome() + " (ID: " + leitor.getId() + ")? (s/n): ");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            leitores.remove(leitor); // Remove da lista
            salvarDados(); // Salva a alteração no arquivo
            System.out.println("Leitor deletado com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void deletarItem() {
        if (itens.isEmpty()) {
            System.out.println("Não há itens para deletar.");
            return;
        }

        System.out.println("--- Lista de Itens ---");
        listarItens();

        int idItem = lerInteiro("Digite o ID do item que deseja DELETAR: ");
        Item item = buscarItemPorId(idItem);

        if (item == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        // VERIFICAÇÃO DE DEPENDÊNCIA
        for (Emprestimo emp : emprestimos) {
            if (emp.getItem().equals(item) && !emp.isDevolvido()) {
                System.out.println("ERRO: Este item está atualmente emprestado (Empréstimo ID: " + emp.getId() + ").");
                System.out.println("Realize a devolução antes de deletar o item.");
                return;
            }
        }

        // CONFIRMAÇÃO
        System.out.print("Tem certeza que deseja deletar o item: " + item.getTitulo() + " (ID: " + item.getId() + ")? (s/n): ");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            itens.remove(item); // Remove da lista
            salvarDados(); // Salva a alteração no arquivo
            System.out.println("Item deletado com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

}


