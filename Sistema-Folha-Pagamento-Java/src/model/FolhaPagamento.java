package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class FolhaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int matriculaFuncionario;
    private final String nomeFuncionario;
    private final String competencia;
    private final LocalDate dataGeracao;
    private final BigDecimal salarioBase;
    private final BigDecimal horasExtras;
    private final BigDecimal adicionais;
    private final BigDecimal faltas;
    private final BigDecimal outrosDescontos;

    public FolhaPagamento(Funcionario funcionario, String competencia, BigDecimal horasExtras,
                          BigDecimal adicionais, BigDecimal faltas, BigDecimal outrosDescontos) {
        this.matriculaFuncionario = funcionario.getMatricula();
        this.nomeFuncionario = funcionario.getNome();
        this.competencia = competencia;
        this.dataGeracao = LocalDate.now();
        this.salarioBase = funcionario.getSalarioBase();
        this.horasExtras = horasExtras;
        this.adicionais = adicionais;
        this.faltas = faltas;
        this.outrosDescontos = outrosDescontos;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBase.add(horasExtras).add(adicionais).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalDescontos() {
        return faltas.add(outrosDescontos).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSalarioLiquido() {
        return getSalarioBruto().subtract(getTotalDescontos()).setScale(2, RoundingMode.HALF_UP);
    }

    public int getMatriculaFuncionario() { return matriculaFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public String getCompetencia() { return competencia; }
    public LocalDate getDataGeracao() { return dataGeracao; }
    public BigDecimal getSalarioBase() { return salarioBase; }
    public BigDecimal getHorasExtras() { return horasExtras; }
    public BigDecimal getAdicionais() { return adicionais; }
    public BigDecimal getFaltas() { return faltas; }
    public BigDecimal getOutrosDescontos() { return outrosDescontos; }

    @Override
    public String toString() {
        return String.format("Matrícula: %d | Funcionário: %s | Competência: %s | Líquido: R$ %s",
                matriculaFuncionario, nomeFuncionario, competencia, getSalarioLiquido().toPlainString());
    }
}
