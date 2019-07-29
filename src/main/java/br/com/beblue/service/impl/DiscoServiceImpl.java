package br.com.beblue.service.impl;

import br.com.beblue.domain.Disco;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.service.DiscoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.beblue.util.Constantes.MensagemLog.*;

@Service
@Transactional
public class DiscoServiceImpl implements DiscoService {

    private final Logger log = LoggerFactory.getLogger(DiscoServiceImpl.class);

    private final DiscoRepository discoRepository;

    public DiscoServiceImpl(DiscoRepository discoRepository) {
        this.discoRepository = discoRepository;
    }

    @Override
    public Disco salvar(Disco disco) {
        log.debug(MSG_LOG_REQUISICAO_PARA_SALVAR_O_DISCO, disco);
        return discoRepository.save(disco);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Disco> consultarDiscosPorGenero(Long idGenero, Pageable pageable) {
        log.debug(MSG_LOG_REQUISICAO_PARA_CONSULTAR_DISCOS_POR_GENERO, idGenero);
        return discoRepository.findDiscosByGenero_IdOrderByDescricaoAsc(idGenero, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Disco> consultarPorId(Long id) {
        log.debug(MSG_LOG_REQUISICAO_PARA_RECUPERAR_DISCO_COM_ID, id);
        return discoRepository.findById(id);
    }
}
