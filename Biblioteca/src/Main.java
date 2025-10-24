import pessoa.Bibliotecario;
import pessoa.Leitor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Bibliotecario b = new Bibliotecario("nome"); // tem que ver esse objeto

        int opcao;
        do{
            System.out.println("--- MENU DO BIBLIOTECÁRIO ---");
            System.out.println("--- 1 - Criar um novo Leitor ---");
            System.out.println("--- 2 - Listar leitores cadastrados ---");
            System.out.println("--- 3 - Buscar leitor pelo ID ---");
            System.out.println("--- 4 - Alterar dados de cadastro ---");

            opcao = sc.nextInt();
            sc.nextLine();

            switch(opcao){
                case 1:
                    b.cadLeitor();
                    break;
                case 2:
                    b.listLeitores();
                    break;
                case 3:
                    System.out.println("Informe o ID do Leitor:");
                    int id = sc.nextInt();
                    b.buscaID(id);
                    break;
                case 4:
                    System.out.println("Informe o ID do Leitor:");
                    id = sc.nextInt();
                    b.editLeitor(id);
                    break;
            }
        }while(opcao != -1);
    }
}