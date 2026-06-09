package edu.fatec.poo.controller;

import java.time.LocalDate;
import java.util.UUID;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.ItemPedido;
import edu.fatec.poo.model.Pedido;
import edu.fatec.poo.model.StatusPedido;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import edu.fatec.poo.view.CarrinhoView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PedidoController {

    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
    private StringProperty mensagem = new SimpleStringProperty("");

    public void CarregarPedidos() {

        try {
            pedidos.clear();
            var pedidoBD = SqlDaoFactory.getPedidoDao().findByCliente(Contexto.getClienteLogado());

            pedidoBD.ifPresent(Lista -> pedidos.addAll(Lista));

        } catch (Exception e) {
            mensagem.set("Erro ao carregar pedidos.");
            e.printStackTrace();
        }

        // buscar do BD depois

    }

    public ObservableList<Pedido> getPedidos() {
        return pedidos;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }
}