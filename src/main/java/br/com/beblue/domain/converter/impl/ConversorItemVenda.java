package br.com.beblue.domain.converter.impl;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.ItemVenda;
import br.com.beblue.domain.converter.Conversor;
import br.com.beblue.domain.converter.ConversorLista;
import br.com.beblue.domain.dto.DiscoDTO;
import br.com.beblue.domain.dto.ItemVendaDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ConversorItemVenda implements Conversor<ItemVendaDTO, ItemVenda>, ConversorLista<ItemVendaDTO, ItemVenda> {

    private final Conversor<DiscoDTO, Disco> discoConversor;

    public ConversorItemVenda(ConversorDisco discoConversor) {
        this.discoConversor = discoConversor;
    }

    @Override
    public ItemVenda converter(ItemVendaDTO itemVendaDTO) {
        return new ItemVenda()
            .disco(discoConversor.converter(itemVendaDTO.getDisco()))
            .quantidade(itemVendaDTO.getQuantidade());
    }

    @Override
    public List<ItemVenda> converter(List<ItemVendaDTO> listaItensVenda) {
        List<ItemVenda> itensVendaConvertidos = new ArrayList<>();
        listaItensVenda.forEach(itemVendaDTO -> {
            itensVendaConvertidos.add(converter(itemVendaDTO));
        });
        return itensVendaConvertidos;
    }
}
