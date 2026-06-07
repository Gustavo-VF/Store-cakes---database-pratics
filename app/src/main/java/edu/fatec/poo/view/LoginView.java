package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.LoginController;
import edu.fatec.poo.model.Produto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {
    LoginController lc = new LoginController();

    public LoginView() {

        setAlignment(Pos.CENTER);
        setSpacing(12);
        setPadding(new Insets(40));

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("E-mail");
        txtEmail.setMaxWidth(300);

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(300);

        Label mensagem = new Label("");

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(300);

        Hyperlink lnkCadastro = new Hyperlink("Criar conta");

        // provisorio pra testar o inicioView
        Button inicioView = new Button("inicioView");
        inicioView.setMaxWidth(300);

        // provisorio pra testar o inicioView
        Button carrinhoVie = new Button("carrinhoView");
        carrinhoVie.setMaxWidth(300);

        // provisorio pra testar o inicioView
        Button ProdutoView = new Button("carrinhoView");
        ProdutoView.setMaxWidth(300);

        // provisorio pra testar o inicioView
        Button PedidoView = new Button("PedidoView");
        PedidoView.setMaxWidth(300);

        // provisorio pra testar o inicioView
        Button VendedorCadProdutoView = new Button("VendedorCadProduto");
        VendedorCadProdutoView.setMaxWidth(300);

        Button compraView = new Button("compraView");
        compraView.setMaxWidth(300);

        txtEmail.textProperty().bindBidirectional(lc.emailProperty());
        txtSenha.textProperty().bindBidirectional(lc.senhaProperty());

        // funcoes dos botoes
        // depois passo pros controller respectivos de cada classe
        btnEntrar.setOnAction((event -> {
            lc.fazerLogin();
        }));

        lnkCadastro.setOnAction(event -> {
            Contexto.chamaOutraTela(new CadastroView(), "Cadastro");
        });

        inicioView.setOnAction(event -> {
            Contexto.chamaOutraTela(new InicioView(), "Inicio");

        });

        carrinhoVie.setOnAction(event -> {
            Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho");

        });

        ProdutoView.setOnAction(event -> {
            Produto produto = new Produto();
            Contexto.chamaOutraTela(new ProdutoView(produto), "Produto");

        });

        PedidoView.setOnAction(event -> {
            Contexto.chamaOutraTela(new PedidoView(), "Pedido");

        });

        VendedorCadProdutoView.setOnAction(event -> {
            Contexto.chamaOutraTela(new VendedorCadProdutoView(), "VendedorCadProduto");

        });

        compraView.setOnAction(event -> {
            Contexto.chamaOutraTela(new CompraView(), "compra");

        });

        getChildren().addAll(txtEmail, txtSenha, mensagem, btnEntrar,
                lnkCadastro, inicioView, carrinhoVie, ProdutoView, PedidoView, VendedorCadProdutoView, compraView);

    }

}
