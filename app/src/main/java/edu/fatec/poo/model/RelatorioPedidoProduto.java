package edu.fatec.poo.model;

import java.time.LocalDate;

public record RelatorioPedidoProduto(
        LocalDate data,
        String nomeProduto,
        Long quantidadePedidos,
        Double valorTotalVendido
) {
    public RelatorioPedidoProduto {
    }
}
