package org.ifpi.bibliotecaif.model.services;

import javafx.collections.ObservableList;
import org.ifpi.bibliotecaif.model.dao.DaoFactory;
import org.ifpi.bibliotecaif.model.dao.LivroLiterarioDao;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Serviço da DAO
 */
public class LivroLiterarioService
{
    private LivroLiterarioDao dao;

    {
        try {
            dao = DaoFactory.criarLivroLiterarioDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Livro> buscarTodos() throws SQLException {
        return dao.findAll();
    }

    public void inserirOuAtualizar(LivroLiterario livro) throws SQLException {
        if (livro.getId() == null) {
            dao.insert(livro);
        } else {
            dao.update(livro);
        }
    }

    public void deletar(LivroLiterario livro) throws SQLException {
        dao.deleteById(livro.getId());
    }
}
