package org.ifpi.bibliotecaif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import java.util.List;
import java.util.ResourceBundle;

public class EditarLivroController implements Initializable {


    private List<Livro> livros = new ArrayList<>();
    private ObservableList<Livro> obsLivros;
    private List<Assunto> assuntos = new ArrayList<>();
    private ObservableList<Assunto> obsAssuntos;
    private List<Genero> generos = new ArrayList<>();
    private ObservableList<Genero> obsGeneros;
    private List<ClassificacaoIndicativa> ci = new ArrayList<>();
    private ObservableList<ClassificacaoIndicativa> obsCi;

    @FXML
    private Button atualizarButton;

    @FXML
    private Button deletarButton;

    @FXML
    private ToggleGroup tipo;

    @FXML
    private RadioButton cbDidatico;

    @FXML
    private RadioButton cbLiterario;

    @FXML
    void onRadioDidaticoChange() throws SQLException {
        cbbAssunto.setDisable(false);
        cbbGenero.setDisable(true);
        cbbClassificacao.setDisable(true);
        carregarLivrosDidaticos();
    }

    @FXML
    void onRadioLiterarioChange() throws SQLException {
        cbbGenero.setDisable(false);
        cbbAssunto.setDisable(true);
        cbbClassificacao.setDisable(false);
        carregarLivrosLiterarios();
    }

    @FXML
    private ComboBox<ClassificacaoIndicativa> cbbClassificacao;

    @FXML
    private ComboBox<Genero> cbbGenero;

    @FXML
    private ComboBox<Assunto> cbbAssunto;

    @FXML
    private ComboBox<Livro> cbbLivrosId;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtEditora;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtTitulo;

    @FXML
    void onAtualizarButtonAction(ActionEvent event) throws SQLException {
        Integer id = cbbLivrosId.getSelectionModel().getSelectedItem().getId();
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String editora = txtEditora.getText();
        String isbn = txtIsbn.getText();
        if (cbLiterario.isSelected()) {
            LivroLiterarioService literarioService = new LivroLiterarioService();
            Genero genero = cbbGenero.getSelectionModel().getSelectedItem();
            ClassificacaoIndicativa ci = cbbClassificacao.getSelectionModel().getSelectedItem();
            LivroLiterario livro = new LivroLiterario(id, titulo, autor, editora, isbn, genero, ci);
            try {
                literarioService.inserirOuAtualizar(livro);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        } else {
            LivroDidaticoService didaticoService = new LivroDidaticoService();
            Assunto assunto = cbbAssunto.getSelectionModel().getSelectedItem();
            LivroDidatico livro = new LivroDidatico(id, titulo, autor, editora, isbn, assunto);
            try {
                didaticoService.inserirOuAtualizar(livro);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        resetar();
        }

    @FXML
    void onDeletarButtonAction(ActionEvent event) throws SQLException {
        if (cbLiterario.isSelected()) {
            LivroLiterario livro = (LivroLiterario) cbbLivrosId.getSelectionModel().getSelectedItem();
            LivroLiterarioService service = new LivroLiterarioService();
            service.deletar(livro);
        } else {
            LivroDidatico livro = (LivroDidatico) cbbLivrosId.getSelectionModel().getSelectedItem();
            LivroDidaticoService service = new LivroDidaticoService();
            service.deletar(livro);
        }
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtIsbn.setText("");
        cbbAssunto.setDisable(true);
        cbLiterario.setSelected(true);
        cbbClassificacao.setValue(ClassificacaoIndicativa.LIVRE);
        carregarLivrosLiterarios();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            carregarLivrosLiterarios();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        carregarClassificacao();
        carregarGeneros();
        carregarAssuntos();
        cbbAssunto.setDisable(true);
        cbLiterario.setSelected(true);
        cbbClassificacao.setValue(ClassificacaoIndicativa.LIVRE);
    }
    public void carregarGeneros() {
        for(Genero a : Genero.values()) {
            generos.add(a);
        }
        obsGeneros = FXCollections.observableArrayList(generos);
        cbbGenero.setItems(obsGeneros);
    }

    public void carregarAssuntos() {
        for(Assunto a : Assunto.values()) {
            assuntos.add(a);
        }
        obsAssuntos = FXCollections.observableArrayList(assuntos);
        cbbAssunto.setItems(obsAssuntos);
    }

    public void carregarClassificacao() {
        for(ClassificacaoIndicativa a : ClassificacaoIndicativa.values()) {
            ci.add(a);
        }
        obsCi = FXCollections.observableArrayList(ci);
        cbbClassificacao.setItems(obsCi);
    }

    public void carregarLivrosLiterarios() throws SQLException {
        LivroLiterarioService literarioService = new LivroLiterarioService();
        ObservableList<Livro> listaLiterario = literarioService.buscarTodos();

        if (livros != null && !livros.isEmpty()) {
            livros.clear();
        }
        if (obsLivros != null) {
            obsLivros.clear();
        }

        for(Livro l : listaLiterario) {
            livros.add(l);
        }
        obsLivros = FXCollections.observableArrayList(livros);
        cbbLivrosId.setItems(obsLivros);
    }

    public void carregarLivrosDidaticos() throws SQLException {
        LivroDidaticoService didaticoService = new LivroDidaticoService();
        ObservableList<Livro> listaDidatico = didaticoService.buscarTodos();

        if (livros != null && !livros.isEmpty()) {
            livros.clear();
        }
        if (obsLivros != null) {
            obsLivros.clear();
        }
        for(Livro l : listaDidatico) {
            livros.add(l);
        }
        obsLivros = FXCollections.observableArrayList(livros);
        cbbLivrosId.setItems(obsLivros);
    }

    public void resetar() throws SQLException {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtIsbn.setText("");
        cbbAssunto.setDisable(true);
        cbLiterario.setSelected(true);
        cbbClassificacao.setValue(ClassificacaoIndicativa.LIVRE);
        carregarLivrosLiterarios();
    }
}
