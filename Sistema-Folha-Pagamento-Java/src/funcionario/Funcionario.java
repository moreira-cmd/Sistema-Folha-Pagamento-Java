package funcionario;

public class Funcionario {
    private int matricula;
    private String nome;
    private String cargo;
    private double salario; // salário base (CLT) ou valor da hora (PJ)

    public Funcionario(int matricula, String nome, String cargo, double salario) {
        this.matricula = matricula;
        setNome(nome);
        setCargo(cargo);
        setSalario(salario);
    }

    public int getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
    public double getSalario() { return salario; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo não pode ser vazio.");
        }
        this.cargo = cargo.trim();
    }

    public void setSalario(double salario) {
        if (salario <= 0) {
            throw new IllegalArgumentException("Salário deve ser maior que zero.");
        }
        this.salario = salario;
    }

    public String getTipo() {
        return "Funcionário";
    }

    // Cada tipo de vínculo sobrescreve este método
    public double calcularSalarioFinal() {
        return salario;
    }

    @Override
    public String toString() {
        return String.format("Matrícula: %d | %s | Nome: %s | Cargo: %s | Salário final: R$ %.2f",
                matricula, getTipo(), nome, cargo, calcularSalarioFinal());
    }
}