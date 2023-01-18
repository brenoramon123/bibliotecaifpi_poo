package org.ifpi.bibliotecaif.model.entities;

import org.ifpi.bibliotecaif.model.entities.enums.Assunto;


/**
 * Classe do tipo Livro Didático
 */
public class LivroDidatico extends Livro {

    private Assunto assunto;

    public LivroDidatico(Integer id, String titulo, String autor, String editora, String isbn, Assunto assunto) {
        super(id, titulo, autor, editora, isbn);
        this.assunto = assunto;
    }

    public LivroDidatico(String titulo, String autor, String editora, Assunto assunto) {
        super(titulo, autor, editora);
        this.assunto = assunto;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
