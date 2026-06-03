package edu.fatec.poo.persistence.sqlServer.daoImplementations;

import edu.fatec.poo.persistence.daoIntefaces.*;

/**
 * Fábrica thread-safe responsável pela criação e gerenciamento de instâncias dos DAOs
 * (Data Access Objects) específicos para o banco de dados SQL Server.
 * <p>
 * Esta classe implementa o padrão de projeto <b>Factory</b> combinado com o gerenciamento
 * de instâncias em cache (estilo <b>Singleton Lazy</b>) para garantir que apenas uma instância de cada
 * implementação de DAO exista em toda a aplicação. Isso otimiza o uso de memória e centraliza
 * os pontos de acesso à camada de persistência.
 * </p>
 *
 * @see CarrinhoDAO
 * @see ClienteDAO
 * @see ItemCarrinhoDAO
 * @see ItemPedidoDAO
 * @see PedidoDAO
 * @see ProdutoDAO
 * @see TipoProdutoDAO
 */
public class SqlDaoFactory {

    private static CarrinhoDAO carrinhoDaoInstance;
    private static ClienteDAO clienteDaoInstance;
    private static ItemCarrinhoDAO itemCarrinhoDaoInstance;
    private static ItemPedidoDAO itemPedidoDaoInstance;
    private static PedidoDAO pedidoDaoInstance;
    private static ProdutoDAO produtoDaoInstance;
    private static TipoProdutoDAO tipoProdutoDaoInstance;

    /**
     * Construtor privado para impedir a instanciação direta da fábrica,
     * reforçando o acesso puramente estático.
     */
    private SqlDaoFactory() {
    }

    /**
     * Fornece a instância única e global de {@link CarrinhoDAO} para operações do carrinho.
     *
     * @return A instância persistente de {@link CarrinhoSqlImpl}.
     */
    public static synchronized CarrinhoDAO getCarrinhoDAO() {
        if (carrinhoDaoInstance == null) {
            carrinhoDaoInstance = new CarrinhoSqlImpl();
        }
        return carrinhoDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link ClienteDAO} para operações de clientes.
     *
     * @return A instância persistente de {@link ClienteSqlImpl}.
     */
    public static synchronized ClienteDAO getClienteDao() {
        if (clienteDaoInstance == null) {
            clienteDaoInstance = new ClienteSqlImpl();
        }
        return clienteDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link ItemCarrinhoDAO} para manipulação dos itens no carrinho.
     *
     * @return A instância persistente de {@link ItemCarrinhoSqlImpl}.
     */
    public static synchronized ItemCarrinhoDAO getItemCarrinhoDao() {
        if (itemCarrinhoDaoInstance == null) {
            itemCarrinhoDaoInstance = new ItemCarrinhoSqlImpl();
        }
        return itemCarrinhoDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link ItemPedidoDAO} para vinculação de itens aos pedidos.
     *
     * @return A instância persistente de {@link ItemPedidoSqlImpl}.
     */
    public static synchronized ItemPedidoDAO getItemPedidoDao() {
        if (itemPedidoDaoInstance == null) {
            itemPedidoDaoInstance = new ItemPedidoSqlImpl();
        }
        return itemPedidoDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link PedidoDAO} para gerenciamento de vendas e relatórios.
     *
     * @return A instância persistente de {@link PedidoSqlImpl}.
     */
    public static synchronized PedidoDAO getPedidoDao() {
        if (pedidoDaoInstance == null) {
            pedidoDaoInstance = new PedidoSqlImpl();
        }
        return pedidoDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link ProdutoDAO} para o catálogo de produtos.
     *
     * @return A instância persistente de {@link ProdutoSqlImpl}.
     */
    public static synchronized ProdutoDAO getProdutoDao() {
        if (produtoDaoInstance == null) {
            produtoDaoInstance = new ProdutoSqlImpl();
        }
        return produtoDaoInstance;
    }

    /**
     * Fornece a instância única e global de {@link TipoProdutoDAO} para categorização de produtos.
     *
     * @return A instância persistente de {@link TipoProdutoSqlImpl}.
     */
    public static synchronized TipoProdutoDAO getTipoProdutoDao() {
        if (tipoProdutoDaoInstance == null) {
            tipoProdutoDaoInstance = new TipoProdutoSqlImpl();
        }
        return tipoProdutoDaoInstance;
    }

}