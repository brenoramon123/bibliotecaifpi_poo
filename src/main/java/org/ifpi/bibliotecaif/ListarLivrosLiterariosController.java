package org.ifpi.bibliotecaif;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;
import org.ifpi.bibliotecaif.model.entities.enums.ClassificacaoIndicativa;
import org.ifpi.bibliotecaif.model.entities.enums.Genero;
import org.ifpi.bibliotecaif.model.services.LivroLiterarioService;
import org.ifpi.bibliotecaif.utils.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListarLivrosLiterariosController implements Initializable {

    @FXML
    private TableView<Livro> tableViewLivro;

    @FXML
    private TableColumn<LivroLiterario, String> tableColumnId;
    @FXML
    private TableColumn<LivroLiterario, String> tableColumnTitulo;
    @FXML
    private TableColumn<LivroLiterario, String> tableColumnAutor;
    @FXML
    private TableColumn<LivroLiterario, String> tableColumnEditora;
    @FXML
    private TableColumn<LivroLiterario, String> tableColumnIsbn;
    @FXML
    private TableColumn<LivroLiterario, Genero> tableColumnGenero;
    @FXML
    private TableColumn<LivroLiterario, ClassificacaoIndicativa> tableColumnClassificacao;

    @FXML
    private Button mudarTipoButton;

    @FXML
    public void onMudarTipoButton() {
        loadView("LivrosDidaticosLista.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeNodes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeNodes() throws SQLException {

        LivroLiterarioService service = new LivroLiterarioService();

        ObservableList<Livro> lista = service.buscarTodos();

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        tableColumnEditora.setCellValueFactory(new PropertyValueFactory<>("Editora"));
        tableColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("Isbn"));
        tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("Genero"));
        tableColumnClassificacao.setCellValueFactory(new PropertyValueFactory<>("ClassificacaoIndicativa"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewLivro.prefHeightProperty().bind(stage.heightProperty());
        tableViewLivro.setItems(lista);
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
