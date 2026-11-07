package funcionario;

public class FuncionarioPJ extends Funcionario {
    private int horasTrabalhadas;

    // No PJ, o parâmetro "salario" representa o valor da hora
    public FuncionarioPJ(int matricula, String nome, String cargo, double valorHora, int horasTrabalhadas) {
        super(matricula, nome, cargo, valorHora);
        setHorasTrabalhadas(horasTrabalhadas);
    }

    public int getHorasTrabalhadas() { return horasTrabalhadas; }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        if (horasTrabalhadas <= 0) {
            throw new IllegalArgumentException("Horas trabalhadas devem ser maiores que zero.");
        }
        this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    public String getTipo() {
        return "PJ";
    }

    @Override
    public double calcularSalarioFinal() {
        return getSalario() * horasTrabalhadas;
    }
}
