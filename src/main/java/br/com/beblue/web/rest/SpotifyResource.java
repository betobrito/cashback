package br.com.beblue.web.rest;

import br.com.beblue.service.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_REQUISICAO_PARA_ALIMENTAR_BASE_DE_DISCOS_POR_GENERO_CONSUMINDO_DO_SPOTIFY;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_BASE_ALIMENTADA_COM_SUCESSO;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyResource {

    private final Logger log = LoggerFactory.getLogger(SpotifyResource.class);

    private final SpotifyService spotifyService;

    public SpotifyResource(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping
    public ResponseEntity<String> alimentarBaseDiscosPorGenero() {
        log.debug(MSG_LOG_REQUISICAO_PARA_ALIMENTAR_BASE_DE_DISCOS_POR_GENERO_CONSUMINDO_DO_SPOTIFY);
        spotifyService.alimentarBaseDiscosPorGenero();
        return ResponseEntity.ok().body(MSG_BASE_ALIMENTADA_COM_SUCESSO);
    }
}
