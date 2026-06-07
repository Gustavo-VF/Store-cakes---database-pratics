package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import edu.fatec.poo.controller.CadastroController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CadastroView extends VBox {
    CadastroController cc = new CadastroController();

    public CadastroView() {
        setAlignment(Pos.CENTER);
        setSpacing(12);
        setPadding(new Insets(40));

        Label lblTitulo = new Label("Nova Conta");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");
        txtNome.setMaxWidth(300);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("E-mail");
        txtEmail.setMaxWidth(300);

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("Telefone");
        txtTelefone.setMaxWidth(300);

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(300);

        PasswordField txtConfirmarSenha = new PasswordField();
        txtConfirmarSenha.setPromptText("Confirmar Senha");
        txtConfirmarSenha.setMaxWidth(300);

        TextField txtEndereco = new TextField();
        txtEndereco.setPromptText("Endereço");
        txtEndereco.setMaxWidth(300);

        TextField txtCep = new TextField();
        txtCep.setPromptText("Cep");
        txtCep.setMaxWidth(300);

        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Numero");
        txtNumero.setMaxWidth(300);

        TextField txtComplemento = new TextField();
        txtComplemento.setPromptText("Complemento");
        txtComplemento.setMaxWidth(300);

        Label mensagem = new Label("");

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setMaxWidth(300);

        Button btnVoltar = new Button("Cancelar");
        btnVoltar.setMaxWidth(300);

        txtNome.textProperty().bindBidirectional(cc.nomeProperty());
        txtEmail.textProperty().bindBidirectional(cc.emailProperty());
        txtSenha.textProperty().bindBidirectional(cc.senhaProperty());
        txtConfirmarSenha.textProperty().bindBidirectional(cc.confirmarSenhaProperty());
        txtTelefone.textProperty().bindBidirectional(cc.telefoneProperty());
        txtEndereco.textProperty().bindBidirectional(cc.enderecoProperty());
        txtNumero.textProperty().bindBidirectional(cc.numeroProperty());
        txtComplemento.textProperty().bindBidirectional(cc.complementoProperty());
        txtCep.textProperty().bindBidirectional(cc.cepProperty());
        mensagem.textProperty().bind(cc.mensagemProperty());

        // funcoes dos botoes chamando os controllers
        btnCadastrar.setOnAction((event -> {
            cc.CadastrarCliente();

        }));

        btnVoltar.setOnAction(e -> {
            Contexto.chamaOutraTela(new LoginView(), "Login");
        });

        getChildren().addAll(lblTitulo, txtNome, txtEmail, txtTelefone, txtSenha,
                txtConfirmarSenha, txtEndereco, txtCep, txtNumero,
                txtComplemento, mensagem, btnCadastrar, btnVoltar);
    }

}
