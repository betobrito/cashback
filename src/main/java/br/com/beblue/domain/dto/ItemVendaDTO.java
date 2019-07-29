package br.com.beblue.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ItemVendaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private DiscoDTO disco;

    private VendaDTO venda;

    @Min(value = 1)
    private Integer quantidade;

    public DiscoDTO getDisco() {
        return disco;
    }

    public void setDisco(DiscoDTO disco) {
        this.disco = disco;
    }

    public VendaDTO getVenda() {
        return venda;
    }

    public void setVenda(VendaDTO venda) {
        this.venda = venda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVendaDTO that = (ItemVendaDTO) o;
        return Objects.equals(disco, that.disco) &&
            Objects.equals(venda, that.venda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disco, venda);
    }

}
