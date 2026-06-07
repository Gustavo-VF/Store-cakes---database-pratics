package edu.fatec.poo.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MinhaContaController {

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty cep = new SimpleStringProperty("");
    private StringProperty numero = new SimpleStringProperty("");
    private StringProperty complemento = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void CarregarDados() {
        // carregar do Contexto.getClienteLogado() depois
    }

    public void Editar() {
    }

    public void Excluir() {
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public StringProperty cepProperty() {
        return cep;
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
}