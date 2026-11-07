package pagamento;

import funcionario.Funcionario;
import java.util.ArrayList;

public class FolhaPagamento {
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private int proximaMatricula = 1;

    public int gerarMatricula() {
        return proximaMatricula++;
    }

    public void adicionar(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void listar() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }

    public void calcularFolha() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        double total = 0;
        for (Funcionario f : funcionarios) {
            System.out.printf("%s (%s) - R$ %.2f%n", f.getNome(), f.getTipo(), f.calcularSalarioFinal());
            total += f.calcularSalarioFinal();
        }
        System.out.printf("TOTAL DA FOLHA: R$ %.2f%n", total);
    }
}
