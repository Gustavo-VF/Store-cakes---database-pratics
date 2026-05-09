package edu.fatec.poo.controller; 

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Label btn_Cadastro;

    @FXML
    void TelaCadastro(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UICadastro.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de cadastro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}