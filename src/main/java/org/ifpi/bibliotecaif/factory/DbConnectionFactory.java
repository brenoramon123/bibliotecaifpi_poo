package org.ifpi.bibliotecaif.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


// Classe para instanciar uma conexão do banco de dados MySQL
public class DbConnectionFactory {

    private static Connection conn = null;


    // Método que conecta ao banco de dados
    public static Connection conectar() throws IOException, SQLException {
        if (conn == null) {
            Properties props = carregarPropriedades();
            String url = props.getProperty("dburl");
            try {
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return conn;
    }


    // Método que termina a conexão com o banco de dados
    public static void fecharConexao() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // Método que encerra a declaração SQL
    public static void fecharStmt(Statement stmt) throws SQLException {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException();
            }
        }
    }


    // Método que encerra o Result Set
    public static void fecharResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException();
            }
        }
    }

    private static Properties carregarPropriedades() throws IOException {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties propriedades = new Properties();
            propriedades.load(fs);
            return propriedades;
        } catch (IOException e) {
            throw new IOException("Falha ao localizar o arquivo contendo as propriedades.");
        }
    }
}
