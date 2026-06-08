package edu.fatec.poo.view;

import edu.fatec.poo.Contexto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SobreLojaView extends VBox {

    public SobreLojaView() {
        setSpacing(0);
        setPrefSize(900, 600);

        HBox top = new HBox(12);
        top.setPadding(new Insets(10, 16, 10, 16));
        top.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("LOGO");

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar");
        txtPesquisa.setPrefWidth(180);

        Label titulo = new Label("Sobre a Loja");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        HBox.setHgrow(titulo, Priority.ALWAYS);
        titulo.setMaxWidth(Double.MAX_VALUE);
        titulo.setAlignment(Pos.CENTER);

        Button btnMenu = new Button("☰");
        btnMenu.setOnAction(e -> {
            ContextMenu menu = new ContextMenu();

            MenuItem itemInicio = new MenuItem("Início");
            MenuItem itemMinhaConta = new MenuItem("Minha Conta");
            MenuItem itemCarrinho = new MenuItem("Carrinho");
            MenuItem itemPedidos = new MenuItem("Últimos Pedidos");
            MenuItem itemSobreLoja = new MenuItem("Sobre a Loja");
            MenuItem itemSair = new MenuItem("Sair");

            itemInicio.setOnAction(ev -> Contexto.chamaOutraTela(new InicioView(), "Início"));
            itemMinhaConta.setOnAction(ev -> Contexto.chamaOutraTela(new MinhaContaView(), "Minha Conta"));
            itemCarrinho.setOnAction(ev -> Contexto.chamaOutraTela(new CarrinhoView(), "Carrinho"));
            itemPedidos.setOnAction(ev -> Contexto.chamaOutraTela(new PedidoView(), "Meus Pedidos"));
            itemSobreLoja.setOnAction(ev -> Contexto.chamaOutraTela(new SobreLojaView(), "Sobre a Loja"));
            itemSair.setOnAction(ev -> {
                Contexto.sair();
                Contexto.chamaOutraTela(new LoginView(), "Login");
            });

            menu.getItems().addAll(
                    itemInicio, itemMinhaConta, itemCarrinho,
                    itemPedidos, itemSobreLoja, itemSair);

            menu.show(btnMenu, Side.BOTTOM, 0, 0);
        });

        top.getChildren().addAll(logo, txtPesquisa, titulo, btnMenu);

        HBox corpo = new HBox(0);
        VBox.setVgrow(corpo, Priority.ALWAYS);

        VBox esquerda = new VBox(16);
        esquerda.setPadding(new Insets(30));
        HBox.setHgrow(esquerda, Priority.ALWAYS);

        Label lblSobre = new Label("Sobre a Loja");
        lblSobre.setFont(Font.font("System", FontWeight.BOLD, 18));

        Label txtSobre = new Label(
                "Bem-vindo à nossa loja de bolos artesanais! " +
                        "Fazemos bolos com ingredientes selecionados e muito carinho. " +
                        "Trabalhamos com encomendas e também temos opções prontas para entrega. " +
                        "Nossa missão é adoçar momentos especiais da sua vida.");
        txtSobre.setWrapText(true);
        txtSobre.setFont(Font.font(13));

        Separator sep = new Separator();

        Label lblContato = new Label("Contato");
        lblContato.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblTelefone = new Label(" (00) 90000-0000");
        Label lblEmail = new Label(" loja@email.com");
        Label lblEndereco = new Label(" Rua dos bobos, 000 - São Paulo/SP");

        esquerda.getChildren().addAll(
                lblSobre, txtSobre, sep,
                lblContato, lblTelefone, lblEmail, lblEndereco);

        VBox sobreChef = new VBox(10);
        sobreChef.setPadding(new Insets(16));

        sobreChef.setPrefWidth(550);
        sobreChef.setMinWidth(250);

        Label tituloChef = new Label("Conheça o chef!");
        tituloChef.setMaxWidth(Double.MAX_VALUE);
        tituloChef.setAlignment(Pos.CENTER);

        Label foto = new Label("Foto \n Aqui");
        foto.setPrefSize(50, 50);
        foto.setAlignment(Pos.CENTER);
        foto.setFont(Font.font("System", 40));
        foto.setStyle("-fx-border-color: #aaa;");
        foto.setMaxWidth(Double.MAX_VALUE);

        Label nomeChef = new Label("Fulano de Tal");
        Label descricaoChef = new Label("Descrição/Curiosidades \nsobre o(a) chef.");
        Label certificadosChef = new Label("- Certificados");
        Label eperienciasChef = new Label("- Experiências");
        Label contatoChef = new Label("Contato");
        Label telefoneChef = new Label("(00) 90000-0000");
        Label emailChef = new Label("Fulano@email.com");

        sobreChef.getChildren().addAll(
                tituloChef, foto, nomeChef, descricaoChef,
                certificadosChef, eperienciasChef,
                contatoChef, telefoneChef, emailChef);
        corpo.getChildren().addAll(esquerda, sobreChef);

        getChildren().addAll(top, corpo);
    }
}