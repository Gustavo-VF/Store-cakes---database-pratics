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

    @Override
    public void start(Stage stage)throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/UILogin.fxml"));        
        Scene cena = new Scene(root);
       
        stage.setTitle("Tela de Login");
        stage.setScene(cena);
        stage.show();



    }
}
