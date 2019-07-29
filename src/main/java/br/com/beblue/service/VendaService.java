package br.com.beblue.service;

import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.domain.Venda;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VendaService {

    Venda realizarVenda(Venda venda);

    Page<Venda> consultarVendasPorPeriodo(ParametroConsultaDTO parametroConsultaDTO);

    Optional<Venda> consultarVenda(Long id);

}
