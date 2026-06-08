package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.MinhaContaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MinhaContaView extends VBox {

    private final MinhaContaController mc = new MinhaContaController();

    public MinhaContaView() {
        setSpacing(0);
        setPrefSize(900, 600);

        // --- BARRA SUPERIOR (TOP) ---
        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        /*
        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);
        txtPesquisa.setOnAction(event -> {
            // mc.Pesquisar(txtPesquisa.getText());
        });

         */

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

            menu.getItems().addAll(itemInicio, itemMinhaConta, itemCarrinho, itemPedidos, itemSobreLoja, itemSair);
            menu.show(btnMenu, Side.BOTTOM, 0, 0);
        });

        // CORREÇÃO: txtPesquisa adicionado ao topo
        top.getChildren().addAll(
                logo,
                //txtPesquisa,
                titulo,
                btnMenu
        );

        // --- CORPO DA TELA ---
        HBox corpo = new HBox(20);
        corpo.setPadding(new Insets(20)); // Adicionado padding para não colar nas bordas
        VBox.setVgrow(corpo, Priority.ALWAYS);

        // Coluna Esquerda (Dados Pessoais)
        VBox esquerda = new VBox(10);
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();
        txtNome.setEditable(false);
        txtNome.setMaxWidth(Double.MAX_VALUE);

        Label lblEmail = new Label("E-mail");
        TextField txtEmail = new TextField();
        txtEmail.setEditable(false);
        txtEmail.setMaxWidth(Double.MAX_VALUE);

        // CORREÇÃO: Telefone corrigido e reativado
        Label lblTelefone = new Label("Telefone");
        TextField txtTelefone = new TextField();
        txtTelefone.setEditable(false);
        txtTelefone.setMaxWidth(Double.MAX_VALUE);

        HBox botoes = new HBox(10);
        Button btnEditar = new Button("Editar");
        Button btnExcluir = new Button("Excluir");
        botoes.getChildren().addAll(btnEditar, btnExcluir);

        HBox botoes2 = new HBox(10);
        Button btnCarrinho = new Button("Meu Carrinho");
        Button btnPedidos = new Button("Últimos Pedidos");
        botoes2.getChildren().addAll(btnCarrinho, btnPedidos);

        esquerda.getChildren().addAll(
                lblNome, txtNome,
                lblEmail, txtEmail,
                lblTelefone, txtTelefone, // Recolocado no layout
                botoes, botoes2);

        // Coluna Direita (Endereço)
        VBox direita = new VBox(10);
        HBox.setHgrow(direita, Priority.ALWAYS);

        Label lblEndereco = new Label("Endereço");
        TextField txtEndereco = new TextField();
        txtEndereco.setEditable(false);
        txtEndereco.setPromptText("Logradouro");
        txtEndereco.setMaxWidth(Double.MAX_VALUE);

        HBox cepNumero = new HBox(10);

        VBox cepBox = new VBox(4);
        Label lblCep = new Label("CEP");
        TextField txtCep = new TextField();
        txtCep.setEditable(false);
        HBox.setHgrow(cepBox, Priority.ALWAYS);
        txtCep.setMaxWidth(Double.MAX_VALUE);
        cepBox.getChildren().addAll(lblCep, txtCep);

        VBox numeroBox = new VBox(4);
        Label lblNumero = new Label("Número");
        TextField txtNumero = new TextField();
        txtNumero.setEditable(false);
        txtNumero.setMaxWidth(Double.MAX_VALUE);
        numeroBox.getChildren().addAll(lblNumero, txtNumero);

        cepNumero.getChildren().addAll(cepBox, numeroBox);

        Label lblComplemento = new Label("Complemento");
        TextField txtComplemento = new TextField();
        txtComplemento.setEditable(false);
        txtComplemento.setMaxWidth(Double.MAX_VALUE);
        txtComplemento.setPrefHeight(80);
        VBox.setVgrow(txtComplemento, Priority.ALWAYS);

        direita.getChildren().addAll(lblEndereco, txtEndereco, cepNumero, lblComplemento, txtComplemento);

        // Juntando as colunas no corpo
        corpo.getChildren().addAll(esquerda, direita);

        // --- BINDINGS (CONTROLLER) ---
        txtNome.textProperty().bindBidirectional(mc.nomeProperty());
        txtEmail.textProperty().bindBidirectional(mc.emailProperty());
        txtTelefone.textProperty().bindBidirectional(mc.telefoneProperty()); // Reativado
        txtEndereco.textProperty().bindBidirectional(mc.enderecoProperty());
        txtCep.textProperty().bindBidirectional(mc.cepProperty());
        txtNumero.textProperty().bindBidirectional(mc.numeroProperty());
        txtComplemento.textProperty().bindBidirectional(mc.complementoProperty());

        // --- AÇÕES DOS BOTÕES ---
        btnEditar.setOnAction(e -> Contexto.chamaOutraTela(new EditarContaView(), "Editar Conta"));
        btnCarrinho.setOnAction(e -> Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho"));
        btnPedidos.setOnAction(e -> Contexto.chamaOutraTela(new PedidoView(), "Meus Pedidos"));

        btnExcluir.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir conta");
            alert.setHeaderText("Certeza que deseja excluir sua conta?");
            alert.setContentText("Esta ação não poderá ser desfeita.");

            ButtonType btnVoltar = new ButtonType("Voltar");
            ButtonType btnConfirmar = new ButtonType("Excluir");

            alert.getButtonTypes().setAll(btnVoltar, btnConfirmar);

            alert.showAndWait().ifPresent(resposta -> {
                if (resposta == btnConfirmar) {
                    try {
                        mc.Excluir();
                    } catch (Exception exception) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Erro");
                        alert2.setHeaderText("Ocorreu um erro?");
                        alert2.setContentText(exception.getMessage());
                        alert2.showAndWait();
                    }
                }
            });
        });

        Label mensagem = new Label("");
        mensagem.setPadding(new Insets(0, 0, 10, 20)); // Evita que a mensagem grude no rodapé
        mensagem.textProperty().bind(mc.mensagemProperty());

        // CORREÇÃO: 'corpo' foi devidamente adicionado à árvore visual aqui
        getChildren().addAll(top, corpo, mensagem);
    }
}