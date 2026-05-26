package edu.fatec.poo.persistence.sqlServer.create;

import edu.fatec.poo.persistence.connection.ADaoConnector;
import edu.fatec.poo.persistence.connection.ICreateTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sqlServerCreateTable implements ICreateTable {

    private final ADaoConnector connector;

    public sqlServerCreateTable(ADaoConnector connector) {
        this.connector = connector;
    }

    @Override
    public void createTableAll() throws SQLException, ClassNotFoundException {
        createTableCliente();
        createTableTipoProduto();
        createTableProduto();
        createTablePedido();
        createTableItemPedido();
        //TODO
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
                        endereco_num INT,
                        endereco_complemento VARCHAR(50)
                    );
                END;
                """;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela Cliente criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela Cliente no: " + e.getMessage());
            throw e;
        }
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
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela Tipo Produto criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela Tipo Produto no: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void createTableProduto() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.produto', N'U') IS NULL
                BEGIN
                    CREATE TABLE produto (
                        id VARCHAR(36) PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        preco NUMERIC(8,2) NOT NULL,
                        tipo_produto VARCHAR(36) NOT NULL,
                        FOREIGN KEY (tipo_produto) REFERENCES tipo_produto(id)
                    );
                END;
                """;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela Produto criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela Produto no: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void createTablePedido() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.pedido', N'U') IS NULL
                BEGIN
                    CREATE TABLE pedido (
                        id VARCHAR(36) PRIMARY KEY,
                        cliente VARCHAR(36) NOT NULL,
                        preco_total NUMERIC(8,2) NOT NULL,
                        data DATE NOT NULL,
                        status VARCHAR(100) NOT NULL,
                        FOREIGN KEY (cliente) REFERENCES cliente(id)
                    );
                END;
                """;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela Pedido criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela Pedido no: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public void createTableItemPedido() throws SQLException, ClassNotFoundException {
        String sql = """
                IF OBJECT_ID(N'dbo.item_pedido', N'U') IS NULL
                BEGIN
                    CREATE TABLE item_pedido (
                        id VARCHAR(36) PRIMARY KEY,
                        quantidade INT NOT NULL,
                        preco_unitario NUMERIC(8,2) NOT NULL,
                        pedido VARCHAR(36) NOT NULL,
                        produto VARCHAR(36) NOT NULL,
                        FOREIGN KEY (pedido) REFERENCES pedido(id),
                        FOREIGN KEY (produto) REFERENCES produto(id)
                    );
                END;
                """;
        try (Connection c = connector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            System.out.println("[SQL Server] Tabela ItemPedido criada com sucesso ou já existente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SQL Server] Erro ao criar tabela ItemPedido no: " + e.getMessage());
            throw e;
        }
    }
}
