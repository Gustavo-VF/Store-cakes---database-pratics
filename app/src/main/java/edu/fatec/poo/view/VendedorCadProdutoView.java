package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.ProdutoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VendedorCadProdutoView extends VBox {

    ProdutoController pc = new ProdutoController();

    public VendedorCadProdutoView() {
        setSpacing(0);
        setPrefSize(900, 600);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);

        Label titulo = new Label("Cadastrar Produto");
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
        corpo.setPadding(new Insets(20));
        VBox.setVgrow(corpo, Priority.ALWAYS);

        VBox esquerda = new VBox(10);
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();
        txtNome.setMaxWidth(Double.MAX_VALUE);

        Label lblIngredientes = new Label("Ingredientes Principais");
        TextArea txtIngredientes = new TextArea();
        txtIngredientes.setMaxWidth(Double.MAX_VALUE);
        txtIngredientes.setPrefHeight(120);
        VBox.setVgrow(txtIngredientes, Priority.ALWAYS);

        HBox valorQtd = new HBox(20);

        VBox valorBox = new VBox(4);
        Label lblValor = new Label("Valor");
        HBox valorInput = new HBox(4);
        valorInput.setAlignment(Pos.CENTER_LEFT);
        Label lblRS = new Label("R$");
        TextField txtValor = new TextField();
        txtValor.setPrefWidth(100);
        valorInput.getChildren().addAll(lblRS, txtValor);
        valorBox.getChildren().addAll(lblValor, valorInput);

        VBox qtdBox = new VBox(4);
        Label lblQtd = new Label("Quantidade máxima\npara compra");
        TextField txtQtd = new TextField();
        txtQtd.setPrefWidth(100);
        qtdBox.getChildren().addAll(lblQtd, txtQtd);

        valorQtd.getChildren().addAll(valorBox, qtdBox);

        esquerda.getChildren().addAll(lblNome, txtNome, lblIngredientes, txtIngredientes, valorQtd);

        VBox direita = new VBox(12);
        direita.setAlignment(Pos.TOP_CENTER);
        direita.setPrefWidth(200);

        Label lblInserirImagem = new Label("Inserir\nimagem");
        lblInserirImagem.setAlignment(Pos.CENTER);

        Label img = new Label("img");
        img.setPrefSize(120, 120);
        img.setAlignment(Pos.CENTER);
        img.setStyle("-fx-border-color: #aaa;");

        Button btnAdicionar = new Button("Adicionar\nproduto");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        // btnAdicionar.setOnAction(e -> pc.AdicionarProduto());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setOnAction(e -> Contexto.chamaOutraTela(new InicioView(), "Início"));

        direita.getChildren().addAll(lblInserirImagem, img, btnAdicionar, btnCancelar);

        corpo.getChildren().addAll(esquerda, direita);

        //
        // txtNome.textProperty().bindBidirectional(pc.nomeProperty());
        // txtIngredientes.textProperty().bindBidirectional(pc.ingredientesProperty());
        // txtValor.textProperty().bindBidirectional(pc.valorProperty());
        // txtQtd.textProperty().bindBidirectional(pc.quantidadeProperty());

        Label mensagem = new Label("");
        // mensagem.textProperty().bind(pc.mensagemProperty());

        getChildren().addAll(top, corpo, mensagem);
    }
}
