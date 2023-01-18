package org.ifpi.bibliotecaif.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ifpi.bibliotecaif.factory.DbConnectionFactory;
import org.ifpi.bibliotecaif.model.entities.Livro;
import org.ifpi.bibliotecaif.model.entities.LivroDidatico;
import org.ifpi.bibliotecaif.model.entities.enums.Assunto;

import java.io.IOException;
import java.sql.*;
import java.util.List;


/**
 * Implementação da interface DAO
 */
public class LivroDidaticoDaoImpl implements LivroDidaticoDao {


    private Connection connection;

    // Instanciando uma conexão com o banco de dados no construtor
    public LivroDidaticoDaoImpl(Connection connection) {
        this.connection = connection;
    }


    // Método que insere um livro didático no banco de dados.
    @Override
    public void insert(LivroDidatico livro) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO livros_didaticos "
                    + "(titulo, autor, editora, isbn, "
                    + "assunto) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setString(5, livro.getAssunto().toString());


            // Gerando um id auto incrementado pelo banco de dados
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
    public void update(LivroDidatico livro) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE livros_literarios " +
                            "SET titulo = IFNULL(NULLIF(?, ''), titulo), " +
                            "SET autor = IFNULL(NULLIF(?, ''), autor)" +
                            "SET editora = IFNULL(NULLIF(?, ''), editora)" +
                            "SET isbn = IFNULL(NULLIF(?, ''), isbn)" +
                            "SET assunto = IFNULL(NULLIF(?, ''), assunto)" +
                            "WHERE id = ?");
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setString(5, livro.getAssunto() == null ? null : livro.getAssunto().toString());
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
                    "DELETE FROM livros_didaticos WHERE id = ?"
            );
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnectionFactory.fecharStmt(stmt);
        }
    }


    // Método que busca todos os livros didáticos do banco de dados
    @Override
    public ObservableList<Livro> findAll() throws SQLException {
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList();
        PreparedStatement stmt;
        ResultSet rs;
        stmt = connection.prepareStatement("SELECT * FROM livros_didaticos");
        rs = stmt.executeQuery();
        Livro livro;
        while(rs.next()) {
            livro = new LivroDidatico(rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editora"),
                    rs.getString("isbn"),
                    Assunto.valueOf(rs.getString("assunto")));
            listaLivros.add(livro);
        }
        DbConnectionFactory.fecharStmt(stmt);
        DbConnectionFactory.fecharResultSet(rs);
        return listaLivros;
    }
}
