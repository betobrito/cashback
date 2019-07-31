package br.com.beblue.service.impl;

import br.com.beblue.domain.Venda;
import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.repository.VendaRepository;
import br.com.beblue.service.VendaService;
import br.com.beblue.service.calculador.CalculadorCashback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.beblue.util.Constantes.MensagemLog.*;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_PERMITIDO_APENAS_NOVAS_VENDAS;

@Service
@Transactional
public class VendaServiceImpl implements VendaService {

    private final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);

    private final CalculadorCashback calculadorCashback;
    private final VendaRepository vendaRepository;

    public VendaServiceImpl(CalculadorCashback calculadorCashback, VendaRepository vendaRepository) {
        this.calculadorCashback = calculadorCashback;
        this.vendaRepository = vendaRepository;
    }

    @Override
    public Venda realizarVenda(Venda venda) {
        log.debug(MSG_LOG_REST_REQUISICAO_PARA_SALVAR_VENDA, venda);
        validarSeEhUmaVendaNova(venda);
        calculadorCashback.calcular(venda);
        return vendaRepository.save(venda);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Venda> consultarVendasPorPeriodo(ParametroConsultaDTO parametroConsultaDTO) {
        log.debug(MSG_LOG_REST_REQUISICAO_PARA_CONSULTAR_VENDAS);
        return vendaRepository.findVendasByDataVendaBetweenOrderByDataVendaDesc(
                                parametroConsultaDTO.getDataInicial(), parametroConsultaDTO.getDataFinal(),
                                parametroConsultaDTO.getPaginacao());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venda> consultarVenda(Long id) {
        log.debug(REST_REQUISICAO_PARA_CONSULTAR_VENDA_POR_ID, id);
        return vendaRepository.findById(id);
    }

    private void validarSeEhUmaVendaNova(Venda venda) {
        if (venda.getId() != null) {
            throw new RuntimeException(MSG_PERMITIDO_APENAS_NOVAS_VENDAS);
        }
    }
}
