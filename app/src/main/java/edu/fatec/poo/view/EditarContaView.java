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

public class EditarContaView extends VBox {

    private final MinhaContaController mc = new MinhaContaController();

    public EditarContaView() {
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

        Label titulo = new Label("Editar Conta");
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

        // AJUSTE: txtPesquisa adicionado para igualar o topo com a MinhaContaView
        top.getChildren().addAll(
                logo,
                //txtPesquisa,
                titulo,
                btnMenu
        );

        // --- CORPO DA TELA ---
        HBox corpo = new HBox(20);
        corpo.setPadding(new Insets(20)); // Mesmo padding para manter o alinhamento idêntico
        VBox.setVgrow(corpo, Priority.ALWAYS);

        // Coluna Esquerda (Dados Pessoais)
        VBox esquerda = new VBox(10);
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        Label lblNome = new Label("Nome");
        TextField txtNome = new TextField();
        txtNome.setMaxWidth(Double.MAX_VALUE);

        Label lblEmail = new Label("E-mail");
        TextField txtEmail = new TextField();
        txtEmail.setMaxWidth(Double.MAX_VALUE);

        // AJUSTE: Telefone descomentado e reativado na mesma posição da tela de visualização
        Label lblTelefone = new Label("Telefone");
        TextField txtTelefone = new TextField();
        txtTelefone.setMaxWidth(Double.MAX_VALUE);

        HBox botoes = new HBox(10);
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        botoes.getChildren().addAll(btnSalvar, btnCancelar);

        esquerda.getChildren().addAll(
                lblNome, txtNome,
                lblEmail, txtEmail,
                lblTelefone, txtTelefone,
                botoes);

        // Coluna Direita (Endereço)
        VBox direita = new VBox(10);
        HBox.setHgrow(direita, Priority.ALWAYS);

        Label lblEndereco = new Label("Endereço");
        TextField txtEndereco = new TextField();
        txtEndereco.setPromptText("Logradouro");
        txtEndereco.setMaxWidth(Double.MAX_VALUE);

        HBox cepNumero = new HBox(10);

        VBox cepBox = new VBox(4);
        Label lblCep = new Label("CEP");
        TextField txtCep = new TextField();
        HBox.setHgrow(cepBox, Priority.ALWAYS);
        txtCep.setMaxWidth(Double.MAX_VALUE);
        cepBox.getChildren().addAll(lblCep, txtCep);

        VBox numeroBox = new VBox(4);
        Label lblNumero = new Label("Número");
        TextField txtNumero = new TextField();
        txtNumero.setMaxWidth(Double.MAX_VALUE);
        numeroBox.getChildren().addAll(lblNumero, txtNumero);

        cepNumero.getChildren().addAll(cepBox, numeroBox);

        Label lblComplemento = new Label("Complemento");
        TextField txtComplemento = new TextField();
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
        btnSalvar.setOnAction(e -> {
            try {
                mc.Editar();
            } catch (Exception exception) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erro");
                alert2.setHeaderText("Ocorreu um erro?");
                alert2.setContentText(exception.getMessage());
                alert2.showAndWait();
            }
        });
        btnCancelar.setOnAction(e -> Contexto.chamaOutraTela(new MinhaContaView(), "Minha Conta"));

        Label mensagem = new Label("");
        mensagem.setPadding(new Insets(0, 0, 10, 20)); // Mesmo espaçamento para mensagens de feedback
        mensagem.textProperty().bind(mc.mensagemProperty());

        // Renderização final dos nós da cena
        getChildren().addAll(top, corpo, mensagem);

        // Carrega as informações atuais do banco/controller para preencher os campos editáveis
        mc.carregarDados();
    }
}