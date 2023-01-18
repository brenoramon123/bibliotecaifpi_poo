package org.ifpi.bibliotecaif.model.services;

import javafx.collections.ObservableList;
import org.ifpi.bibliotecaif.model.dao.DaoFactory;
import org.ifpi.bibliotecaif.model.dao.LivroDidaticoDao;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroDidatico;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Serviço da DAO
 */
public class LivroDidaticoService
{
    private LivroDidaticoDao dao;

    {
        try {
            dao = DaoFactory.criarLivroDidaticoDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Livro> buscarTodos() throws SQLException {
        return dao.findAll();
    }

    public void inserirOuAtualizar(LivroDidatico livro) throws SQLException {
        if (livro.getId() == null) {
            dao.insert(livro);
        } else {
            dao.update(livro);
        }
    }
    
    public void deletar(LivroDidatico livro) throws SQLException {
        dao.deleteById(livro.getId());
    }
}
