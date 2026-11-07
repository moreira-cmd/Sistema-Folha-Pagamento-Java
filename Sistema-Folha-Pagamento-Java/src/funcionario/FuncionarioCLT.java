package funcionario;

public class FuncionarioCLT extends Funcionario {
    private static final double DESCONTO_INSS = 0.11;

    public FuncionarioCLT(int matricula, String nome, String cargo, double salario) {
        super(matricula, nome, cargo, salario);
    }

    public double getDescontoInss() {
        return getSalario() * DESCONTO_INSS;
    }

    @Override
    public String getTipo() {
        return "CLT";
    }

    @Override
    public double calcularSalarioFinal() {
        return getSalario() - getDescontoInss();
    }
}
