package br.com.beblue.domain.dto;

import java.io.Serializable;

public class GrantTypeSpotifyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String grantType;

    public GrantTypeSpotifyDTO() {
        this.grantType = "client_credentials";
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
