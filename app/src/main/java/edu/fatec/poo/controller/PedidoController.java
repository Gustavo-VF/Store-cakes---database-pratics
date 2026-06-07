package edu.fatec.poo.controller;

import java.time.LocalDate;

import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.Pedido;
import edu.fatec.poo.model.StatusPedido;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PedidoController {

    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
    private StringProperty mensagem = new SimpleStringProperty("");

    public void CarregarPedidos() {

        Cliente cliente1 = new Cliente();
        cliente1.setNome("Gustavo");

        Pedido p1 = new Pedido();
        p1.setCliente(cliente1);
        p1.setData(LocalDate.of(2024, 3, 15));
        p1.setPrecoTotal(45.00);
        p1.setStatus(StatusPedido.ENTREGUE);

        Pedido p2 = new Pedido();
        p2.setCliente(cliente1);
        p2.setData(LocalDate.of(2024, 4, 20));
        p2.setPrecoTotal(90.00);
        p2.setStatus(StatusPedido.ANDAMENTO);

        Pedido p3 = new Pedido();
        p3.setCliente(cliente1);
        p3.setData(LocalDate.of(2024, 5, 1));
        p3.setPrecoTotal(60.00);
        p3.setStatus(StatusPedido.CANCELADO);

        pedidos.addAll(p1, p2, p3);
        // buscar do BD depois
    }

    public void pedirNovamente(Pedido pedido) {
        // lógica depois
    }

    public ObservableList<Pedido> getPedidos() {
        return pedidos;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }
}