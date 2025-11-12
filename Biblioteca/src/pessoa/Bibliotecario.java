package pessoa;

import emprestimo.Emprestimo;
import item.Item;
import item.Categoria;
import item.Livro;
import item.Revista;
import java.util.ArrayList;
import java.io.*;

public class Bibliotecario extends Pessoa {

    private static int contadorBibliotecario = 1;
    private ArrayList<Leitor> leitores;
    private ArrayList<Autor> autores;
    private ArrayList<Categoria> categorias;
    private ArrayList<Item> itens;
    private ArrayList<Emprestimo> emprestimos;

    public Bibliotecario(String nome) {
        super(nome, contadorBibliotecario++);
        this.leitores = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    // --- 1. MÉTODOS DE LEITOR ---

    public boolean cadLeitor(String nome, String cpf) {
        if (!validarCPF(cpf)) {
            System.out.println("Erro: CPF inválido!");
            return false;
        }
        Leitor leitor = new Leitor(nome, cpf);
        leitores.add(leitor);
        System.out.println("Leitor cadastrado com sucesso! ID: " + leitor.getId());
        salvarDados();
        return true;
    }

    public void listLeitores() {
        if (leitores.isEmpty()) {
            System.out.println("Ainda não há leitores cadastrados.");
            return;
        }
        for (Leitor l : leitores) {
            l.exibirInfo();
            System.out.println("-----------------------------");
        }
    }

    public void editarLeitorNome(Leitor leitor, String novoNome) {
        leitor.setNome(novoNome);
        System.out.println("Nome atualizado!");
        salvarDados();
    }

    public boolean editarLeitorCPF(Leitor leitor, String novoCpf) {
        if (!validarCPF(novoCpf)) {
            System.out.println("Erro: CPF inválido!");
            return false;
        }
        leitor.setCpf(novoCpf);
        System.out.println("CPF atualizado!");
        salvarDados();
        return true;
    }

    public void deletarLeitor(Leitor leitor) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                System.out.println("ERRO: Este leitor possui um empréstimo ativo (ID: " + emp.getId() + ").");
                System.out.println("Realize a devolução antes de deletar o leitor.");
                return;
            }
        }

        leitores.remove(leitor);
        salvarDados();
        System.out.println("Leitor deletado com sucesso.");
    }

    public Leitor buscaID(int id) {
        for (Leitor leitor : leitores) {
            if (leitor.getId() == id) {
                return leitor;
            }
        }
        return null;
    }

    public boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

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


    // --- 2. MÉTODOS DE AUTOR ---
    public void cadastrarAutor(String nome) {
        Autor novoAutor = new Autor(nome);
        autores.add(novoAutor);
        System.out.println("Autor cadastrado com sucesso! ID: " + novoAutor.getId());
        salvarDados();
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

    public void editarAutor(Autor autor, String novoNome) {
        autor.setNome(novoNome);
        salvarDados();
        System.out.println("Autor atualizado com sucesso!");
    }

    public void deletarAutor(Autor autor) {
        for (Item item : this.itens) {
            if (item instanceof item.Livro livro) {
                if (livro.getAutor().equals(autor)) {
                    System.out.println("ERRO: Este autor está associado ao livro '" + livro.getTitulo() + "'.");
                    return;
                }
            }
        }
        autores.remove(autor);
        salvarDados();
        System.out.println("Autor deletado com sucesso.");
    }

    public Autor buscarAutorPorId(int id) {
        for (Autor a : autores) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // --- 3. MÉTODOS DE CATEGORIA ---
    public void cadastrarCategoria(String nome) {
        Categoria novaCat = new Categoria(nome);
        categorias.add(novaCat);
        System.out.println("Categoria cadastrada com sucesso! ID: " + novaCat.getId());
        salvarDados();
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

    public void editarCategoria(Categoria cat, String novoNome) {
        cat.setNome(novoNome);
        salvarDados();
        System.out.println("Categoria atualizada com sucesso!");
    }

    public void deletarCategoria(Categoria cat) {
        for (Item item : this.itens) {
            if (item.getCategoria().equals(cat)) {
                System.out.println("ERRO: Esta categoria está associada ao item '" + item.getTitulo() + "'.");
                return;
            }
        }
        categorias.remove(cat);
        salvarDados();
        System.out.println("Categoria deletada com sucesso.");
    }

    public Categoria buscarCategoriaPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        itens.add(item);
        System.out.println("Item adicionado com sucesso! ID: " + item.getId());
        salvarDados();
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

    public void editarItem(Item item, int opcao, String valor, Object obj) {
        switch (opcao) {
            case 1 -> item.setTitulo(valor);
            case 2 -> item.setQuantidadeExemplares(Integer.parseInt(valor));
            case 3 -> item.setCategoria((Categoria) obj);
            case 4 -> {
                if (item instanceof Livro livro) {
                    livro.setAutor((Autor) obj);
                } else if (item instanceof Revista revista) {
                    revista.setEditora(valor);
                }
            }
        }
        System.out.println("Item atualizado com sucesso!");
        salvarDados();
    }

    public void deletarItem(Item item) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getItem().equals(item) && !emp.isDevolvido()) {
                System.out.println("ERRO: Este item está atualmente emprestado (Empréstimo ID: " + emp.getId() + ").");
                return;
            }
        }
        itens.remove(item);
        salvarDados();
        System.out.println("Item deletado com sucesso.");
    }

    public Item buscarItemPorId(int id) {
        for (Item item : itens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    // --- 5. MÉTODOS DE LÓGICA DE NEGÓCIO (Empréstimo/Devolução) ---

    public void realizarEmprestimo(Leitor leitor, Item item) {
        Emprestimo emp = new Emprestimo(leitor, item);
        emprestimos.add(emp);
        System.out.println("Empréstimo realizado com sucesso!");
        emp.exibirInfo();
        salvarDados();
    }

    public void realizarDevolucao(Emprestimo emprestimo) {
        emprestimo.devolver();
        salvarDados();
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

    public ArrayList<Emprestimo> getEmprestimosAtivos(Leitor leitor) {
        ArrayList<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : emprestimos) {
            if (emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                ativos.add(emp);
            }
        }
        return ativos;
    }

    public Emprestimo getEmprestimoAtivoPorId(int id, Leitor leitor) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getId() == id && emp.getLeitor().equals(leitor) && !emp.isDevolvido()) {
                return emp;
            }
        }
        return null;
    }

    // --- 6. MÉTODOS DE PERSISTÊNCIA E SISTEMA ---
    public void resetarDados() {
        this.leitores.clear();
        this.itens.clear();
        this.emprestimos.clear();
        this.autores.clear();
        this.categorias.clear();

        Item.setContadorID(0);
        Leitor.setContadorLeitor(0);
        Emprestimo.setContadorID(0);
        Autor.setContadorID(0);
        Categoria.setContadorID(0);

        salvarDados();
        System.out.println(">>> DADOS DA BIBLIOTECA RESETADOS COM SUCESSO! <<<");
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            oos.writeInt(Item.getContadorID());
            oos.writeInt(Leitor.getContadorLeitor());
            oos.writeInt(Emprestimo.getContadorID());
            oos.writeInt(Autor.getContadorID());
            oos.writeInt(Categoria.getContadorID());
            oos.writeObject(leitores);
            oos.writeObject(itens);
            oos.writeObject(emprestimos);
            oos.writeObject(autores);
            oos.writeObject(categorias);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() {
        File arquivo = new File("biblioteca.dat");
        if (!arquivo.exists()) {
            System.out.println("Arquivo de dados não encontrado. Começando com novos dados.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Item.setContadorID(ois.readInt());
            Leitor.setContadorLeitor(ois.readInt());
            Emprestimo.setContadorID(ois.readInt());
            Autor.setContadorID(ois.readInt());
            Categoria.setContadorID(ois.readInt());
            this.leitores = (ArrayList<Leitor>) ois.readObject();
            this.itens = (ArrayList<Item>) ois.readObject();
            this.emprestimos = (ArrayList<Emprestimo>) ois.readObject();
            this.autores = (ArrayList<Autor>) ois.readObject();
            this.categorias = (ArrayList<Categoria>) ois.readObject();
            System.out.println("Dados carregados com sucesso de biblioteca.dat");
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            this.leitores = new ArrayList<>();
            this.itens = new ArrayList<>();
            this.emprestimos = new ArrayList<>();
            this.autores = new ArrayList<>();
            this.categorias = new ArrayList<>();
        }
    }

    // --- 8. MÉTODOS SOBRESCRITOS
    @Override
    public void exibirInfo() {
        System.out.println("--- Informacoes Bibliotecario ---");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Status: Gerenciando " + leitores.size() + " leitores e " + itens.size() + " itens.");
    }
}