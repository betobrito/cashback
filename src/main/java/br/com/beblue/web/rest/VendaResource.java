package br.com.beblue.web.rest;

import br.com.beblue.domain.Venda;
import br.com.beblue.domain.converter.Conversor;
import br.com.beblue.domain.converter.impl.ConversorVenda;
import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.domain.dto.VendaDTO;
import br.com.beblue.service.VendaService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static br.com.beblue.util.Constantes.Outros.NOME_ENTIDADE_VENDA;
import static br.com.beblue.web.rest.VendaResource.API_VENDAS;

@RestController
@RequestMapping(API_VENDAS)
public class VendaResource {

    public static final String API_VENDAS = "/api/vendas/";

    private final Logger log = LoggerFactory.getLogger(VendaResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendaService vendaService;
    private final Conversor<VendaDTO, Venda> conversor;

    public VendaResource(VendaService vendaService, ConversorVenda conversor) {
        this.vendaService = vendaService;
        this.conversor = conversor;
    }

    @PostMapping
    public ResponseEntity<Venda> realizarVenda(@Valid @RequestBody VendaDTO vendaDTO) throws URISyntaxException {
        log.debug("REST requisição para realizar Venda : {}", vendaDTO);
        Venda vendaRealizada = vendaService.realizarVenda(conversor.converter(vendaDTO));
        return ResponseEntity.created(new URI(API_VENDAS + vendaRealizada.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, NOME_ENTIDADE_VENDA, vendaRealizada.getId().toString()))
            .body(vendaRealizada);
    }

    @PostMapping("/periodo")
    public ResponseEntity<List<Venda>> consultarVendasPorPeriodo(@RequestBody ParametroConsultaDTO parametroConsultaDTO) {
        log.debug("REST requisição para consultar Vendas");
        Page<Venda> vendas = vendaService.consultarVendasPorPeriodo(parametroConsultaDTO);
        return ResponseEntity.ok().body(vendas.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> consultarVenda(@PathVariable Long id) {
        log.debug("REST requisição para consultar Venda por id: {}", id);
        Optional<Venda> venda = vendaService.consultarVenda(id);
        return ResponseUtil.wrapOrNotFound(venda);
    }
}
