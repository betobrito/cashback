package br.com.beblue.domain.dto;

import java.io.Serializable;

public class GeneroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String descricao;

    private String idSpotify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdSpotify() {
        return idSpotify;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }
}
