package br.com.beblue.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumSpotifyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ItemSpotifyDTO> items;

    public AlbumSpotifyDTO() {
        this.items = new ArrayList<>();
    }

    public List<ItemSpotifyDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemSpotifyDTO> items) {
        this.items = items;
    }
}
