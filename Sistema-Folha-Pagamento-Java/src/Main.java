import model.Funcionario;
import model.FolhaPagamento;
import repository.DadosRepository;
import service.FuncionarioService;
import service.FolhaPagamentoService;
import util.Entrada;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final Entrada entrada = new Entrada(scanner);
    private final DadosRepository repository = new DadosRepository();
    private final FuncionarioService funcionarioService = new FuncionarioService(repository);
    private final FolhaPagamentoService folhaService = new FolhaPagamentoService(repository);

    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = entrada.inteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarFuncionario();
                    case 2 -> listarFuncionarios();
                    case 3 -> consultarFuncionario();
                    case 4 -> atualizarFuncionario();
                    case 5 -> alterarStatusFuncionario();
                    case 6 -> gerarFolha();
                    case 7 -> listarFolhas();
                    case 0 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("       SISTEMA DE FOLHA DE PAGAMENTO");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar funcionário");
        System.out.println("2 - Listar funcionários");
        System.out.println("3 - Consultar funcionário");
        System.out.println("4 - Atualizar funcionário");
        System.out.println("5 - Ativar/desativar funcionário");
        System.out.println("6 - Gerar folha de pagamento");
        System.out.println("7 - Consultar folhas geradas");
        System.out.println("0 - Sair");
        System.out.println("========================================");
    }

    private void cadastrarFuncionario() {
        System.out.println("\n--- CADASTRO DE FUNCIONÁRIO ---");
        String nome = entrada.texto("Nome: ");
        String cpf = entrada.texto("CPF: ");
        String cargo = entrada.texto("Cargo: ");
        BigDecimal salario = entrada.decimal("Salário base: R$ ");

        Funcionario funcionario = funcionarioService.cadastrar(nome, cpf, cargo, salario);
        System.out.println("Funcionário cadastrado. Matrícula: " + funcionario.getMatricula());
    }

    private void listarFuncionarios() {
        System.out.println("\n--- FUNCIONÁRIOS ---");
        if (funcionarioService.listar().isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        funcionarioService.listar().forEach(System.out::println);
    }

    private void consultarFuncionario() {
        int matricula = entrada.inteiro("Matrícula: ");
        Funcionario funcionario = funcionarioService.exigirFuncionario(matricula);
        System.out.println(funcionario);
    }

    private void atualizarFuncionario() {
        int matricula = entrada.inteiro("Matrícula do funcionário: ");
        Funcionario atual = funcionarioService.exigirFuncionario(matricula);

        System.out.println("Dados atuais: " + atual);
        String nome = entrada.texto("Novo nome: ");
        String cpf = entrada.texto("Novo CPF: ");
        String cargo = entrada.texto("Novo cargo: ");
        BigDecimal salario = entrada.decimal("Novo salário base: R$ ");

        funcionarioService.atualizar(matricula, nome, cpf, cargo, salario);
        System.out.println("Cadastro atualizado.");
    }

    private void alterarStatusFuncionario() {
        int matricula = entrada.inteiro("Matrícula: ");
        Funcionario funcionario = funcionarioService.exigirFuncionario(matricula);
        System.out.println("1 - Ativar");
        System.out.println("2 - Desativar");
        int opcao = entrada.inteiro("Escolha: ");
        if (opcao != 1 && opcao != 2) {
            System.out.println("Opção inválida.");
            return;
        }
        funcionarioService.alterarStatus(matricula, opcao == 1);
        System.out.println("Status atualizado.");
    }

    private void gerarFolha() {
        int matricula = entrada.inteiro("Matrícula do funcionário: ");
        Funcionario funcionario = funcionarioService.exigirFuncionario(matricula);

        String competencia = entrada.texto("Competência (MM/AAAA): ");
        BigDecimal horasExtras = entrada.decimal("Quantidade de horas extras: ");
        BigDecimal adicionais = entrada.decimal("Outros adicionais (R$): ");
        BigDecimal horasFaltas = entrada.decimal("Quantidade de horas de faltas: ");
        BigDecimal outrosDescontos = entrada.decimal("Outros descontos (R$): ");

        FolhaPagamento folha = folhaService.gerar(
                funcionario, competencia, horasExtras, adicionais, horasFaltas, outrosDescontos
        );
        exibirFolha(folha);
    }

    private void listarFolhas() {
        System.out.println("\n--- HISTÓRICO DE FOLHAS ---");
        if (folhaService.listar().isEmpty()) {
            System.out.println("Nenhuma folha gerada.");
            return;
        }
        folhaService.listar().forEach(f -> {
            System.out.println(f);
            System.out.println("----------------------------------------");
        });
    }

    private void exibirFolha(FolhaPagamento folha) {
        System.out.println("\n========== DEMONSTRATIVO DE PAGAMENTO ==========");
        System.out.println("Funcionário: " + folha.getNomeFuncionario());
        System.out.println("Matrícula: " + folha.getMatriculaFuncionario());
        System.out.println("Competência: " + folha.getCompetencia());
        System.out.println("Data de geração: " + folha.getDataGeracao());
        System.out.println("-----------------------------------------------");
        imprimirValor("Salário base", folha.getSalarioBase());
        imprimirValor("Horas extras", folha.getHorasExtras());
        imprimirValor("Adicionais", folha.getAdicionais());
        imprimirValor("Salário bruto", folha.getSalarioBruto());
        imprimirValor("Desconto por faltas", folha.getFaltas());
        imprimirValor("Outros descontos", folha.getOutrosDescontos());
        imprimirValor("Total de descontos", folha.getTotalDescontos());
        System.out.println("-----------------------------------------------");
        imprimirValor("SALÁRIO LÍQUIDO", folha.getSalarioLiquido());
        System.out.println("================================================");
    }

    private void imprimirValor(String descricao, BigDecimal valor) {
        System.out.printf("%-25s R$ %s%n", descricao + ":", valor.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }
}
