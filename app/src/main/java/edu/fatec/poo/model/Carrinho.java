package edu.fatec.poo.model;

import java.util.UUID;

public class Carrinho {
    private UUID id_carrinho;
    private UUID id_cliente;

    public UUID getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(UUID id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public UUID getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(UUID id_cliente) {
        this.id_cliente = id_cliente;
    }
}
