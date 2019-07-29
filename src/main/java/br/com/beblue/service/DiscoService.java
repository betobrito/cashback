package br.com.beblue.service;

import br.com.beblue.domain.Disco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DiscoService {

    Disco salvar(Disco disco);

    Page<Disco> consultarDiscosPorGenero(Long idGenero, Pageable pageable);

    Optional<Disco> consultarPorId(Long id);

}
