package org.ifpi.bibliotecaif.model.dao;

import org.ifpi.bibliotecaif.factory.DbConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Classe que injeta as dependências e instancia o banco de dados
 */
public class DaoFactory {

    public static LivroLiterarioDao criarLivroLiterarioDao() throws SQLException, IOException {
        return new LivroLiterarioDaoImpl(DbConnectionFactory.conectar());
    }

    public static LivroDidaticoDao criarLivroDidaticoDao() throws SQLException, IOException {
        return new LivroDidaticoDaoImpl(DbConnectionFactory.conectar());
    }
}
