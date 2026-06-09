package edu.fatec.poo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.view.CompraView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdutoController {

    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty ingredientes = new SimpleStringProperty("");
    private StringProperty valor = new SimpleStringProperty("");
    private StringProperty quantidade = new SimpleStringProperty("");
    private StringProperty mensagem = new SimpleStringProperty("");

    public void ComprarItem(Produto produto) {

        ItemCarrinho item = new ItemCarrinho();
        item.setId(UUID.randomUUID());
        item.setProduto(produto);
        item.setQuantidade(1);
        item.setCarrinho(Contexto.getCarrinhoAtivo());

        List<ItemCarrinho> itensCompra = new ArrayList<>();
        itensCompra.add(item);

        Contexto.chamaOutraTela(
                new CompraView(itensCompra),
                "Comprar");
    }

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