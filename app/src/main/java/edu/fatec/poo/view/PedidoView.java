package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.PedidoController;
import edu.fatec.poo.model.Pedido;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PedidoView extends VBox {

    PedidoController mc = new PedidoController();

    public PedidoView() {
        setSpacing(0);
        setPrefSize(900, 600);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);

        Label titulo = new Label("Meus Pedidos");
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

        TableView<Pedido> tabela = new TableView<>();
        tabela.setItems(mc.getPedidos());
        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<Pedido, String> colNome = new TableColumn<>("Nome");
        colNome.setPrefWidth(250);
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCliente().getNome()));

        TableColumn<Pedido, String> colData = new TableColumn<>("Data");
        colData.setPrefWidth(150);
        colData.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getData().toString()));

        TableColumn<Pedido, String> colValor = new TableColumn<>("Valor");
        colValor.setPrefWidth(150);
        colValor.setCellValueFactory(
                cell -> new SimpleStringProperty(String.format("R$ %.2f", cell.getValue().getPrecoTotal())));

        TableColumn<Pedido, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(150);
        colStatus.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus() != null
                ? cell.getValue().getStatus().toString()
                : ""));

        TableColumn<Pedido, Void> colAcao = new TableColumn<>("");
        colAcao.setPrefWidth(150);
        colAcao.setCellFactory(col -> new TableCell<>() {
            Button btn = new Button("Pedir novamente");
            {
                btn.setOnAction(e -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    mc.pedirNovamente(pedido);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tabela.getColumns().addAll(colNome, colData, colValor, colStatus, colAcao);

        Label mensagem = new Label("");
        mensagem.textProperty().bind(mc.mensagemProperty());

        getChildren().addAll(top, tabela, mensagem);

        mc.CarregarPedidos();
    }
}