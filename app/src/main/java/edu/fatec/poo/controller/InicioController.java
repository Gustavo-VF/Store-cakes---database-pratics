package edu.fatec.poo.controller;

import edu.fatec.poo.model.Produto;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioController {

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();
    private StringProperty pesquisa = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void Pesquisar() {

    }

    public ObservableList<Produto> getProdutos() {
        return produtos;
    }

    public void CarregarProdutos() {
        try {
            var resProdutos = SqlDaoFactory.getProdutoDao().searchAll();
            resProdutos.ifPresent(lista -> produtos.setAll(lista));

        } catch (Exception e) {
            mensagem.set("Erro ao carregar oprodutos");
            e.printStackTrace();
        }

    }

    public StringProperty pesquisaProperty() {
        return pesquisa;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

}
