package pessoa;

import emprestimo.Emprestimo;
import item.Item;
import item.Categoria; // IMPORTAR
import item.Livro;    // IMPORTAR
import item.Revista;  // IMPORTAR
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*; // Importa todas as classes de Input/Output

public class Bibliotecario extends Pessoa {
    private static int contadorBibliotecario = 1;
    private ArrayList<Leitor> leitores;
    private ArrayList<Autor> autores;
    private ArrayList<Categoria> categorias;
    private ArrayList<Item> itens;
    private ArrayList<Emprestimo> emprestimos;
    private Scanner sc = new Scanner(System.in);

    public Bibliotecario(String nome) {
        super(nome, contadorBibliotecario++);
        this.leitores = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.categorias = new ArrayList<>();
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

    // --- NOVOS MÉTODOS PARA AUTOR ---
    public void cadastrarAutor() {
        System.out.print("Nome do Autor: ");
        String nome = sc.nextLine();
        Autor novoAutor = new Autor(nome);
        autores.add(novoAutor);
        System.out.println("Autor cadastrado com sucesso! ID: " + novoAutor.getId());
    }

    public void listarAutores() {
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }
        for (Autor a : autores) {
            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome());
        }
    }

    public Autor buscarAutorPorId(int id) {
        for (Autor a : autores) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // --- NOVOS MÉTODOS PARA CATEGORIA ---
    public void cadastrarCategoria() {
        System.out.print("Nome da Categoria: ");
        String nome = sc.nextLine();
        Categoria novaCat = new Categoria(nome);
        categorias.add(novaCat);
        System.out.println("Categoria cadastrada com sucesso! ID: " + novaCat.getId());
    }

    public void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }
        for (Categoria c : categorias) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
        }
    }

    public Categoria buscarCategoriaPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
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

    public void editarLeitor() {
        if (leitores.isEmpty()) {
            System.out.println("Não há leitores para editar.");
            return;
        }

        System.out.println("--- Lista de Leitores ---");
        listLeitores();

        int idLeitor = lerInteiro("Digite o ID do leitor que deseja EDITAR: ");
        Leitor leitor = buscaID(idLeitor);

        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return;
        }

        System.out.println("Editando Leitor: " + leitor.getNome());
        System.out.println("O que deseja editar?");
        System.out.println("1 - Nome");
        System.out.println("2 - CPF");
        System.out.println("0 - Cancelar");

        int opcao = lerInteiro("Escolha: ");

        switch (opcao) {
            case 1 -> {
                System.out.print("Digite o novo nome: ");
                String novoNome = sc.nextLine();
                leitor.setNome(novoNome);
                System.out.println("Nome atualizado!");
            }
            case 2 -> {
                String novoCpf;
                while (true) {
                    System.out.print("Digite o novo CPF (somente números): ");
                    novoCpf = sc.nextLine();
                    if (validarCPF(novoCpf)) {
                        leitor.setCpf(novoCpf);
                        System.out.println("CPF atualizado!");
                        break;
                    } else {
                        System.out.println("CPF inválido! Tente novamente.");
                    }
                }
            }
            case 0 -> {
                System.out.println("Edição cancelada.");
                return; // Sai do método sem salvar
            }
            default -> System.out.println("Opção inválida.");
        }

        salvarDados(); // Salva as alterações
    }

    // --- ATUALIZAR editarItem() (IMPORTANTE!) ---
    // (O método antigo vai quebrar, pois autor não é mais String)
    public void editarItem() {
        if (itens.isEmpty()) {
            System.out.println("Não há itens para editar.");
            return;
        }

        System.out.println("--- Lista de Itens ---");
        listarItens();
        int idItem = lerInteiro("Digite o ID do item que deseja EDITAR: ");
        Item item = buscarItemPorId(idItem);

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

        if (item instanceof item.Livro) {
            System.out.println("4 - Autor");
        } else if (item instanceof item.Revista) {
            System.out.println("4 - Editora");
        }
        System.out.println("0 - Cancelar");

        int opcao = lerInteiro("Escolha: ");

        switch (opcao) {
            case 1 -> { /* ... (igual - Título) ... */ }
            case 2 -> { /* ... (igual - Quantidade) ... */ }
            case 3 -> { // NOVO CASO: EDITAR CATEGORIA
                System.out.println("--- Categorias Disponíveis ---");
                listarCategorias();
                int idCat = lerInteiro("Digite o ID da nova Categoria: ");
                Categoria novaCat = buscarCategoriaPorId(idCat);
                if (novaCat != null) {
                    item.setCategoria(novaCat);
                } else {
                    System.out.println("Categoria não encontrada.");
                }
            }
            case 4 -> { // MUDOU DE 3 PARA 4
                if (item instanceof item.Livro livro) {
                    // NOVO: EDITAR AUTOR (OBJETO)
                    System.out.println("--- Autores Disponíveis ---");
                    listarAutores();
                    int idAut = lerInteiro("Digite o ID do novo Autor: ");
                    Autor novoAutor = buscarAutorPorId(idAut);
                    if (novoAutor != null) {
                        livro.setAutor(novoAutor);
                    } else {
                        System.out.println("Autor não encontrado.");
                    }
                } else if (item instanceof item.Revista revista) {
                    // (igual - Editora)
                    System.out.print("Digite a nova editora: ");
                    String novaEditora = sc.nextLine();
                    revista.setEditora(novaEditora);
                } else {
                    System.out.println("Opção inválida.");
                    return;
                }
            }
            case 0 -> { /* ... (igual - Cancelar) ... */ }
            default -> { /* ... (igual - Default) ... */ }
        }

        System.out.println("Item atualizado com sucesso!");
        salvarDados();
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

    // ... (depois do método selecionarOuCriarCategoria()) ...

    public void editarAutor() {
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor para editar.");
            return;
        }

        System.out.println("--- Lista de Autores ---");
        listarAutores();
        int idAut = lerInteiro("Digite o ID do autor que deseja EDITAR: ");
        Autor autor = buscarAutorPorId(idAut);

        if (autor == null) {
            System.out.println("Autor não encontrado.");
            return;
        }

        System.out.print("Digite o novo nome para '" + autor.getNome() + "': ");
        String novoNome = sc.nextLine();
        autor.setNome(novoNome);
        salvarDados();
        System.out.println("Autor atualizado com sucesso!");
    }

    public void deletarAutor() {
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor para deletar.");
            return;
        }

        System.out.println("--- Lista de Autores ---");
        listarAutores();
        int idAut = lerInteiro("Digite o ID do autor que deseja DELETAR: ");
        Autor autor = buscarAutorPorId(idAut);

        if (autor == null) {
            System.out.println("Autor não encontrado.");
            return;
        }

        // VERIFICAÇÃO DE DEPENDÊNCIA
        for (Item item : this.itens) {
            if (item instanceof item.Livro livro) { // Java 17+
                if (livro.getAutor().equals(autor)) {
                    System.out.println("ERRO: Este autor está associado ao livro '" + livro.getTitulo() + "'.");
                    System.out.println("Não é possível deletar.");
                    return;
                }
            }
        }

        // Confirmação
        System.out.print("Tem certeza que deseja deletar o autor: " + autor.getNome() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            autores.remove(autor);
            salvarDados();
            System.out.println("Autor deletado com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void editarCategoria() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria para editar.");
            return;
        }

        System.out.println("--- Lista de Categorias ---");
        listarCategorias();
        int idCat = lerInteiro("Digite o ID da categoria que deseja EDITAR: ");
        Categoria cat = buscarCategoriaPorId(idCat);

        if (cat == null) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        System.out.print("Digite o novo nome para '" + cat.getNome() + "': ");
        String novoNome = sc.nextLine();
        cat.setNome(novoNome);
        salvarDados();
        System.out.println("Categoria atualizada com sucesso!");
    }

    public void deletarCategoria() {
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria para deletar.");
            return;
        }

        System.out.println("--- Lista de Categorias ---");
        listarCategorias();
        int idCat = lerInteiro("Digite o ID da categoria que deseja DELETAR: ");
        Categoria cat = buscarCategoriaPorId(idCat);

        if (cat == null) {
            System.out.println("Categoria não encontrada.");
            return;
        }

        // VERIFICAÇÃO DE DEPENDÊNCIA
        for (Item item : this.itens) {
            if (item.getCategoria().equals(cat)) {
                System.out.println("ERRO: Esta categoria está associada ao item '" + item.getTitulo() + "'.");
                System.out.println("Não é possível deletar.");
                return;
            }
        }

        // Confirmação
        System.out.print("Tem certeza que deseja deletar a categoria: " + cat.getNome() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            categorias.remove(cat);
            salvarDados();
            System.out.println("Categoria deletada com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
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
            this.autores.clear(); // <-- ADICIONADO
            this.categorias.clear(); // <-- ADICIONADO

            Item.setContadorID(0);
            Leitor.setContadorLeitor(0);
            Emprestimo.setContadorID(0);
            Autor.setContadorID(0); // <-- ADICIONADO
            Categoria.setContadorID(0); // <-- ADICIONADO

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
            oos.writeInt(Autor.getContadorID());      // NOVO
            oos.writeInt(Categoria.getContadorID());  // NOVO

            // Salva as listas
            oos.writeObject(leitores);
            oos.writeObject(itens);
            oos.writeObject(emprestimos);
            oos.writeObject(autores);                 // NOVO
            oos.writeObject(categorias);              // NOVO

            System.out.println("Dados salvos com sucesso em biblioteca.dat");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado das listas e contadores do arquivo.
     */
// --- ATUALIZAR carregarDados() ---
    @SuppressWarnings("unchecked")
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
            Autor.setContadorID(ois.readInt());         // NOVO
            Categoria.setContadorID(ois.readInt());     // NOVO

            // Carrega as listas (NA MESMA ORDEM que salvou)
            this.leitores = (ArrayList<Leitor>) ois.readObject();
            this.itens = (ArrayList<Item>) ois.readObject();
            this.emprestimos = (ArrayList<Emprestimo>) ois.readObject();
            this.autores = (ArrayList<Autor>) ois.readObject();          // NOVO
            this.categorias = (ArrayList<Categoria>) ois.readObject();   // NOVO

            System.out.println("Dados carregados com sucesso de biblioteca.dat");
        } catch (FileNotFoundException e) { /*...*/ }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            // Zera TUDO se der erro
            this.leitores = new ArrayList<>();
            this.itens = new ArrayList<>();
            this.emprestimos = new ArrayList<>();
            this.autores = new ArrayList<>();         // NOVO
            this.categorias = new ArrayList<>();      // NOVO
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


