package pessoa;

import java.util.Scanner;
import java.util.ArrayList;

public class Bibliotecario extends Pessoa {

    Scanner sc = new Scanner(System.in);
    private ArrayList<Leitor> leitores; //lista de leitores cadastrados

    public Bibliotecario(String nome) {
        super(nome); // chama o construtor da classe pessoa.Pessoa
        this.leitores = new ArrayList<>(); //inicializa lista de leitores cadastrados
    }

    //Cadastrar novo leitor
    public void cadLeitor(){
        System.out.println("Digite o nome do Leitor ---");
        String nome = sc.nextLine();

        Leitor leitor = new Leitor(nome);
        leitores.add(leitor);
        System.out.println("Leitor cadastrado com sucesso! ID: " + leitor.getId());
    }

    //Listar todos os Leitores cadastrados
    public void listLeitores(){
        if(leitores.isEmpty()){
            System.out.println("Ainda não há leitores cadastrados.");
            return;
        }
        for (int i = 0; i < leitores.size(); i++) {
            Leitor l = leitores.get(i);
            System.out.println("ID: " + l.getId() + " | Nome: " + l.getNome()); // Alterar o Layout do output
            }
    }

    //Buscar Leitor pelo ID fixo
    public Leitor buscaID (int id){
        for (int i = 0; i < leitores.size(); i++) {
            Leitor leitor = leitores.get(i);
            if(leitor.getId() == id){
                System.out.println("ID: " + leitor.getId() + " | Nome: " + leitor.getNome());
                return leitor;
            }
            else{
                System.out.println("ID não encontrado no sistema");
            }
        }
        return null;
    }

    //Editar nome Leitor (podemos aumentar essa função para alterar outros dados)
    public void editLeitor(int id){
        Leitor leitor = buscaID(id);

        if (leitor != null) {
            System.out.println("Digite o novo nome:");
            String novoNome = sc.nextLine();
            leitor.setNome(novoNome);
            System.out.println("Nome atualizado com sucesso!");
        }
    }


    //Remover Leitor pelo ID
}
