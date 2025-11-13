classDiagram
    %% --- Interface de Persistência ---
    class Serializable {
        <<Interface>>
    }

    %% --- Hierarquia Pessoa ---
    class Pessoa {
        <<Abstract>>
        -id: int
        -nome: String
        +getId(): int
        +getNome(): String
        +setNome(String): void
        +exibirInfo()*: void
    }
    Serializable <|.. Pessoa

    class Leitor {
        -cpf: String
        +getCpf(): String
        +setCpf(String): void
        +exibirInfo(): void
    }
    Pessoa <|-- Leitor

    class Bibliotecario {
        %% Classe de Controle, herda ID e Nome
        %% Não possui atributos de dados próprios
        +exibirInfo(): void
    }
    Pessoa <|-- Bibliotecario

    class Autor {
        -id: int
        -nome: String
        +getId(): int
        +getNome(): String
        +setNome(String): void
    }
    Serializable <|.. Autor

    %% --- Hierarquia Item ---
    class Item {
        <<Abstract>>
        -id: int
        -titulo: String
        -quantidadeExemplares: int
        -disponivel: boolean
        -categoria: Categoria
        +getId(): int
        +getTitulo(): String
        +setTitulo(String): void
        +getQuantidadeExemplares(): int
        +setQuantidadeExemplares(int): void
        +isDisponivel(): boolean
        +getCategoria(): Categoria
        +setCategoria(Categoria): void
        +exibirInfo()*: void
    }
    Serializable <|.. Item

    class Livro {
        -autor: Autor
        +getAutor(): Autor
        +setAutor(Autor): void
        +exibirInfo(): void
    }
    Item <|-- Livro

    class Revista {
        -editora: String
        +getEditora(): String
        +setEditora(String): void
        +exibirInfo(): void
    }
    Item <|-- Revista

    class Categoria {
        -id: int
        -nome: String
        +getId(): int
        +getNome(): String
        +setNome(String): void
    }
    Serializable <|.. Categoria

    %% --- Entidades Independentes ---
    class Emprestimo {
        -id: int
        -leitor: Leitor
        -item: Item
        -devolvido: boolean
        -dataPrevista: String
        +getId(): int
        +getLeitor(): Leitor
        +getItem(): Item
        +isDevolvido(): boolean
        +getDataPrevista(): String
        +setDataPrevista(String): void
        +devolver(): void
        +exibirInfo(): void
    }
    Serializable <|.. Emprestimo

    class Evento {
        -id: int
        -nome: String
        -data: String
        -local: String
        +getId(): int
        +getNome(): String
        +setNome(String): void
        +getData(): String
        +setData(String): void
        +getLocal(): String
        +setLocal(String): void
        +exibirInfo(): void
    }
    Serializable <|.. Evento
