package br.com.beblue.web.rest;

import br.com.beblue.domain.Disco;
import br.com.beblue.service.DiscoService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_REQUISICAO_PARA_CONSULTAR_DISCOS_POR_GENERO;
import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_REQUISICAO_PARA_RECUPERAR_DISCO_COM_ID;
import static br.com.beblue.util.Constantes.Outros.PARAMETRO_ID_GENERO;

@RestController
@RequestMapping("/api/discos")
public class DiscoResource {

    private final Logger log = LoggerFactory.getLogger(DiscoResource.class);

    private final DiscoService discoService;

    public DiscoResource(DiscoService discoService) {
        this.discoService = discoService;
    }

    @GetMapping("/genero")
    public ResponseEntity<List<Disco>> consultarDiscosPorGenero(@PathParam(PARAMETRO_ID_GENERO) Long idGenero, Pageable pageable) {
        log.debug(MSG_LOG_REQUISICAO_PARA_CONSULTAR_DISCOS_POR_GENERO, idGenero);
        Page<Disco> page = discoService.consultarDiscosPorGenero(idGenero, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disco> consultarDisco(@PathVariable Long id) {
        log.debug(MSG_LOG_REQUISICAO_PARA_RECUPERAR_DISCO_COM_ID, id);
        Optional<Disco> disco = discoService.consultarPorId(id);
        return ResponseUtil.wrapOrNotFound(disco);
    }
}
