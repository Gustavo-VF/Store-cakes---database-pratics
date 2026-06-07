package edu.fatec.poo.controller;

import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioController {

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();
    private StringProperty pesquisa = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty(null);

    public void Pesquisar() {

    }

    public ObservableList<Produto> getProdutos() {
        return produtos;
    }

    public void CarregarProdutos() {
        TipoProduto tipoPronto = new TipoProduto();
        tipoPronto.setDescricao("Pronto para entrega");

        TipoProduto tipoEncomenda = new TipoProduto();
        tipoEncomenda.setDescricao("Encomenda");

        Produto p1 = new Produto();
        p1.setNome("Bolo de Chocolate");
        p1.setPreco(45.00);
        p1.setTipoProduto(tipoPronto);

        Produto p2 = new Produto();
        p2.setNome("Bolo de Morango");
        p2.setPreco(50.00);
        p2.setTipoProduto(tipoEncomenda);

        Produto p3 = new Produto();
        p3.setNome("Bolo Red Velvet");
        p3.setPreco(60.00);
        p3.setTipoProduto(tipoPronto);

        Produto p4 = new Produto();
        p4.setNome("Bolo de Cenoura");
        p4.setPreco(35.00);
        p4.setTipoProduto(tipoEncomenda);

        produtos.addAll(p1, p2, p3, p4);
    }

    public StringProperty pesquisaProperty() {
        return pesquisa;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

}
