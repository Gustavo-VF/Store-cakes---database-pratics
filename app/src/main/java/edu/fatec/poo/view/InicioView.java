package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.InicioController;
import edu.fatec.poo.model.Produto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InicioView extends VBox {
    InicioController ic = new InicioController();

    public InicioView() {
        setSpacing(0);
        setPrefSize(900, 600);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);
        txtPesquisa.setOnAction(event -> {
            ic.Pesquisar();
        });

        Label titulo = new Label("INICIO");
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

        /////////////////////////////////////////

        FlowPane gridProdutos = new FlowPane();
        gridProdutos.setHgap(12);
        gridProdutos.setVgap(12);
        gridProdutos.setPadding(new Insets(16));

        ic.getProdutos().addListener((javafx.collections.ListChangeListener<Produto>) change -> {
            gridProdutos.getChildren().clear();
            for (Produto p : ic.getProdutos()) {
                VBox cardProduto = new VBox(4);
                cardProduto.setPadding(new Insets(8));
                cardProduto.setPrefWidth(150);

                // trocar por uma Img depois
                Label img = new Label("img");
                img.setPrefSize(50, 50);
                img.setAlignment(Pos.CENTER);

                Label nome = new Label(p.getNome());

                Label tipo = new Label(p.getTipoProduto() != null ? p.getTipoProduto().getDescricao() : "");

                Label preco = new Label(String.format("R$ %.2f", p.getPreco()));

                Button btnAdd = new Button("+ Adicionar");
                btnAdd.setMaxWidth(Double.MAX_EXPONENT);

                btnAdd.setOnAction(e -> {
                    Contexto.chamaOutraTela(new ProdutoView(p), "Produto");
                });

                cardProduto.getChildren().addAll(img, nome, tipo, preco, btnAdd);
                gridProdutos.getChildren().add(cardProduto);
            }
        });

        /////////////////////////////////////////

        ScrollPane scrollProdutos = new ScrollPane(gridProdutos);
        scrollProdutos.setFitToWidth(true);
        scrollProdutos.setFitToHeight(true);

        /////////////////////////////////////////

        VBox sobreChef = new VBox(10);
        sobreChef.setPadding((new Insets(16)));
        sobreChef.setPrefHeight(200);
        sobreChef.setMaxWidth(200);

        Label tituloChef = new Label("Conheça o chef!");
        tituloChef.setMaxWidth(Double.MAX_VALUE);
        tituloChef.setAlignment(Pos.CENTER);

        Label foto = new Label("Foto \n Aqui");
        foto.setPrefSize(50, 50);
        foto.setAlignment(Pos.CENTER);
        foto.setFont(Font.font("System", 40));
        foto.setStyle("-fx-border-color: #aaa;");
        foto.setMaxWidth(Double.MAX_VALUE);

        Label nomeChef = new Label("Fulano de Tal");

        Label descricaoChef = new Label("Descrição/Curiosidades \nsobre o(a) chef.");

        Label certificadosChef = new Label("- Certificados");

        Label eperienciasChef = new Label("- Experiências");

        Label contatoChef = new Label("Contato");

        Label telefoneChef = new Label("(00) 90000-0000");

        Label emailChef = new Label("Fulano@email.com");

        sobreChef.getChildren().addAll(
                tituloChef, foto, nomeChef, descricaoChef,
                certificadosChef, eperienciasChef,
                contatoChef, telefoneChef, emailChef);

        top.getChildren().addAll(logo, txtPesquisa, titulo, btnMenu);

        /////////////////////////////////////////

        HBox painel = new HBox();
        VBox.setVgrow(painel, Priority.ALWAYS);
        HBox.setHgrow(scrollProdutos, Priority.ALWAYS);
        painel.getChildren().addAll(scrollProdutos, sobreChef);

        Label mensagem = new Label("");

        ic.CarregarProdutos();

        txtPesquisa.textProperty().bindBidirectional(ic.pesquisaProperty());
        mensagem.textProperty().bind(ic.mensagemProperty());

        getChildren().addAll(top, painel, mensagem);
    }

}
