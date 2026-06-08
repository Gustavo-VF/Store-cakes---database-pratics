package edu.fatec.poo.controller;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.Carrinho;
import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import edu.fatec.poo.view.InicioView;

public class LoginController {

    private StringProperty email = new SimpleStringProperty("");
    private StringProperty senha = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void fazerLogin() {
        try {
            var resultado = SqlDaoFactory.getClienteDao().findByEmail(email.get());

            if (resultado.isEmpty()) {
                mensagem.set("E-mail: " + email.get() + " não cadastrado.");
                return;
            }
            Cliente cliente = resultado.get();

            if (!cliente.getSenha().equals(senha.get())) {
                mensagem.set("Senha incorreta.");
                return;
            }

            Contexto.setClienteLogado(cliente);

            var carrinhoR = SqlDaoFactory.getCarrinhoDAO().findByCliente(cliente);

            if (carrinhoR.isPresent()) {

                Contexto.setCarrinhoAtivo(carrinhoR.get());

            } else {
                Carrinho novCarrinho = new Carrinho();
                novCarrinho.setId(UUID.randomUUID());
                novCarrinho.setCliente(cliente);
                var novoCarrinho = SqlDaoFactory.getCarrinhoDAO().add(novCarrinho);
                Contexto.setCarrinhoAtivo(novCarrinho);
            }
            Contexto.chamaOutraTela(new InicioView(), "Inicio");

        } catch (Exception e) {
            mensagem.set("Erro ao conectar no BD.");
            e.printStackTrace();
        }
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }
}