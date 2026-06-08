package edu.fatec.poo.view;

import java.util.UUID;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.SqlDaoFactory;
import edu.fatec.poo.persistence.sqlServer.daoImplementations.CarrinhoSqlImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ProdutoView extends VBox {

    public ProdutoView(Produto produto) {
        setSpacing(0);
        setPrefSize(900, 600);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);

        Label titulo = new Label("Produto");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        HBox.setHgrow(titulo, Priority.ALWAYS);
        titulo.setMaxWidth(Double.MAX_VALUE);
        titulo.setAlignment(Pos.CENTER);

        Button btnMenu = new Button("☰");
        btnMenu.setOnAction(e -> {
            ContextMenu menu = new ContextMenu();

            MenuItem itemInicio = new MenuItem("Início");
            MenuItem itemMinhaConta = new MenuItem("Minha Conta");
            MenuItem itemCarrinho = new MenuItem("Carrinho");
            MenuItem itemPedidos = new MenuItem("Últimos Pedidos");
            MenuItem itemSobreLoja = new MenuItem("Sobre a Loja");
            MenuItem itemSair = new MenuItem("Sair");

            itemInicio.setOnAction(ev -> Contexto.chamaOutraTela(new InicioView(), "Início"));
            itemMinhaConta.setOnAction(ev -> Contexto.chamaOutraTela(new MinhaContaView(), "Minha Conta"));
            itemCarrinho.setOnAction(ev -> Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho"));
            itemPedidos.setOnAction(ev -> Contexto.chamaOutraTela(new PedidoView(), "Meus Pedidos"));
            itemSobreLoja.setOnAction(ev -> Contexto.chamaOutraTela(new SobreLojaView(), "Sobre a Loja"));
            itemSair.setOnAction(ev -> {
                Contexto.sair();
                Contexto.chamaOutraTela(new LoginView(), "Login");
            });

            menu.getItems().addAll(
                    itemInicio, itemMinhaConta, itemCarrinho,
                    itemPedidos, itemSobreLoja, itemSair);

            menu.show(btnMenu, Side.BOTTOM, 0, 0);
        });

        top.getChildren().addAll(logo, txtPesquisa, titulo, btnMenu);

        HBox corpo = new HBox(20);
        corpo.setPadding(new Insets(30));
        VBox.setVgrow(corpo, Priority.ALWAYS);

        VBox esquerda = new VBox(12);
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        Label nomeProduto = new Label(produto.getNome());
        nomeProduto.setFont(Font.font(20));
        nomeProduto.setMaxWidth(Double.MAX_VALUE);
        nomeProduto.setAlignment(Pos.CENTER);

        Label ingredientes = new Label("Ingrediente 01, Ingrediente 02, Ingrediente 03, Ingrediente 04");
        ingredientes.setWrapText(true);
        ingredientes.setFont(Font.font(14));

        Label mensagem = new Label("");

        HBox botoes = new HBox(12);
        Button btnCarrinho = new Button("add no Carrinho");
        btnCarrinho.setFont(Font.font(15));
        btnCarrinho.setOnAction(e -> {
            try {
                ItemCarrinho item = new ItemCarrinho();
                item.setId(UUID.randomUUID());
                item.setProduto(produto);
                item.setQuantidade(1);
                item.setCarrinho(Contexto.getCarrinhoAtivo());

                SqlDaoFactory.getItemCarrinhoDao().add(item);

            } catch (Exception er) {
                mensagem.setText("Erro ao adicioanr item no carrinho");
                er.printStackTrace();

            }

            Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho");
        });

        Button btnComprar = new Button("Comprar");
        btnComprar.setFont(Font.font(15));

        btnComprar.setOnAction(e -> {

        });

        botoes.getChildren().addAll(btnCarrinho, btnComprar);

        esquerda.getChildren().addAll(nomeProduto, ingredientes, botoes);

        VBox direita = new VBox(12);
        direita.setAlignment(Pos.TOP_CENTER);
        direita.setPrefWidth(200);

        Label img = new Label("img");
        img.setPrefSize(150, 150);
        img.setAlignment(Pos.CENTER);
        img.setStyle("-fx-border-color: #aaa; -fx-border-radius: 10;");

        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.setFont(Font.font("System", FontWeight.BOLD, 18));
        preco.setMaxWidth(Double.MAX_VALUE);
        preco.setAlignment(Pos.CENTER_RIGHT);

        direita.getChildren().addAll(img, preco);

        corpo.getChildren().addAll(esquerda, direita);

        getChildren().addAll(top, corpo, mensagem);
    }
}