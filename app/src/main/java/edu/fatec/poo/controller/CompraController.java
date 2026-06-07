package edu.fatec.poo.controller;

import edu.fatec.poo.model.ItemPedido;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraController {

    private ObservableList<ItemPedido> itens = FXCollections.observableArrayList();
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty subtotal = new SimpleStringProperty("R$ 0,00");
    private StringProperty frete = new SimpleStringProperty("R$ 0,00");
    private StringProperty total = new SimpleStringProperty("R$ 0,00");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void CarregarItens() {
        TipoProduto tipo = new TipoProduto();
        tipo.setDescricao("Pronto para entrega");

        Produto p1 = new Produto();
        p1.setNome("Bolo de Chocolate");
        p1.setPreco(45.00);
        p1.setTipoProduto(tipo);

        ItemPedido i1 = new ItemPedido();
        i1.setProduto(p1);
        i1.setQuantidade(1);
        i1.setPrecoUnitario(45.00);

        Produto p2 = new Produto();
        p2.setNome("Bolo de Morango");
        p2.setPreco(50.00);
        p2.setTipoProduto(tipo);

        ItemPedido i2 = new ItemPedido();
        i2.setProduto(p2);
        i2.setQuantidade(2);
        i2.setPrecoUnitario(50.00);

        itens.addAll(i1, i2);

        calcularTotais();
    }

    private void calcularTotais() {
        double sub = itens.stream()
                .mapToDouble(i -> i.getPrecoUnitario() * i.getQuantidade())
                .sum();
        double fr = 5.00;
        subtotal.set(String.format("R$ %.2f", sub));
        frete.set(String.format("R$ %.2f", fr));
        total.set(String.format("R$ %.2f", sub + fr));
    }

    public void AdicionarEndereco() {
    }

    public void Prosseguir() {
    }

    public ObservableList<ItemPedido> getItens() {
        return itens;
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public StringProperty subtotalProperty() {
        return subtotal;
    }

    public StringProperty freteProperty() {
        return frete;
    }

    public StringProperty totalProperty() {
        return total;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }
}