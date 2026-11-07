package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int matricula;
    private String nome;
    private String cpf;
    private String cargo;
    private BigDecimal salarioBase;
    private boolean ativo;

    public Funcionario(int matricula, String nome, String cpf, String cargo, BigDecimal salarioBase) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.ativo = true;
    }

    public int getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getCargo() { return cargo; }
    public BigDecimal getSalarioBase() { return salarioBase; }
    public boolean isAtivo() { return ativo; }

    public void atualizar(String nome, String cpf, String cargo, BigDecimal salarioBase) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
    }

    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return String.format("Matrícula: %d | Nome: %s | CPF: %s | Cargo: %s | Salário: R$ %s | Status: %s",
                matricula, nome, cpf, cargo, salarioBase.toPlainString(), ativo ? "Ativo" : "Inativo");
    }
}
