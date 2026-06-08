package edu.fatec.poo.controller;

import java.util.UUID;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Role;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.ClienteSqlImpl;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CadastroController {

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty senha = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty confirmarSenha = new SimpleStringProperty("");
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty numero = new SimpleStringProperty("");
    private StringProperty complemento = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");
    private StringProperty cep = new SimpleStringProperty("");

    public void CadastrarCliente() {
        if (nome.get().isEmpty()) {
            mensagem.set("Preencha o Nome ");
            return;
        }

        if (email.get().isEmpty()) {
            mensagem.set("Email invalido");
            return;
        }

        if (senha.get().isEmpty()) {
            mensagem.set("Senha invalida");
            return;
        }

        if (confirmarSenha.get().isEmpty()) {
            mensagem.set("Confirme a senha");
            return;
        }

        if (!senha.get().equals(confirmarSenha.get())) {
            mensagem.set("As senhas estao divergentes entre si");
        }

        if (telefone.get().isEmpty()) {
            mensagem.set("Preencha o Telefone ");
            return;
        }

        if (endereco.get().isEmpty()) {
            mensagem.set("Preencha o endereco ");
            return;
        }

        if (numero.get().isEmpty()) {
            mensagem.set("Preencha o Numero ");
            return;
        }

        if (cep.get().isEmpty()) {
            mensagem.set("Preencha o CEP ");
            return;
        }

        try {
            var resultado = SqlDaoFactory.getClienteDao().findByEmail(email.get());

            // verifica se email est acadastrado no bd
            if (resultado.isPresent()) {
                mensagem.set("E-mail: " + email.get() +
                        " email ja possui um cadastro cadastrado.");
                return;
            }

            Cliente novoCliente = new Cliente();
            novoCliente.setId(UUID.randomUUID());
            novoCliente.setNome(nome.get());
            novoCliente.setEmail(email.get());
            novoCliente.setSenha(senha.get());
            if (!endereco.get().isEmpty()) {
                novoCliente.setEnderecoLogradouro(endereco.get());
            }
            if (!cep.get().isEmpty()) {
                novoCliente.setEnderecoCep(cep.get());
            }
            if (!complemento.get().isEmpty()) {
                novoCliente.setEnderecoComplemento(complemento.get());
            }
            if (!numero.get().isEmpty()) {
                novoCliente.setEnderecoNum(Integer.parseInt(numero.get()));
            }
            novoCliente.setRole(Role.COMPRADOR);

            ClienteSqlImpl ClienteSQL = new ClienteSqlImpl();

            var resultado2 = ClienteSQL.add(novoCliente);
            if (resultado2.isPresent()) {
                mensagem.set("Cadastro Realizado!");
            } else {
                mensagem.set("ERRO ao Cadastrar");
            }
        } catch (

        Exception e) {
            mensagem.set("Erro ao conectar no BD.");
            e.printStackTrace();
        }

    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty confirmarSenhaProperty() {
        return confirmarSenha;
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public StringProperty numeroProperty() {
        return numero;
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

    public StringProperty cepProperty() {
        return cep;
    }

}
