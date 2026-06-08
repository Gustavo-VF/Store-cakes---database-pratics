package edu.fatec.poo;

import edu.fatec.poo.persistence.sqlServer.ConfiguredSqlConnector;
import edu.fatec.poo.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new ConfiguredSqlConnector().buildDb();
        Contexto.iniciar(stage);
        Contexto.chamaOutraTela(new LoginView(), "Login");
    }

}
