package edu.fatec.poo;

import edu.fatec.poo.model.Carrinho;
import edu.fatec.poo.model.Cliente;
import edu.fatec.poo.view.LoginView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Contexto {

    private static Stage stage;
    private static Scene cena;
    private static Cliente clienteLogado;
    private static Carrinho carrinhoAtivo;

    public static void iniciar(Stage s) {
        stage = s;
    }

    public static void chamaOutraTela(Pane tela, String titulo) {
        if (cena == null) {
            cena = new Scene(tela, 900, 600);
            stage.setTitle(titulo);
            stage.setScene(cena);
            stage.show();
        } else {
            stage.setTitle(titulo);
            cena.setRoot(tela);
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static Cliente getClienteLogado() {
        return clienteLogado;
    }

    public static void setClienteLogado(Cliente c) {
        clienteLogado = c;
    }

    public static Carrinho getCarrinhoAtivo() {
        return carrinhoAtivo;
    }

    public static void setCarrinhoAtivo(Carrinho c) {
        carrinhoAtivo = c;
    }

    public static void sair() {
        clienteLogado = null;
        carrinhoAtivo = null;
        chamaOutraTela(new LoginView(), "Login");
    }
}
