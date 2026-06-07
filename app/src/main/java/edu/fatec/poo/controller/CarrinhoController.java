package edu.fatec.poo.controller;

import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarrinhoController {

    private ObservableList<ItemCarrinho> itens = FXCollections.observableArrayList();

    public void Pesquisar() {
    }

    public void CarregarItens() {
        TipoProduto tipoPronto = new TipoProduto();
        tipoPronto.setDescricao("Pronto para entrega");

        Produto p1 = new Produto();
        p1.setNome("Bolo de Chocolate");
        p1.setPreco(45.00);
        p1.setTipoProduto(tipoPronto);

        Produto p2 = new Produto();
        p2.setNome("Bolo de Morango");
        p2.setPreco(50.00);
        p2.setTipoProduto(tipoPronto);

        ItemCarrinho ic1 = new ItemCarrinho();
        ic1.setProduto(p1);
        ic1.setQuantidade(1);

        ItemCarrinho ic2 = new ItemCarrinho();
        ic2.setProduto(p2);
        ic2.setQuantidade(2);

        Produto p3 = new Produto();
        p3.setNome("Bolo de Chocolate");
        p3.setPreco(45.00);
        p3.setTipoProduto(tipoPronto);

        Produto p4 = new Produto();
        p4.setNome("Bolo de Morango");
        p4.setPreco(50.00);
        p4.setTipoProduto(tipoPronto);

        ItemCarrinho ic3 = new ItemCarrinho();
        ic3.setProduto(p3);
        ic3.setQuantidade(1);

        ItemCarrinho ic4 = new ItemCarrinho();
        ic4.setProduto(p4);
        ic4.setQuantidade(2);

        itens.addAll(ic1, ic2, ic3, ic4);
    }

    public ObservableList<ItemCarrinho> getItemCarrinho() {
        return itens;
    }
}
