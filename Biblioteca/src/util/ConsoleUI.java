package util;

import java.util.Scanner;

public class ConsoleUI {

    public static void exibirCabecalho(Configuracao config) {
        System.out.println("\n========================================");
        System.out.println(" Bem-vindo, " + config.getNomeExibicao() + "!");
        System.out.println(" Tema Atual: " + config.getTema());
        System.out.println("========================================");
    }

    public static void exibirMenuPrincipal() {
        System.out.println("1 - Menu de Leitores");
        System.out.println("2 - Menu de Itens (Livros/Revistas)");
        System.out.println("3 - Menu de Empréstimos");
        System.out.println("4 - Menu de Autores");
        System.out.println("5 - Menu de Categorias");
        System.out.println("6 - Menu de Eventos");
        System.out.println("7 - Menu de Editoras");
        System.out.println("8 - Menu de Prateleiras");
        System.out.println("9 - Configurações");
        System.out.println("10 - [RESETAR DADOS]");
        System.out.println("0 - Sair");
    }

    public static void exibirMenuEntidade(String tipo) {
        System.out.println("\n--- MENU DE " + tipo + " ---");
        System.out.println("1 - Cadastrar " + formatarTitulo(tipo));
        System.out.println("2 - Listar " + tipo);
        System.out.println("3 - Editar " + formatarTitulo(tipo));
        System.out.println("4 - Deletar " + formatarTitulo(tipo));
        System.out.println("0 - Voltar");
    }

    public static String formatarTitulo(String s) {
        if (s.endsWith("ES")) return s.substring(0, s.length() - 2).toLowerCase();
        if (s.endsWith("S")) return s.substring(0, s.length() - 1).toLowerCase();
        return s.toLowerCase();
    }

    public static boolean confirmar(Scanner sc) {
        System.out.print("TEM CERTEZA? (s/n): ");
        return sc.nextLine().equalsIgnoreCase("s");
    }

    public static boolean checkCancel(String input) {
        if (input.equalsIgnoreCase("c")) {
            System.out.println("Operação cancelada.");
            return true;
        }
        return false;
    }

    public static int lerOpcao(Scanner sc, int min, int max) {
        while (true) {
            System.out.print("Escolha: ");
            try {
                int opcao = Integer.parseInt(sc.nextLine());
                if (opcao >= min && opcao <= max) return opcao;
                System.out.println("Opção inválida.");
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }

    public static Integer lerInteiroCancelavel(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("c")) return null;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

}