package edu.fatec.poo.view;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class UILogin  extends Application{
    public static void main(String args[]){
         launch(args);
    }

    @Override
    public void start(Stage stage){
        GridPane painel = new GridPane();
        painel.setAlignment(Pos.CENTER);

        Scene cena = new Scene(painel, 720, 480 );


        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Senha");
        TextField passworField = new TextField();
        Button Cadastrar = new Button("Cadastrar");
        Button Entrar = new Button("Entrar");

        emailLabel.setFont(new Font(25));
        emailField.setFont(new Font(25));
        passwordLabel.setFont(new Font(25));
        passworField.setFont(new Font(25));
        Cadastrar.setFont(new Font(25));
        Entrar.setFont(new Font(25));
        
        painel.add(emailLabel, 0 ,0);
        painel.add(emailField, 1,0);
        painel.add(passwordLabel, 0 ,1);
        painel.add(passworField, 1,1);
        painel.add(Cadastrar,0,2);
        painel.add(Entrar,1,2);
        
        painel.setHgap(10);
        painel.setVgap(10);
        painel.setStyle("-fx-padding:20;");
        
        Cadastrar.setOnAction(p ->{
         UICadastro cad = new UICadastro();
         cad.start(stage);
            
        });


       // painel.getChildren().addAll(emailLabel, emailField, passwordLabel, passworField );
        
       stage.setTitle("Tela de Login");
        stage.setScene(cena);
        stage.show();



    }
}
