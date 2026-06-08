package edu.fatec.poo.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdutoController {

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty ingredientes = new SimpleStringProperty("");
    private StringProperty valor = new SimpleStringProperty("");
    private StringProperty quantidade = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void AdicionarProduto() {
        // lógica depois
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty ingredientesProperty() {
        return ingredientes;
    }

    public StringProperty valorProperty() {
        return valor;
    }

    public StringProperty quantidadeProperty() {
        return quantidade;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }
}