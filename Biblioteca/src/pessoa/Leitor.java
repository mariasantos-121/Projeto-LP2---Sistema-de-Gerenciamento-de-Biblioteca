package pessoa;

public class Leitor extends Pessoa {
    private static int contadorLeitor = 0;
    private String cpf;

    public Leitor(String nome, String cpf) {
        super(nome, contadorLeitor++);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
