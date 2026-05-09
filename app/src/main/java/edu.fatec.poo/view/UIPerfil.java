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

public class UIPerfil extends Application {
    public static void main(String args[]){
        launch(args);
    }

    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/UIPerfil.fxml"));        

        Scene cena = new Scene(root);

        stage.setTitle("Perfil");
        stage.setScene(cena);
        stage.show();

    }




    
}
