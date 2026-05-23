package edu.fatec.poo.model;

import java.util.UUID;

public class ItemPedido {
    private UUID id;
    private int quantidade;
    private double precoUnintario;
    private Pedido pedido;
    private Produto produto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnintario() {
        return precoUnintario;
    }

    public void setPrecoUnintario(double precoUnintario) {
        this.precoUnintario = precoUnintario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
