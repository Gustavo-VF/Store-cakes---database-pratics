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
                        role VARCHAR(15) NOT NULL
                    );
                
                    -- População ajustada com as roles: VENDEDOR e COMPRADOR
                    INSERT INTO cliente (id, nome, email, senha, endereco_logradouro, endereco_cep, endereco_num, role)
                    VALUES 
                    ('4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8', 'Vendedor Master', 'vendedor', 'vendedor', 'Av. Paulista, 1000', '01310-100', 1000, 'VENDEDOR'),
                    ('8f7e6d5c-4b3a-2a1a-0f9e-8d7c6b5a4f3e', 'Comprador Padrão', 'comprador', 'comprador', 'Rua das Flores, 45', '12345-678', 45, 'COMPRADOR');
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
                
                    INSERT INTO tipo_produto (id, descricao)
                    VALUES 
                    ('1b2c3d4e-5f6a-7b8c-9d0e-1f2a3b4c5d6e', 'Bolo'),
                    ('2c3d4e5f-6a7b-8c9d-0e1f-2a3b4c5d6e7f', 'Torta'),
                    ('11111111-1111-1111-1111-111111111111', 'Pronto para entrega'),
                    ('22222222-2222-2222-2222-222222222222', 'Encomenda');
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
                        vendedor VARCHAR(36) NOT NULL,
                        FOREIGN KEY (tipo_produto) REFERENCES tipo_produto(id),
                        FOREIGN KEY (vendedor) REFERENCES cliente(id)
                    );
                
                    INSERT INTO produto (id, nome, preco, tipo_produto, vendedor)
                    VALUES 
                    ('3d4e5f6a-7b8c-9d0e-1f2a-3b4c5d6e7f8a', 'Bolinho de Carro', 99.99, '1b2c3d4e-5f6a-7b8c-9d0e-1f2a3b4c5d6e', '4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('4e5f6a7b-8c9d-0e1f-2a3b-4c5d6e7f8a9b', 'Bolo de POOte', 89.90, '2c3d4e5f-6a7b-8c9d-0e1f-2a3b4c5d6e7f', '4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0001-0001-0001-aaaaaaaaaaaa', 'Bolo de Chocolate', 45.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0002-0002-0002-aaaaaaaaaaaa', 'Bolo de Morango', 50.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0003-0003-0003-aaaaaaaaaaaa', 'Bolo Red Velvet', 60.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0004-0004-0004-aaaaaaaaaaaa', 'Bolo de Cenoura', 35.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0005-0005-0005-aaaaaaaaaaaa', 'Bolo de Limão', 40.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0006-0006-0006-aaaaaaaaaaaa', 'Bolo de Coco', 42.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0007-0007-0007-aaaaaaaaaaaa', 'Bolo de Banana', 38.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0008-0008-0008-aaaaaaaaaaaa', 'Bolo de Maracujá', 48.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0009-0009-0009-aaaaaaaaaaaa', 'Bolo de Nozes', 55.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0010-0010-0010-aaaaaaaaaaaa', 'Bolo de Laranja', 37.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0011-0011-0011-aaaaaaaaaaaa', 'Bolo de Paçoca', 43.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0012-0012-0012-aaaaaaaaaaaa', 'Bolo de Prestígio', 52.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0013-0013-0013-aaaaaaaaaaaa', 'Bolo de Brigadeiro', 58.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0014-0014-0014-aaaaaaaaaaaa', 'Bolo de Abacaxi', 41.00, '22222222-2222-2222-2222-222222222222','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8'),
                    ('aaaaaaaa-0015-0015-0015-aaaaaaaaaaaa', 'Bolo de Churros', 62.00, '11111111-1111-1111-1111-111111111111','4a7f3e12-8b9c-4d5e-a1b2-c3d4e5f6a7b8');
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
                
                    INSERT INTO pedido (id, cliente, data, status)
                    VALUES ('5f6a7b8c-9d0e-1f2a-3b4c-5d6e7f8a9b0c', '8f7e6d5c-4b3a-2a1a-0f9e-8d7c6b5a4f3e', GETDATE(), 'ANDAMENTO');
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
                
                    INSERT INTO item_pedido (id, quantidade, preco_unitario, pedido, produto)
                    VALUES ('6a7b8c9d-0e1f-2a3b-4c5d-6e7f8a9b0c1d', 1, 89.90, '5f6a7b8c-9d0e-1f2a-3b4c-5d6e7f8a9b0c', '4e5f6a7b-8c9d-0e1f-2a3b-4c5d6e7f8a9b');
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
                
                    INSERT INTO carrinho (id, cliente)
                    VALUES ('7b8c9d0e-1f2a-3b4c-5d6e-7f8a9b0c1d2e', '8f7e6d5c-4b3a-2a1a-0f9e-8d7c6b5a4f3e');
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
                
                    INSERT INTO item_carrinho (id, quantidade, carrinho, produto)
                    VALUES ('8c9d0e1f-2a3b-4c5d-6e7f-8a9b0c1d2e3f', 1, '7b8c9d0e-1f2a-3b4c-5d6e-7f8a9b0c1d2e', '3d4e5f6a-7b8c-9d0e-1f2a-3b4c5d6e7f8a');
                END;
                """;
        executeTableCreation("Item Carrinho", sql);
    }
}