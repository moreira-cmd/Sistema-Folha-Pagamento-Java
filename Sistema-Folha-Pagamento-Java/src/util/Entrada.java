package util;

import java.math.BigDecimal;
import java.util.Scanner;

public class Entrada {
    private final Scanner scanner;

    public Entrada(Scanner scanner) {
        this.scanner = scanner;
    }

    public String texto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public int inteiro(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public BigDecimal decimal(String mensagem) {
        while (true) {
            try {
                String valor = texto(mensagem).replace(",", ".");
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }
}
