package br.com.beblue.domain.converter.impl;

import br.com.beblue.domain.ItemVenda;
import br.com.beblue.domain.Venda;
import br.com.beblue.domain.converter.Conversor;
import br.com.beblue.domain.converter.ConversorLista;
import br.com.beblue.domain.dto.ItemVendaDTO;
import br.com.beblue.domain.dto.VendaDTO;
import org.springframework.stereotype.Component;

@Component
public class ConversorVenda implements Conversor<VendaDTO, Venda> {

    private final ConversorLista<ItemVendaDTO, ItemVenda> conversorItemVenda;

    public ConversorVenda(ConversorItemVenda conversorItemVenda) {
        this.conversorItemVenda = conversorItemVenda;
    }

    @Override
    public Venda converter(VendaDTO vendaDTO) {
        return new Venda().id(vendaDTO.getId()).itensVenda(conversorItemVenda.converter(vendaDTO.getItensVenda()));
    }

}
