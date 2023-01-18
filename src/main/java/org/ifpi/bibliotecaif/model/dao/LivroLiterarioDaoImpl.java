package org.ifpi.bibliotecaif.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ifpi.bibliotecaif.factory.DbConnectionFactory;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroLiterario;
import org.ifpi.bibliotecaif.model.entities.enums.ClassificacaoIndicativa;
import org.ifpi.bibliotecaif.model.entities.enums.Genero;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Implementação da interface DAO
 */
public class LivroLiterarioDaoImpl implements LivroLiterarioDao {

    private Connection connection;

    // Instanciando uma conexão com o banco de dados no construtor
    public LivroLiterarioDaoImpl(Connection connection) {
        this.connection = connection;
    }

    // Método que insere um livro literário no banco de dados.
    @Override
    public void insert(LivroLiterario livro) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO livros_literarios "
                    + "(titulo, autor, editora, isbn, "
                    + "genero, classificacao_indicativa) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setString(5, livro.getGenero().toString());
            stmt.setString(6, livro.getClassificacaoIndicativa().toString());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    livro.setId(id);
                }
                DbConnectionFactory.fecharResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnectionFactory.fecharStmt(stmt);

        }
    }


    // Método que modifica um item do banco de dados baseado no id
    @Override
    public void update(LivroLiterario livro) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE livros_literarios " +
                            "SET titulo = IFNULL(NULLIF(?, ''), titulo), " +
                            "autor = IFNULL(NULLIF(?, ''), autor), " +
                            "editora = IFNULL(NULLIF(?, ''), editora), " +
                            "isbn = IFNULL(NULLIF(?, ''), isbn), " +
                            "genero = IFNULL(NULLIF(?, ''), genero), " +
                            "classificacao_indicativa = IFNULL(NULLIF(?, ''), classificacao_indicativa) " +
                            "WHERE id = ?");
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setString(5, livro.getGenero() == null ? null : livro.getGenero().toString());
            stmt.setString(6, livro.getClassificacaoIndicativa() == null ? null : livro.getClassificacaoIndicativa().toString());
            stmt.setInt(7, livro.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnectionFactory.fecharStmt(stmt);
        }
    }


    // Método que deleta um item do banco de dados com base em seu id
    @Override
    public void deleteById(Integer id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM livros_literarios WHERE id = ?"
            );
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnectionFactory.fecharStmt(stmt);
        }
    }

    // Método que busca todos os livros literários do banco de dados
    @Override
    public ObservableList<Livro> findAll() throws SQLException {
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList();
        PreparedStatement stmt;
        ResultSet rs;
        stmt = connection.prepareStatement("SELECT * FROM livros_literarios");
        rs = stmt.executeQuery();
        Livro livro;
        while(rs.next()) {
            livro = new LivroLiterario(rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editora"),
                    rs.getString("isbn"),
                    Genero.valueOf(rs.getString("genero")),
                    ClassificacaoIndicativa.valueOf(rs.getString("classificacao_indicativa")));
            listaLivros.add(livro);
        }
        DbConnectionFactory.fecharStmt(stmt);
        DbConnectionFactory.fecharResultSet(rs);
        return listaLivros;
    }
}
