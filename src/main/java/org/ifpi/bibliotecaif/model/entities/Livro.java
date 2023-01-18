package org.ifpi.bibliotecaif.model.entities;

import org.ifpi.bibliotecaif.model.entities.enums.Assunto;
import org.ifpi.bibliotecaif.model.entities.enums.ClassificacaoIndicativa;
import org.ifpi.bibliotecaif.model.entities.enums.Genero;

/**
 * Classe abstrata que extende as classes filhas com atributos comuns entre elas
 */
public abstract class Livro {

    private Integer id;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;

    public Livro(Integer id, String titulo, String autor, String editora) {

    }

    public Livro(Integer id, String titulo, String autor, String editora, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
    }

    public Livro(String titulo, String autor, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - TITULO: " + titulo;
    }
}
