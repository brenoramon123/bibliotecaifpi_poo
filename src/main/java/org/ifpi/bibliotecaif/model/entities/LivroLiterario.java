package org.ifpi.bibliotecaif.model.entities;

import org.ifpi.bibliotecaif.model.entities.enums.ClassificacaoIndicativa;
import org.ifpi.bibliotecaif.model.entities.enums.Genero;

/**
 * Classe do tipo Livro Literário
 */
public class LivroLiterario extends Livro {
    private Genero genero;
    private ClassificacaoIndicativa classificacaoIndicativa;

    public LivroLiterario(Integer id, String titulo, String autor, String editora, String isbn, Genero genero, ClassificacaoIndicativa classificacaoIndicativa) {
        super(id, titulo, autor, editora, isbn);
        this.genero = genero;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public ClassificacaoIndicativa getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(ClassificacaoIndicativa classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
