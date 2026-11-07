package service;

import model.Funcionario;
import repository.DadosRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class FuncionarioService {
    private final List<Funcionario> funcionarios;
    private final DadosRepository repository;
    private int proximaMatricula;

    public FuncionarioService(DadosRepository repository) {
        this.repository = repository;
        this.funcionarios = repository.carregarFuncionarios();
        this.proximaMatricula = funcionarios.stream()
                .mapToInt(Funcionario::getMatricula)
                .max().orElse(0) + 1;
    }

    public Funcionario cadastrar(String nome, String cpf, String cargo, BigDecimal salario) {
        validarTexto(nome, "Nome");
        validarTexto(cpf, "CPF");
        validarTexto(cargo, "Cargo");
        validarSalario(salario);

        boolean cpfExiste = funcionarios.stream().anyMatch(f -> f.getCpf().equals(cpf));
        if (cpfExiste) {
            throw new IllegalArgumentException("Já existe um funcionário com esse CPF.");
        }

        Funcionario funcionario = new Funcionario(proximaMatricula++, nome, cpf, cargo, salario);
        funcionarios.add(funcionario);
        salvar();
        return funcionario;
    }

    public List<Funcionario> listar() {
        return funcionarios.stream()
                .sorted(Comparator.comparingInt(Funcionario::getMatricula))
                .toList();
    }

    public Funcionario buscar(int matricula) {
        return funcionarios.stream()
                .filter(f -> f.getMatricula() == matricula)
                .findFirst()
                .orElse(null);
    }

    public void atualizar(int matricula, String nome, String cpf, String cargo, BigDecimal salario) {
        Funcionario funcionario = exigirFuncionario(matricula);
        validarTexto(nome, "Nome");
        validarTexto(cpf, "CPF");
        validarTexto(cargo, "Cargo");
        validarSalario(salario);

        boolean cpfDuplicado = funcionarios.stream()
                .anyMatch(f -> f.getMatricula() != matricula && f.getCpf().equals(cpf));
        if (cpfDuplicado) {
            throw new IllegalArgumentException("Outro funcionário já utiliza esse CPF.");
        }

        funcionario.atualizar(nome, cpf, cargo, salario);
        salvar();
    }

    public void alterarStatus(int matricula, boolean ativo) {
        Funcionario funcionario = exigirFuncionario(matricula);
        funcionario.setAtivo(ativo);
        salvar();
    }

    public Funcionario exigirFuncionario(int matricula) {
        Funcionario funcionario = buscar(matricula);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        return funcionario;
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode ficar vazio.");
        }
    }

    private void validarSalario(BigDecimal salario) {
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }
    }

    private void salvar() {
        repository.salvarFuncionarios(funcionarios);
    }
}
