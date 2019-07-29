package br.com.beblue.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @Size(min = 1)
    private List<ItemVendaDTO> itensVenda;

    public VendaDTO() {
        this.itensVenda = new ArrayList<>();
    }

    public List<ItemVendaDTO> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVendaDTO> itensVenda) {
        this.itensVenda = itensVenda;
    }

}
