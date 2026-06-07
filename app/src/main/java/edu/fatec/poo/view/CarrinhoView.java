package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.CarrinhoController;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.Produto;
import edu.fatec.poo.model.TipoProduto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class CarrinhoView extends VBox {
    CarrinhoController cc = new CarrinhoController();

    public CarrinhoView() {
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
            cc.Pesquisar();
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

        top.getChildren().addAll(logo, txtPesquisa, titulo, btnMenu);

        /////////////////////////////////////////

        VBox paneItens = new VBox();
        paneItens.setPadding(new Insets(16));
        VBox.setVgrow(paneItens, Priority.ALWAYS);

        cc.getItemCarrinho().addListener((javafx.collections.ListChangeListener<ItemCarrinho>) change -> {

            atualizarGrid(paneItens);

        });

        cc.CarregarItens();

        VBox btnOpcoes = new VBox();

        Button comprarTodos = new Button("Comprar\nTodos");
        comprarTodos.setMaxWidth(300);
        comprarTodos.setFont(Font.font(15));
        comprarTodos.setTextAlignment(TextAlignment.CENTER);

        Button comprarSelecionados = new Button("Comprar\nSelecionados");
        comprarSelecionados.setMaxWidth(300);
        comprarSelecionados.setFont(Font.font(15));
        comprarSelecionados.setTextAlignment(TextAlignment.CENTER);

        Button esvaziarCarrinho = new Button("Esvaziar\nCarrinho");
        esvaziarCarrinho.setMaxWidth(300);
        esvaziarCarrinho.setFont(Font.font(15));
        esvaziarCarrinho.setTextAlignment(TextAlignment.CENTER);

        btnOpcoes.getChildren().addAll(comprarTodos, comprarSelecionados, esvaziarCarrinho);
        btnOpcoes.setAlignment(Pos.CENTER);
        btnOpcoes.setPadding(new Insets(20));
        btnOpcoes.setSpacing(20);

        Label mensagem = new Label("");

        HBox painelProtudos = new HBox();

        VBox.setVgrow(painelProtudos, Priority.ALWAYS);

        painelProtudos.getChildren().addAll(paneItens, btnOpcoes);

        getChildren().addAll(top, painelProtudos, mensagem);

    }

    private void atualizarGrid(VBox paneItens) {
        paneItens.getChildren().clear();
        for (ItemCarrinho ic : cc.getItemCarrinho()) {
            Produto produtoCarrinho = ic.getProduto();

            HBox itemNoCarrinho = new HBox(5);

            CheckBox checkItem = new CheckBox();
            checkItem.setMaxWidth(30);
            checkItem.setPrefWidth(30);

            // trocar por img do produto
            VBox imgPreco = new VBox(4);
            Label imgItem = new Label("imgItem");
            imgItem.setPrefSize(100, 100);
            imgItem.setAlignment(Pos.CENTER);
            imgItem.setStyle("-fx-border-color: #aaa;");
            Label precoItem = new Label(String.format("R$ %.2f", produtoCarrinho.getPreco()));
            imgPreco.getChildren().addAll(imgItem, precoItem);

            VBox dados = new VBox(4);
            HBox.setHgrow(dados, Priority.ALWAYS);
            itemNoCarrinho.setMaxWidth(Double.MAX_VALUE);

            Label nomeItem = new Label(produtoCarrinho.getNome());
            nomeItem.setFont(Font.font(15));
            TipoProduto tipoItemCarrinho = produtoCarrinho.getTipoProduto();
            Label descricaoItem = new Label(tipoItemCarrinho.getDescricao());
            descricaoItem.setFont(Font.font(15));

            dados.getChildren().addAll(nomeItem, descricaoItem);

            HBox quantidadeItem = new HBox(5);
            Button menos = new Button("-");
            menos.setFont(Font.font(15));

            Label numeroQuantidade = new Label("1");
            numeroQuantidade.setFont(Font.font(15));

            Button mais = new Button("+");
            mais.setFont(Font.font(15));
            quantidadeItem.setAlignment(Pos.CENTER);

            quantidadeItem.getChildren().addAll(menos, numeroQuantidade, mais);

            itemNoCarrinho.getChildren().addAll(checkItem, imgPreco, dados, quantidadeItem);
            itemNoCarrinho.setPadding(new Insets(10));
            itemNoCarrinho.setPrefHeight(100);
            itemNoCarrinho.setAlignment(Pos.CENTER_LEFT);

            paneItens.getChildren().add(itemNoCarrinho);
            paneItens.setSpacing(10);
            paneItens.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(paneItens, Priority.ALWAYS);

            menos.setOnAction(e -> {
                int qtd = Integer.parseInt(numeroQuantidade.getText());
                if (qtd > 1) {
                    numeroQuantidade.setText(String.valueOf(qtd - 1));
                }
            });

            mais.setOnAction(e -> {
                int qtd = Integer.parseInt(numeroQuantidade.getText());
                numeroQuantidade.setText(String.valueOf(qtd + 1));
            });

        }
    }

}
