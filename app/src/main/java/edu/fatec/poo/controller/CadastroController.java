package edu.fatec.poo.controller; 

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class CadastroController {

    @FXML
    private Button btn_Voltar;

    @FXML
    void VoltarLogin(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UILogin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}