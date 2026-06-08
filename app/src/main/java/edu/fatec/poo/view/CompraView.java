
package edu.fatec.poo.view;

import java.util.List;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.CompraController;
import edu.fatec.poo.model.ItemCarrinho;
import edu.fatec.poo.model.ItemPedido;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CompraView extends VBox {

    CompraController cc = new CompraController();

    public CompraView(List<ItemCarrinho> itensPraComprar) {
        setSpacing(0);
        setPrefSize(900, 600);

        cc.setItens(itensPraComprar);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);

        Label titulo = new Label("Comprar");
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

        HBox corpo = new HBox(0);
        VBox.setVgrow(corpo, Priority.ALWAYS);

        VBox esquerda = new VBox(8);
        esquerda.setPadding(new Insets(16));
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        cc.getItens().addListener((javafx.collections.ListChangeListener<ItemPedido>) change -> {
            esquerda.getChildren().clear();
            for (ItemPedido item : cc.getItens()) {
                HBox linha = new HBox(10);
                linha.setPadding(new Insets(8));

                VBox info = new VBox(4);
                HBox.setHgrow(info, Priority.ALWAYS);

                Label nome = new Label(item.getProduto().getNome());

                Label tipo = new Label(item.getProduto().getTipoProduto() != null
                        ? item.getProduto().getTipoProduto().getDescricao()
                        : "");

                info.getChildren().addAll(nome, tipo);

                VBox precoQtd = new VBox(4);
                precoQtd.setAlignment(Pos.CENTER_RIGHT);
                Label preco = new Label(String.format("R$ %.2f", item.getPrecoUnitario()));
                Label qtd = new Label("0" + item.getQuantidade());
                precoQtd.getChildren().addAll(preco, qtd);

                linha.getChildren().addAll(info, precoQtd);
                esquerda.getChildren().add(linha);
            }
        });

        ScrollPane scroll = new ScrollPane(esquerda);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        HBox.setHgrow(scroll, Priority.ALWAYS);

        VBox direita = new VBox(12);
        direita.setPadding(new Insets(16));
        direita.setPrefWidth(280);
        direita.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 0 1;");

        Label lblEndereco = new Label("Endereço de Entrega");
        lblEndereco.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblEndereco.setMaxWidth(Double.MAX_VALUE);
        lblEndereco.setAlignment(Pos.CENTER);

        TextField txtEndereco = new TextField();
        txtEndereco.setPromptText("Endereço cadastrado");
        txtEndereco.setMaxWidth(Double.MAX_VALUE);
        txtEndereco.textProperty().bindBidirectional(cc.enderecoProperty());

        direita.getChildren().addAll(lblEndereco, txtEndereco);

        corpo.getChildren().addAll(scroll, direita);

        HBox rodape = new HBox(20);
        rodape.setPadding(new Insets(12, 16, 12, 16));
        rodape.setAlignment(Pos.CENTER_LEFT);
        rodape.setStyle("-fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho"));

        VBox subtotalBox = new VBox();
        Label lblSubtotalTitulo = new Label("Subtotal:");
        Label lblSubtotal = new Label("R$ 00,00");
        lblSubtotal.textProperty().bind(cc.subtotalProperty());
        subtotalBox.getChildren().addAll(lblSubtotalTitulo, lblSubtotal);

        VBox freteBox = new VBox();
        Label lblFreteTitulo = new Label("Frete:");
        Label lblFrete = new Label("R$ 00,00");
        lblFrete.textProperty().bind(cc.freteProperty());
        freteBox.getChildren().addAll(lblFreteTitulo, lblFrete);

        VBox totalBox = new VBox();
        Label lblTotalTitulo = new Label("Total");
        Label lblTotal = new Label("R$ 00,00");
        lblTotal.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblTotal.textProperty().bind(cc.totalProperty());
        totalBox.getChildren().addAll(lblTotalTitulo, lblTotal);

        HBox.setHgrow(totalBox, Priority.ALWAYS);
        totalBox.setAlignment(Pos.CENTER_RIGHT);

        Button btnProsseguir = new Button("Prosseguir");
        btnProsseguir.setFont(Font.font(14));
        btnProsseguir.setOnAction(e -> cc.Prosseguir());

        rodape.getChildren().addAll(btnVoltar, subtotalBox, freteBox, totalBox, btnProsseguir);

        Label mensagem = new Label("");
        mensagem.textProperty().bind(cc.mensagemProperty());

        getChildren().addAll(top, corpo, rodape, mensagem);

        cc.CarregarItens();
    }
}