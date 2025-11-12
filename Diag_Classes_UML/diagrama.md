classDiagram
    direction LR

    class Pessoa {
        - String nome
        + getNome()
        + setNome()
        + getId()
    }

    class Leitor {
        - String cpf
        + getCpf()
        + setCpf()
        + exibirInfo()
    }

    class Bibliotecario {
        - ArrayList<Leitor> leitores
        - ArrayList<Autor> autores
        - ArrayList<Categoria> categorias
        - ArrayList<Item> itens
        - ArrayList<Emprestimo> emprestimos
        + cadLeitor()
        + listLeitores()
        + editarLeitorNome()
        + editarLeitorCPF()
        + deletarLeitor()
        + buscaID()
        + validarCPF()
        + cadastrarAutor()
        + listarAutores()
        + editarAutor()
        + deletarAutor()
        + buscarAutorPorId()
        + cadastrarCategoria()
        + listarCategorias()
        + editarCategoria()
        + deletarCategoria()
        + buscarCategoriaPorId()
        + addItem()
        + listarItens()
        + editarItem()
        + deletarItem()
        + buscarItemPorId()
        + realizarEmprestimo()
        + realizarDevolucao()
        + listarEmprestimos()
        + getEmprestimosAtivos()
        + getEmprestimoAtivoPorId()
        + resetarDados()
        + salvarDados()
        + carregarDados()
        + exibirInfo()
    }

    class Autor {
        - String nome
        + getId()
        + getNome()
        + setNome()
    }

    class Categoria {
        - String nome
        + getId()
        + getNome()
        + setNome()
    }

    class Item {
        - String titulo
        - int quantidadeExemplares
        - boolean disponivel
        - Categoria categoria
        + getCategoria()
        + setCategoria()
        + getId()
        + getTitulo()
        + setTitulo()
        + getQuantidadeExemplares()
        + setQuantidadeExemplares()
        + isDisponivel()
    }

    class Livro {
        - Autor autor
        + getAutor()
        + setAutor()
        + exibirInfo()
    }

    class Revista {
        - String editora
        + getEditora()
        + setEditora()
        + exibirInfo()
    }

    class Emprestimo {
        - Leitor leitor
        - Item item
        - boolean devolvido
        + getId()
        + getLeitor()
        + getItem()
        + isDevolvido()
        + devolver()
        + exibirInfo()
    }

    Pessoa <|-- Leitor
    Pessoa <|-- Bibliotecario
    Item <|-- Livro
    Item <|-- Revista
    Bibliotecario --> Leitor : gerencia
    Bibliotecario --> Autor : cadastra
    Bibliotecario --> Categoria : organiza
    Bibliotecario --> Item : controla
    Bibliotecario --> Emprestimo : realiza
    Item --> Categoria : pertence
    Livro --> Autor : escrito_por
    Emprestimo --> Leitor : pertence_a
    Emprestimo --> Item : refere_se
