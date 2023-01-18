package org.ifpi.bibliotecaif.model.dao;

import javafx.collections.ObservableList;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;

import java.sql.SQLException;
import java.util.List;


/**
 * Interface que define como a DAO deve ser implementada
 */
public interface LivroLiterarioDao {

    void insert(LivroLiterario livro) throws SQLException;
    void update(LivroLiterario livro) throws SQLException;
    void deleteById(Integer id) throws SQLException;
    ObservableList<Livro> findAll() throws SQLException;
}
