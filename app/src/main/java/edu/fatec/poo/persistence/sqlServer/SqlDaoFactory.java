package edu.fatec.poo.persistence.sqlServer;

import edu.fatec.poo.persistence.daoIntefaces.*;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.*;

public class SqlDaoFactory {
    private static CarrinhoDAO carrinhoDaoInstance;
    private static ClienteDAO clienteDaoInstance;
    private static ItemCarrinhoDAO itemCarrinhoDaoInstance;
    private static ItemPedidoDAO itemPedidoDaoInstance;
    private static PedidoDAO pedidoDaoInstance;
    private static ProdutoDAO produtoDaoInstance;
    private static TipoProdutoDAO tipoProdutoDaoInstance;

    private SqlDaoFactory() {
    }

    public static synchronized CarrinhoDAO getCarrinhoDAO() {
        if (carrinhoDaoInstance == null) {
            carrinhoDaoInstance = new CarrinhoSqlImpl();
        }
        return carrinhoDaoInstance;
    }

    public static synchronized ClienteDAO getClienteDao() {
        if (clienteDaoInstance == null) {
            clienteDaoInstance = new ClienteSqlImpl();
        }
        return clienteDaoInstance;
    }

    public static synchronized ItemCarrinhoDAO getItemCarrinhoDao() {
        if (itemCarrinhoDaoInstance == null) {
            itemCarrinhoDaoInstance = new ItemCarrinhoSqlImpl();
        }
        return itemCarrinhoDaoInstance;
    }

    public static synchronized ItemPedidoDAO getItemPedidoDao() {
        if (itemPedidoDaoInstance == null) {
            itemPedidoDaoInstance = new ItemPedidoSqlImpl();
        }
        return itemPedidoDaoInstance;
    }

    public static synchronized PedidoDAO getPedidoDao() {
        if (pedidoDaoInstance == null) {
            pedidoDaoInstance = new PedidoSqlImpl();
        }
        return pedidoDaoInstance;
    }

    public static synchronized ProdutoDAO getProdutoDao() {
        if (produtoDaoInstance == null) {
            produtoDaoInstance = new ProdutoSqlImpl();
        }
        return produtoDaoInstance;
    }

    public static synchronized TipoProdutoDAO getTipoProdutoDao() {
        if (tipoProdutoDaoInstance == null) {
            tipoProdutoDaoInstance = new TipoProdutoSqlImpl();
        }
        return tipoProdutoDaoInstance;
    }

}
