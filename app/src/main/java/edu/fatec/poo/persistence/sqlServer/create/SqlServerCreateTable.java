package edu.fatec.poo.persistence.sqlServer.create;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlServerCreateTable implements ICreateTable {

    private final ADaoConnector connector;

    public SqlServerCreateTable(ADaoConnector connector) {
        this.connector = connector;
    }

    @Override
    public void createTableAll() throws SQLException, ClassNotFoundException {
        createTableCliente();
        createTableTipoProduto();
        createTableProduto();
        createTablePedido();
        createTableItemPedido();
        createTableCarrinho();
        createTableItemCarrinho();
    }

    private void executeTableCreation(String tableName, String sql) throws SQLException, ClassNotFoundException {
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela " + tableName + " criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela " + tableName + ": " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void createTableCliente() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.cliente', N'U') IS NULL
                BEGIN
                    CREATE TABLE cliente (
                        id VARCHAR(36) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        senha VARCHAR(100) NOT NULL,
                        endereco_logradouro VARCHAR(150),
                        endereco_cep VARCHAR(9),
                        endereco_num INT CHECK ( endereco_num > 0 ),
                        endereco_complemento VARCHAR(50),
                        role VARCHAR(10) NOT NULL
                    );
                END;
                """;
        executeTableCreation("Cliente", sql);
    }

    @Override
    public void createTableTipoProduto() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.tipo_produto', N'U') IS NULL
                BEGIN
                    CREATE TABLE tipo_produto (
                        id VARCHAR(36) PRIMARY KEY,
                        descricao VARCHAR(100) NOT NULL
                    );
                END;
                """;
        executeTableCreation("Tipo Produto", sql);
    }

    @Override
    public void createTableProduto() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.produto', N'U') IS NULL
                BEGIN
                    CREATE TABLE produto (
                        id VARCHAR(36) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        preco NUMERIC(8,2) NOT NULL CHECK ( preco > 0.0 ),
                        tipo_produto VARCHAR(36) NOT NULL,
                        cliente VARCHAR(36) NOT NULL,
                        FOREIGN KEY (tipo_produto) REFERENCES tipo_produto(id),
                        FOREIGN KEY (cliente) REFERENCES cliente(id)
                    );
                END;
                """;
        executeTableCreation("Produto", sql);
    }

    @Override
    public void createTablePedido() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.pedido', N'U') IS NULL
                BEGIN
                    CREATE TABLE pedido (
                        id VARCHAR(36) PRIMARY KEY,
                        cliente VARCHAR(36) NOT NULL,
                        data DATE NOT NULL CHECK ( data <= GETDATE() ),
                        status VARCHAR(100) NOT NULL,
                        FOREIGN KEY (cliente) REFERENCES cliente(id)
                    );
                END;
                """;
        executeTableCreation("Pedido", sql);
    }

    @Override
    public void createTableItemPedido() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.item_pedido', N'U') IS NULL
                BEGIN
                    CREATE TABLE item_pedido (
                        id VARCHAR(36) PRIMARY KEY,
                        quantidade INT NOT NULL CHECK ( quantidade > 0 ),
                        preco_unitario NUMERIC(8,2) NOT NULL CHECK ( preco_unitario > 0 ),
                        pedido VARCHAR(36) NOT NULL,
                        produto VARCHAR(36) NOT NULL,
                        FOREIGN KEY (pedido) REFERENCES pedido(id),
                        FOREIGN KEY (produto) REFERENCES produto(id)
                    );
                END;
                """;
        executeTableCreation("Item Pedido", sql);
    }

    @Override
    public void createTableCarrinho() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.carrinho', N'U') IS NULL
                BEGIN
                    CREATE TABLE carrinho (
                        id VARCHAR(36) PRIMARY KEY,
                        cliente VARCHAR(36) NOT NULL UNIQUE,
                        FOREIGN KEY (cliente) REFERENCES cliente(id)
                    );
                END;
                """;
        executeTableCreation("Carrinho", sql);
    }

    @Override
    public void createTableItemCarrinho() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.item_carrinho', N'U') IS NULL
                BEGIN
                    CREATE TABLE item_carrinho (
                        id VARCHAR(36) PRIMARY KEY,
                        quantidade INT NOT NULL CHECK ( quantidade > 0 ), 
                        carrinho VARCHAR(36) NOT NULL,
                        produto VARCHAR(36) NOT NULL,
                        FOREIGN KEY (carrinho) REFERENCES carrinho(id) ON DELETE CASCADE,
                        FOREIGN KEY (produto) REFERENCES produto(id)
                    );
                END;
                """;
        executeTableCreation("Item Carrinho", sql);
    }
}