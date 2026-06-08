package edu.fatec.poo.controller;

import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.ItemPedido;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class CompraController {

    private ObservableList<ItemPedido> itens = FXCollections.observableArrayList();
    private StringProperty endereco = new SimpleStringProperty("");
    private StringProperty subtotal = new SimpleStringProperty("R$ 0,00");
    private StringProperty frete = new SimpleStringProperty("R$ 0,00");
    private StringProperty total = new SimpleStringProperty("R$ 0,00");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void CarregarItens() {

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

    public void setItens(List<ItemCarrinho> novosItens) {
        itens.clear();
        for (ItemCarrinho ni : novosItens) {
            ItemPedido ip = new ItemPedido();

            ip.setProduto(ni.getProduto());
            ip.setQuantidade(ni.getQuantidade());
            ip.setPrecoUnitario(ni.getProduto().getPreco());
            itens.add(ip);
        }
        calcularTotais();
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