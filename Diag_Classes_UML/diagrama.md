classDiagram
class Emprestimo {
    - leitor
    - item
    - devolvido
    - dataPrevista
    - dataEmprestimo
    + getId()
    + getLeitor()
    + getItem()
    + isDevolvido()
    + getDataEmprestimo()
    + getDataPrevista()
    + devolver()
    + toString()
}
class Serializable

Serializable <|.. Emprestimo

class Evento {
    - nome
    - data
    - local
    + getId()
    + getNome()
    + setNome()
    + getData()
    + setData()
    + getLocal()
    + setLocal()
    + toString()
}
Serializable <|.. Evento

class Categoria {
    - nome
    + getId()
    + getNome()
    + setNome()
    + toString()
}
Serializable <|.. Categoria

class Item {
    - titulo
    - quantidadeExemplares
    - disponivel
    - categoria
    + getId()
    + getTitulo()
    + setTitulo()
    + getQuantidadeExemplares()
    + setQuantidadeExemplares()
    + isDisponivel()
    + setDisponivel()
    + getCategoria()
    + setCategoria()
    + toString()
}
Serializable <|.. Item

class Livro {
    - autor
    - isbn
    + getAutor()
    + setAutor()
    + getIsbn()
    + setIsbn()
    + toString()
}
Item <|-- Livro

class Revista {
    - edicao
    - periodicidade
    + getEdicao()
    + setEdicao()
    + getPeriodicidade()
    + setPeriodicidade()
    + toString()
}
Item <|-- Revista

class Pessoa {
    - nome
    - cpf
    - telefone
    + getNome()
    + setNome()
    + getCpf()
    + setCpf()
    + getTelefone()
    + setTelefone()
    + toString()
}
Serializable <|.. Pessoa

class Autor {
    - nacionalidade
    + getNacionalidade()
    + setNacionalidade()
    + toString()
}
Serializable <|.. Autor

class Bibliotecario {
    - matricula
    + getMatricula()
    + setMatricula()
    + registrarEmprestimo()
    + registrarDevolucao()
    + toString()
}
Pessoa <|-- Bibliotecario

class Leitor {
    - matricula
    - emprestimos
    + getMatricula()
    + setMatricula()
    + getEmprestimos()
    + adicionarEmprestimo()
    + toString()
}
Pessoa <|-- Leitor
