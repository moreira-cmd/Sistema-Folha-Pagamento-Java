package sistema;

import funcionario.FuncionarioCLT;
import funcionario.FuncionarioPJ;
import java.util.Scanner;
import pagamento.FolhaPagamento;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private FolhaPagamento folha = new FolhaPagamento();

    public void exibir() {

        int opcao;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Consultar funcionários");
            System.out.println("3 - Calcular folha de pagamento");
            System.out.println("0 - Sair");

            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {

                case 1:
                    cadastrar();
                    break;

                case 2:
                    System.out.println("\n--- Funcionários cadastrados ---");
                    folha.listar();
                    break;

                case 3:
                    System.out.println("\n--- Folha de pagamento ---");
                    folha.calcularFolha();
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n1 - CLT");
        System.out.println("2 - PJ");
        int tipo = lerInt("Tipo de vínculo: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido!");
            return;
        }

        String nome = lerTexto("Nome: ");
        String cargo = lerTexto("Cargo: ");
        int matricula = folha.gerarMatricula();

        if (tipo == 1) {
            double salario = lerDouble("Salário base: ");
            folha.adicionar(new FuncionarioCLT(matricula, nome, cargo, salario));
        } else {
            double valorHora = lerDouble("Valor da hora: ");
            int horas = lerInt("Horas trabalhadas: ");
            while (horas <= 0) {
                System.out.println("As horas devem ser maiores que zero.");
                horas = lerInt("Horas trabalhadas: ");
            }
            folha.adicionar(new FuncionarioPJ(matricula, nome, cargo, valorHora, horas));
        }

        System.out.println("Funcionário cadastrado! Matrícula: " + matricula);
    }

    // Métodos auxiliares de leitura com validação

    private String lerTexto(String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("O campo não pode ser vazio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                if (valor > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}