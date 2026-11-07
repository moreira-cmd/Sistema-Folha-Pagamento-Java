package repository;

import model.Funcionario;
import model.FolhaPagamento;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DadosRepository {
    private final Path pasta = Path.of("dados");
    private final Path arquivoFuncionarios = pasta.resolve("funcionarios.dat");
    private final Path arquivoFolhas = pasta.resolve("folhas.dat");

    public DadosRepository() {
        try {
            Files.createDirectories(pasta);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível criar a pasta de dados.", e);
        }
    }

    public List<Funcionario> carregarFuncionarios() {
        return carregar(arquivoFuncionarios);
    }

    public List<FolhaPagamento> carregarFolhas() {
        return carregar(arquivoFolhas);
    }

    public void salvarFuncionarios(List<Funcionario> funcionarios) {
        salvar(arquivoFuncionarios, funcionarios);
    }

    public void salvarFolhas(List<FolhaPagamento> folhas) {
        salvar(arquivoFolhas, folhas);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> carregar(Path arquivo) {
        if (!Files.exists(arquivo)) {
            return new ArrayList<>();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(arquivo))) {
            return (List<T>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: não foi possível carregar " + arquivo + ". Uma lista vazia será utilizada.");
            return new ArrayList<>();
        }
    }

    private void salvar(Path arquivo, Object dados) {
        try (ObjectOutputStream saida = new ObjectOutputStream(Files.newOutputStream(arquivo))) {
            saida.writeObject(dados);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao salvar os dados.", e);
        }
    }
}
