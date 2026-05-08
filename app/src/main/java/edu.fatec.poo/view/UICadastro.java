package edu.fatec.poo.view;

import javafx.stage.Stage;

import javafx.event.Event;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class UICadastro  extends Application{
    public static void main(String args[]){
         launch(args);
    }

    @Override
    public void start(Stage stage){
        GridPane painel = new GridPane();
        painel.setAlignment(Pos.CENTER);

        Scene cena = new Scene(painel, 720, 480 );

        Label nomelabel = new Label("Nome");
        TextField nomField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Senha");
        TextField passworField = new TextField();
        
        
        Button Voltar = new Button("Voltar");
        Button Entrar = new Button("Entrar");

        nomelabel.setFont(new Font(25));
        nomField.setFont(new Font(25));
        emailLabel.setFont(new Font(25));
        emailField.setFont(new Font(25));
        passwordLabel.setFont(new Font(25));
        passworField.setFont(new Font(25));
        Voltar.setFont(new Font(25));
        Entrar.setFont(new Font(25));

        
       

        
        painel.add(nomelabel, 0, 0);
        painel.add(nomField, 1, 0);
        painel.add(emailLabel, 0 ,1);
        painel.add(emailField, 1,1);
        painel.add(passwordLabel, 0 ,2);
        painel.add(passworField, 1,2);
        painel.add(Voltar,0,3);
        painel.add(Entrar,1,3);
        
        painel.setHgap(10);
        painel.setVgap(10);
        painel.setStyle("-fx-padding:20;");
        


       // painel.getChildren().addAll(emailLabel, emailField, passwordLabel, passworField );
        
       stage.setTitle("Tela de Tela de Cadastro");
        stage.setScene(cena);
        stage.show();



    }
}
