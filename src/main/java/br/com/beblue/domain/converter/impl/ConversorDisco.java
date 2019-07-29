package br.com.beblue.domain.converter.impl;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Genero;
import br.com.beblue.domain.ItemVenda;
import br.com.beblue.domain.converter.Conversor;
import br.com.beblue.domain.converter.ConversorLista;
import br.com.beblue.domain.dto.DiscoDTO;
import br.com.beblue.domain.dto.GeneroDTO;
import br.com.beblue.domain.dto.ItemVendaDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ConversorDisco implements Conversor<DiscoDTO, Disco> {

    @Override
    public Disco converter(DiscoDTO discoDTO) {
        return new Disco()
            .id(discoDTO.getId());
    }
}
