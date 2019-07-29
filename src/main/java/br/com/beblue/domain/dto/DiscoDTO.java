package br.com.beblue.domain.dto;

import java.io.Serializable;
import java.util.Objects;

public class DiscoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private GeneroDTO genero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeneroDTO getGenero() {
        return genero;
    }

    public void setGenero(GeneroDTO genero) {
        this.genero = genero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscoDTO discoDTO = (DiscoDTO) o;
        return Objects.equals(id, discoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
