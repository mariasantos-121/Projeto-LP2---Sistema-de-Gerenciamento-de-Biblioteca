classDiagram
    direction TB

    %% --- INTERFACES ---
    class Serializable {
        <<Interface>>
    }
    class Identificavel {
        <<Interface>>
        +getId() int
        +getNome() String
    }
    class Exibivel {
        <<Interface>>
        +exibirInfo() void
    }

    %% --- CLASSES DE SUPORTE ---
    class Main {
        <<View>>
        +main(args)
        -menuEntidade()
        -rotearCadastro()
    }
    
    class ConsoleUI {
        <<Utility>>
        +lerOpcao()
        +exibirCabecalho()
    }

    class Configuracao {
        -nomeExibicao: String
        -tema: String
    }
    Serializable <|.. Configuracao

    %% --- HIERARQUIA PESSOA ---
    class Pessoa {
        <<Abstract>>
        -id: int
        -nome: String
    }
    Serializable <|.. Pessoa
    Identificavel <|.. Pessoa
    Exibivel <|.. Pessoa

    class Leitor {
        -cpf: String
        -contadorLeitor: int
    }
    Pessoa <|-- Leitor

    class Bibliotecario {
        <<Controller>>
        -leitores: List
        -itens: List
        -emprestimos: List
        -autores: List
        -categorias: List
        -eventos: List
        -editoras: List
        -prateleiras: List
        -config: Configuracao
        +listarRegistros(lista)
        +buscarPorId(lista, id)
        +cadastrarEditora()
        +cadastrarPrateleira()
        +realizarEmprestimo()
        +salvarDados()
        +carregarDados()
    }
    Pessoa <|-- Bibliotecario

    %% --- HIERARQUIA ITEM ---
    class Item {
        <<Abstract>>
        -id: int
        -titulo: String
        -qtd: int
        -categoria: Categoria
        -prateleira: Prateleira
    }
    Serializable <|.. Item
    Identificavel <|.. Item
    Exibivel <|.. Item

    class Livro {
        -autor: Autor
        -editora: Editora
    }
    Item <|-- Livro

    class Revista {
        -editora: Editora
    }
    Item <|-- Revista

    %% --- ENTIDADES INDEPENDENTES ---
    class Autor {
        -id: int
        -nome: String
    }
    Serializable <|.. Autor
    Identificavel <|.. Autor
    Exibivel <|.. Autor

    class Categoria {
        -id: int
        -nome: String
    }
    Serializable <|.. Categoria
    Identificavel <|.. Categoria
    Exibivel <|.. Categoria

    class Evento {
        -id: int
        -nome: String
        -data: String
        -local: String
    }
    Serializable <|.. Evento
    Identificavel <|.. Evento
    Exibivel <|.. Evento

    class Editora {
        -id: int
        -nome: String
    }
    Serializable <|.. Editora
    Identificavel <|.. Editora
    Exibivel <|.. Editora

    class Prateleira {
        -id: int
        -localizacao: String
    }
    Serializable <|.. Prateleira
    Identificavel <|.. Prateleira
    Exibivel <|.. Prateleira

    class Emprestimo {
        -id: int
        -leitor: Leitor
        -item: Item
        -dataPrevista: String
        -devolvido: boolean
        +devolver()
        +renovar()
    }
    Serializable <|.. Emprestimo
    Identificavel <|.. Emprestimo
    Exibivel <|.. Emprestimo

    %% --- RELACIONAMENTOS ---
    
    %% Main usa Bibliotecario e ConsoleUI
    Main ..> Bibliotecario : usa
    Main ..> ConsoleUI : usa

    %% Bibliotecario compõe todas as listas
    Bibliotecario *-- Leitor
    Bibliotecario *-- Item
    Bibliotecario *-- Emprestimo
    Bibliotecario *-- Autor
    Bibliotecario *-- Categoria
    Bibliotecario *-- Evento
    Bibliotecario *-- Editora
    Bibliotecario *-- Prateleira
    Bibliotecario --> Configuracao : gerencia

    %% Associações de Item
    Item --> Categoria : tem
    Item --> Prateleira : tem
    Livro --> Autor : tem
    Livro --> Editora : tem
    Revista --> Editora : tem

    %% Associações de Empréstimo
    Emprestimo --> Leitor : referente a
    Emprestimo --> Item : referente a
