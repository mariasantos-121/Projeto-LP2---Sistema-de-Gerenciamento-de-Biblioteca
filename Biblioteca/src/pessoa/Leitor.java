package pessoa;

public class Leitor extends Pessoa {
    private static int contadorLeitor = 0;
    private String cpf;

    public Leitor(String nome, String cpf) {
        super(nome, ++contadorLeitor);
        this.cpf = cpf;
    }

    public static int getContadorLeitor() {return contadorLeitor;}

    public static void setContadorLeitor(int c) {contadorLeitor = c;}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public void exibirInfo(){
        System.out.println("--- Informacoes do Leitor ---");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
    }
}
