package edu.fatec.poo.controller;

import java.util.ArrayList;
import java.util.List;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.ItemCarrinho;

import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import edu.fatec.poo.view.CompraView;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarrinhoController {

    private ObservableList<ItemCarrinho> itens = FXCollections.observableArrayList();
    private List<ItemCarrinho> itensSelecionados = new ArrayList<>();

    private StringProperty mensagem = new SimpleStringProperty("");
    private StringProperty total = new SimpleStringProperty("Total " + "R$: 00.00");

    public void Pesquisar() {
    }

    public void CarregarItens() {
        try {

            var carrinho = Contexto.getCarrinhoAtivo();
            if (carrinho == null) {
                mensagem.set("Carrinho vazio");
            }
            var resCarrinho = SqlDaoFactory.getItemCarrinhoDao().findByCarrinho(carrinho);

            resCarrinho.ifPresent(Lista -> itens.setAll(Lista));
        } catch (

        Exception e) {

        }

    }

    public void atualizarQuantidade(ItemCarrinho item, int qtd) {
        try {
            item.setQuantidade(qtd);
            SqlDaoFactory.getItemCarrinhoDao().update(item);
        } catch (Exception e) {
            mensagem.set("Erro ao atualizar qtd");
            e.printStackTrace();
        }

        calcTotal();

    }

    public void removeItem(ItemCarrinho item) {
        try {
            SqlDaoFactory.getItemCarrinhoDao().delete(item);
            itensSelecionados.remove(item);
            itens.remove(item);
        } catch (Exception e) {
            mensagem.set("Erro ao remover item");
            e.printStackTrace();

        }

    }

    public void ComprarTodos() {
        for (ItemCarrinho itemCarrinho : itens) {
            if (!itensSelecionados.contains(itemCarrinho)) {
                itensSelecionados.add(itemCarrinho);

            }

        }
        comprar();

    }

    public void comprar() {
        if (itensSelecionados.isEmpty()) {
            mensagem.set("Selecione pelo menos um item do carrinho.");
            return;
        }

        Contexto.chamaOutraTela(new CompraView(itensSelecionados), "Comprar");

    }

    public void esvaziarCarrinho() {
        try {
            for (ItemCarrinho item : itens) {
                if (itensSelecionados.contains(item)) {
                    itensSelecionados.remove(item);
                }
                SqlDaoFactory.getItemCarrinhoDao().delete(item);
            }
            itens.clear();
        } catch (Exception e) {
            mensagem.set("erro ao exsvaziar carrinho");
            e.printStackTrace();
        }
    }

    public void selecionarItem(ItemCarrinho item, boolean select) {
        if (select) {
            itensSelecionados.add(item);
        } else {
            itensSelecionados.remove(item);
        }

        calcTotal();

    }

    public void calcTotal() {
        double calculo = 0.0;

        for (ItemCarrinho item : itensSelecionados) {

            calculo += (item.getProduto().getPreco()) * (item.getQuantidade());

        }

        total.set(String.format("Total: R$ %.2f", calculo));

    }

    public ObservableList<ItemCarrinho> getItemCarrinho() {
        return itens;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

    public StringProperty totalProperty() {
        return total;
    }

    public List<ItemCarrinho> getItemSelecionado() {
        return itensSelecionados;
    }

}
