classDiagram
    direction LR

    class Pessoa {
        - String nome
    }

    class Leitor {
        - String cpf
    }

    class Autor {
        - String nome
        - String nacionalidade
    }

    class Bibliotecario {
        - Configuracao config
        - ArrayList~Leitor~ leitores
        - ArrayList~Autor~ autores
        - ArrayList~Categoria~ categorias
        - ArrayList~Item~ itens
        - ArrayList~Emprestimo~ emprestimos
        - ArrayList~Evento~ eventos
        - ArrayList~Editora~ editoras
        - ArrayList~Prateleira~ prateleiras
    }

    Pessoa <|-- Leitor
    Pessoa <|-- Bibliotecario


    class Item {
        - String titulo
        - boolean disponivel
    }

    class Livro {
        - Autor autor
    }

    class Revista {
        - Editora editora
    }

    class Categoria {
        - String nome
    }

    class Editora {
        - String nome
    }

    class Prateleira {
        - String identificacao
        - Categoria categoria
    }

    Item <|-- Livro
    Item <|-- Revista
    Livro --> Autor
    Revista --> Editora
    Item --> Categoria
    Prateleira --> Categoria

    class Emprestimo {
        - Leitor leitor
        - Item item
        - boolean devolvido
        - String dataPrevista
    }

    class Evento {
        - String nome
        - String data
        - String local
    }

    Emprestimo --> Item
    Emprestimo --> Leitor
    Bibliotecario --> Emprestimo
    Bibliotecario --> Evento

    class Configuracao {
        - String nomeExibicao
        - String tema
    }

    class ConsoleUI {

    }

    class Main {

    }

    Bibliotecario --> Configuracao
    Main --> ConsoleUI
    ConsoleUI --> Bibliotecario
