package org.ifpi.bibliotecaif;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.ifpi.bibliotecaif.utils.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemAdicionar;

    @FXML
    private MenuItem menuItemEditar;

    @FXML
    private MenuItem menuItemVerTodos;

    @FXML
    private MenuItem menuItemSobre;
    @FXML
    public void onMenuItemAdicionar() {
        loadView("AdicionarLivro.fxml");
    }

    @FXML
    public void onMenuItemEditar() {
        loadView("EditarLivro.fxml");
    }

    @FXML
    public void onMenuItemVerTodos() {
        loadView("LivrosLiterariosLista.fxml");
    }

    @FXML
    public void onMenuItemSobre() {
        loadView("Sobre.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized void loadView(String absName) {
        try {
            URL fxmlLocation = getClass().getResource(absName);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            AnchorPane newPane = loader.load();
            Scene mainScene = Main.getMainScene();
            AnchorPane mainPane = (AnchorPane) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainPane.getChildren().get(0);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(mainMenu);
            mainPane.getChildren().addAll(newPane.getChildren());
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Erro ao carregar view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
