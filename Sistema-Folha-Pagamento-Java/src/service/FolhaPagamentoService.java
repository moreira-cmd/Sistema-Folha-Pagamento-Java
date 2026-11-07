package service;

import model.Funcionario;
import model.FolhaPagamento;
import repository.DadosRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FolhaPagamentoService {
    private static final BigDecimal DIVISOR_MENSAL = new BigDecimal("220");
    private static final BigDecimal ADICIONAL_HORA_EXTRA = new BigDecimal("1.5");

    private final List<FolhaPagamento> folhas;
    private final DadosRepository repository;

    public FolhaPagamentoService(DadosRepository repository) {
        this.repository = repository;
        this.folhas = repository.carregarFolhas();
    }

    public FolhaPagamento gerar(Funcionario funcionario, String competencia, BigDecimal quantidadeHorasExtras,
                                BigDecimal adicionais, BigDecimal quantidadeHorasFaltas, BigDecimal outrosDescontos) {
        if (!funcionario.isAtivo()) {
            throw new IllegalArgumentException("Não é possível gerar folha para funcionário inativo.");
        }

        if (competencia == null || !competencia.matches("(0[1-9]|1[0-2])/\\d{4}")) {
            throw new IllegalArgumentException("Competência inválida. Utilize MM/AAAA.");
        }

        validarNaoNegativo(quantidadeHorasExtras, "Horas extras");
        validarNaoNegativo(adicionais, "Adicionais");
        validarNaoNegativo(quantidadeHorasFaltas, "Horas de faltas");
        validarNaoNegativo(outrosDescontos, "Outros descontos");

        boolean jaExiste = folhas.stream().anyMatch(f ->
                f.getMatriculaFuncionario() == funcionario.getMatricula()
                        && f.getCompetencia().equals(competencia));
        if (jaExiste) {
            throw new IllegalArgumentException("Já existe uma folha para esse funcionário nessa competência.");
        }

        BigDecimal valorHora = funcionario.getSalarioBase().divide(DIVISOR_MENSAL, 4, RoundingMode.HALF_UP);
        BigDecimal valorHoraExtra = valorHora.multiply(ADICIONAL_HORA_EXTRA);
        BigDecimal totalHorasExtras = valorHoraExtra.multiply(quantidadeHorasExtras).setScale(2, RoundingMode.HALF_UP);
        BigDecimal descontoFaltas = valorHora.multiply(quantidadeHorasFaltas).setScale(2, RoundingMode.HALF_UP);

        FolhaPagamento folha = new FolhaPagamento(
                funcionario, competencia, totalHorasExtras, adicionais, descontoFaltas, outrosDescontos
        );

        if (folha.getTotalDescontos().compareTo(folha.getSalarioBruto()) > 0) {
            throw new IllegalArgumentException("O total de descontos não pode superar o salário bruto.");
        }

        folhas.add(folha);
        repository.salvarFolhas(folhas);
        return folha;
    }

    public List<FolhaPagamento> listar() {
        return List.copyOf(folhas);
    }

    private void validarNaoNegativo(BigDecimal valor, String campo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(campo + " não pode ser negativo.");
        }
    }
}
