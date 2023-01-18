package org.ifpi.bibliotecaif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.ifpi.bibliotecaif.model.dao.LivroLiterarioDaoImpl;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroDidatico;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;
import org.ifpi.bibliotecaif.model.entities.enums.Assunto;
import org.ifpi.bibliotecaif.model.entities.enums.ClassificacaoIndicativa;
import org.ifpi.bibliotecaif.model.entities.enums.Genero;
import org.ifpi.bibliotecaif.model.services.LivroDidaticoService;
import org.ifpi.bibliotecaif.model.services.LivroLiterarioService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdicionarLivroController implements Initializable {


    private List<Assunto> assuntos = new ArrayList<>();
    private ObservableList<Assunto> obsAssuntos;
    private List<Genero> generos = new ArrayList<>();
    private ObservableList<Genero> obsGeneros;
    private List<ClassificacaoIndicativa> ci = new ArrayList<>();
    private ObservableList<ClassificacaoIndicativa> obsCi;
    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtEditora;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTitulo;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton cbDidatico;

    @FXML
    private RadioButton cbLiterario;

    @FXML
    void onRadioDidaticoChange() {
        cbbAssunto.setDisable(false);
        cbbGenero.setDisable(true);
        cbbClassificacao.setDisable(true);
    }

    @FXML
    void onRadioLiterarioChange() {
        cbbGenero.setDisable(false);
        cbbAssunto.setDisable(true);
        cbbClassificacao.setDisable(false);
    }

    @FXML
    private ComboBox<Assunto> cbbAssunto;

    @FXML
    private ComboBox<Genero> cbbGenero;

    @FXML
    private ComboBox<ClassificacaoIndicativa> cbbClassificacao;

    @FXML
    private Button adicionarButton;

    @FXML
    void onAdicionarButtonAction(ActionEvent event) throws SQLException {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String editora = txtEditora.getText();
        String isbn = txtISBN.getText();
        if (cbLiterario.isSelected()) {
            LivroLiterarioService literarioService = new LivroLiterarioService();
            Genero genero = cbbGenero.getSelectionModel().getSelectedItem();
            ClassificacaoIndicativa ci = cbbClassificacao.getSelectionModel().getSelectedItem();
            LivroLiterario livro = new LivroLiterario(null, titulo, autor, editora, isbn, genero, ci);
            try {
                literarioService.inserirOuAtualizar(livro);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        } else {
            LivroDidaticoService didaticoService = new LivroDidaticoService();
            Assunto assunto = cbbAssunto.getSelectionModel().getSelectedItem();
            LivroDidatico livro = new LivroDidatico(null, titulo, autor, editora, isbn, assunto);
            try {
                didaticoService.inserirOuAtualizar(livro);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtISBN.setText("");
        cbbClassificacao.setValue(ClassificacaoIndicativa.LIVRE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarAssuntos();
        carregarGeneros();
        carregarClassificacao();
        cbbAssunto.setDisable(true);
        cbLiterario.setSelected(true);
        cbbClassificacao.setValue(ClassificacaoIndicativa.LIVRE);
    }

    public void carregarAssuntos() {
        for(Assunto a : Assunto.values()) {
            assuntos.add(a);
        }
        obsAssuntos = FXCollections.observableArrayList(assuntos);
        cbbAssunto.setItems(obsAssuntos);
    }

    public void carregarGeneros() {
        for(Genero a : Genero.values()) {
            generos.add(a);
        }
        obsGeneros = FXCollections.observableArrayList(generos);
        cbbGenero.setItems(obsGeneros);
    }

    public void carregarClassificacao() {
        for(ClassificacaoIndicativa a : ClassificacaoIndicativa.values()) {
            ci.add(a);
        }
        obsCi = FXCollections.observableArrayList(ci);
        cbbClassificacao.setItems(obsCi);
    }

}
