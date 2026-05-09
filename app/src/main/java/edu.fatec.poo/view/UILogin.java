package edu.fatec.poo.view;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class UILogin  extends Application{
    public static void main(String args[]){
         launch(args);
    }

    @FXML
private Label btn_Cadastro;

@FXML // Adicione o @FXML para o Scene Builder reconhecer o método
void TelaCadastro(MouseEvent event) { // O parâmetro 'event' é obrigatório aqui
    try {
        Parent loader = FXMLLoader.load(getClass().getResource("/UICadastro.fxml"));
        
        // Agora o 'event' e o 'Node' vão funcionar
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(new Scene(loader));
        stage.show();
        
    } catch (Exception e) {
        System.out.println("erro ao carregar a tela");
    }
}

    @Override
    public void start(Stage stage)throws Exception{
        
    Parent root = FXMLLoader.load(getClass().getResource("/UILogin.fxml"));        
        Scene cena = new Scene(root);
       
        stage.setTitle("Tela de Login");
        stage.setScene(cena);
        stage.show();



    }
}
