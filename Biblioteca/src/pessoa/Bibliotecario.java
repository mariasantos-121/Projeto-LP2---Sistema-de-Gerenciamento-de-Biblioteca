package pessoa;

import emprestimo.Emprestimo;
import evento.Evento;
import item.*;
import util.Configuracao;
import java.util.ArrayList;
import java.io.*;

public class Bibliotecario extends Pessoa {

    private static int contadorBibliotecario = 1;
    private Configuracao config;
    private ArrayList<Leitor> leitores;
    private ArrayList<Autor> autores;
    private ArrayList<Categoria> categorias;
    private ArrayList<Item> itens;
    private ArrayList<Emprestimo> emprestimos;
    private ArrayList<Evento> eventos;
    private ArrayList<Editora> editoras;
    private ArrayList<Prateleira> prateleiras;

    public Bibliotecario(String nome) {
        super(nome, contadorBibliotecario++);
        this.leitores = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.editoras = new ArrayList<>();
        this.prateleiras = new ArrayList<>();
        this.config = new Configuracao();
    }

    // --- MÉTODOS DE LEITOR ---

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


    // --- MÉTODOS DE AUTOR ---

    public void cadastrarAutor(String nome) {
        Autor novoAutor = new Autor(nome);
        autores.add(novoAutor);
        System.out.println("Autor cadastrado com sucesso! ID: " + novoAutor.getId());
        salvarDados();
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


    // --- MÉTODOS DE CATEGORIA ---
    public void cadastrarCategoria(String nome) {
        Categoria novaCat = new Categoria(nome);
        categorias.add(novaCat);
        System.out.println("Categoria cadastrada com sucesso! ID: " + novaCat.getId());
        salvarDados();
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


    public void addItem(Item item) {
        itens.add(item);
        System.out.println("Item adicionado com sucesso! ID: " + item.getId());
        salvarDados();
    }

    public void editarItem(Item item, int opcao, String valor, Object obj) {
        switch (opcao) {
            case 1 -> item.setTitulo(valor);
            case 2 -> item.setQuantidadeExemplares(Integer.parseInt(valor));
            case 3 -> item.setCategoria((Categoria) obj);
            case 4 -> {
                // Opção 4: Autor (para Livro) ou Editora (para Revista)
                if (item instanceof Livro livro) {
                    livro.setAutor((Autor) obj);
                } else if (item instanceof Revista revista) {
                    revista.setEditora((Editora) obj); // <--- MUDANÇA AQUI
                }
            }
            case 5 -> item.setPrateleira((Prateleira) obj);
            case 6 -> {
                if (item instanceof Livro livro) {
                    livro.setEditora((Editora) obj);
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

    // --- MÉTODOS DE LÓGICA DE NEGÓCIO (Empréstimo/Devolução) ---

    public void realizarEmprestimo(Leitor leitor, Item item, String dataPrevista) {
        Emprestimo emp = new Emprestimo(leitor, item, dataPrevista);
        emprestimos.add(emp);
        System.out.println("Empréstimo realizado com sucesso!");
        emp.exibirInfo();
        salvarDados();
    }


    public void realizarDevolucao(Emprestimo emprestimo) {
        emprestimo.devolver();
        salvarDados();
    }

    public void renovarEmprestimo(Emprestimo emprestimo, String novaData) {
        emprestimo.setDataPrevista(novaData);
        System.out.println("Empréstimo renovado com sucesso!");
        emprestimo.exibirInfo();
        salvarDados();
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

    // --- MÉTODOS DE EVENTO---

    public void cadastrarEvento(String nome, String data, String local) {
        Evento novoEvento = new Evento(nome, data, local);
        eventos.add(novoEvento);
        System.out.println("Evento cadastrado com sucesso! ID: " + novoEvento.getId());
        salvarDados();
    }


    public void editarEvento(Evento evento, int opcao, String novoValor) {
        switch (opcao) {
            case 1 -> evento.setNome(novoValor);
            case 2 -> evento.setData(novoValor);
            case 3 -> evento.setLocal(novoValor);
            default -> System.out.println("Opção de edição inválida.");
        }
        System.out.println("Evento atualizado com sucesso!");
        salvarDados();
    }

    public void deletarEvento(Evento evento) {
        eventos.remove(evento);
        System.out.println("Evento deletado com sucesso.");
        salvarDados();
    }

    // --- MÉTODOS DE EDITORA ---
    public void cadastrarEditora(String nome) {
        Editora nova = new Editora(nome);
        editoras.add(nova);
        System.out.println("Editora cadastrada! ID: " + nova.getId());
        salvarDados();
    }

    public void editarEditora(Editora e, String novoNome) {
        e.setNome(novoNome);
        System.out.println("Editora atualizada!");
        salvarDados();
    }

    public void deletarEditora(Editora e) {
        editoras.remove(e); // Futuramente verificar dependencia com Revista
        System.out.println("Editora removida.");
        salvarDados();
    }


    // --- MÉTODOS DE PRATELEIRA ---
    public void cadastrarPrateleira(String local) {
        Prateleira nova = new Prateleira(local);
        prateleiras.add(nova);
        System.out.println("Prateleira cadastrada! ID: " + nova.getId());
        salvarDados();
    }
    public void editarPrateleira(Prateleira p, String novoLocal) {
        p.setLocalizacao(novoLocal);
        System.out.println("Prateleira atualizada!");
        salvarDados();
    }

    public void deletarPrateleira(Prateleira p) {
        prateleiras.remove(p);
        System.out.println("Prateleira removida.");
        salvarDados();
    }

    // ---------------------- LISTAR GENERICO ------------------------
    public void listarRegistros(ArrayList<? extends util.Exibivel> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        for (util.Exibivel item : lista) {
            item.exibirInfo();
            System.out.println("-------------------------------------------------");
        }
    }

    // --- MÉTODOS DE PERSISTÊNCIA E SISTEMA ---
    public void resetarDados() {
        this.leitores.clear();
        this.itens.clear();
        this.emprestimos.clear();
        this.autores.clear();
        this.categorias.clear();
        this.eventos.clear();
        this.editoras.clear();
        this.prateleiras.clear();

        Item.setContadorID(0);
        Leitor.setContadorLeitor(0);
        Emprestimo.setContadorID(0);
        Autor.setContadorID(0);
        Categoria.setContadorID(0);
        Evento.setContadorID(0);
        Editora.setContadorID(0);
        Prateleira.setContadorID(0);

        salvarDados();
        System.out.println(">>> DADOS DA BIBLIOTECA RESETADOS COM SUCESSO! <<<");
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            // 1. Salva Contadores
            oos.writeInt(Item.getContadorID());
            oos.writeInt(Leitor.getContadorLeitor());
            oos.writeInt(Emprestimo.getContadorID());
            oos.writeInt(Autor.getContadorID());
            oos.writeInt(Categoria.getContadorID());
            oos.writeInt(Evento.getContadorID());
            oos.writeInt(Editora.getContadorID());
            oos.writeInt(Prateleira.getContadorID());
            // 2. Salva Listas
            oos.writeObject(leitores);
            oos.writeObject(itens);
            oos.writeObject(emprestimos);
            oos.writeObject(autores);
            oos.writeObject(categorias);
            oos.writeObject(eventos);
            oos.writeObject(editoras);
            oos.writeObject(prateleiras);

        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() {
        carregarConfig();
        File arquivo = new File("biblioteca.dat");
        if (!arquivo.exists()) {
            System.out.println("Arquivo de dados não encontrado. Começando com novos dados.");
            this.cadastrarEvento("Clube do Livro", "Toda Terça às 19h", "Sala Principal");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            // 1. Carrega Contadores
            Item.setContadorID(ois.readInt());
            Leitor.setContadorLeitor(ois.readInt());
            Emprestimo.setContadorID(ois.readInt());
            Autor.setContadorID(ois.readInt());
            Categoria.setContadorID(ois.readInt());
            Evento.setContadorID(ois.readInt());
            Editora.setContadorID(ois.readInt());
            Prateleira.setContadorID(ois.readInt());

            // 2. Carrega Listas
            this.leitores = (ArrayList<Leitor>) ois.readObject();
            this.itens = (ArrayList<Item>) ois.readObject();
            this.emprestimos = (ArrayList<Emprestimo>) ois.readObject();
            this.autores = (ArrayList<Autor>) ois.readObject();
            this.categorias = (ArrayList<Categoria>) ois.readObject();
            this.eventos = (ArrayList<Evento>) ois.readObject();
            this.editoras = (ArrayList<Editora>) ois.readObject();
            this.prateleiras = (ArrayList<Prateleira>) ois.readObject();

            System.out.println("Dados carregados com sucesso de biblioteca.dat");
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            this.leitores = new ArrayList<>();
            this.itens = new ArrayList<>();
            this.emprestimos = new ArrayList<>();
            this.autores = new ArrayList<>();
            this.categorias = new ArrayList<>();
            this.eventos = new ArrayList<>();
            this.editoras = new ArrayList<>();
            this.prateleiras = new ArrayList<>();
        }
    }

    // --- GETTERS (Para a Main usar) ---
    public ArrayList<Leitor> getLeitores() { return leitores; }
    public ArrayList<Autor> getAutores() { return autores; }
    public ArrayList<Categoria> getCategorias() { return categorias; }
    public ArrayList<Item> getItens() { return itens; }
    public ArrayList<Emprestimo> getEmprestimos() { return emprestimos; }
    public ArrayList<Evento> getEventos() { return eventos; }
    public ArrayList<Editora> getEditoras() { return editoras; }
    public ArrayList<Prateleira> getPrateleiras() { return prateleiras; }

    // --- METODO GENERICO DE BUSCA ---
    public <T extends util.Identificavel> T buscarPorId(ArrayList<T> lista, int id) {
        for (T objeto : lista) {
            if (objeto.getId() == id) {
                return objeto;
            }
        }
        return null;
    }

    // =============================================================
    //       GERENCIAMENTO DE CONFIGURAÇÃO (PREFERÊNCIAS)
    // =============================================================

    public Configuracao getConfig() {
        return config;
    }

    public void atualizarConfig(String novoNome, String novoTema) {
        config.setNomeExibicao(novoNome);
        config.setTema(novoTema);
        salvarConfig(); // Salva imediatamente em arquivo separado
    }

    public void salvarConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("config.dat"))) {
            oos.writeObject(config);
        } catch (IOException e) {
            System.out.println("Erro ao salvar configurações: " + e.getMessage());
        }
    }

    public void carregarConfig() {
        File arquivo = new File("config.dat");
        if (!arquivo.exists()) return; // Se não existe, usa o padrão (criado no construtor)

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            this.config = (Configuracao) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao carregar configurações.");
        }
    }

    // --- MÉTODOS SOBRESCRITOS ---
    @Override
    public void exibirInfo() {
        System.out.println("--- Informacoes Bibliotecario ---");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Status: Gerenciando " + leitores.size() + " leitores e " + itens.size() + " itens.");
    }
}