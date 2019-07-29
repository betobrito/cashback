package br.com.beblue.domain.dto;

import java.io.Serializable;

public class GeneroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
