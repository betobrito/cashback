package br.com.beblue.web.rest;

import br.com.beblue.domain.Disco;
import br.com.beblue.service.DiscoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscoResourceTest {

    @Mock
    private DiscoService discoServiceMock;

    private DiscoResource discoResource;
    private Optional<Disco> optionalDisco;
    private Disco disco;
    private List<Disco> discos;
    private Page<Disco> paginacaoDiscos;

    @Before
    public void inicializar() {
        this.discoResource = new DiscoResource(discoServiceMock);
        disco = new Disco();
        this.optionalDisco = Optional.of(disco);
        this.discos = new ArrayList<>();
        discos.add(disco);
        this.paginacaoDiscos = new PageImpl<>(discos);
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscoDelegandoParaOhServicoRetornandoUmDisco() {
        when(discoServiceMock.consultarPorId(1L)).thenReturn(optionalDisco);

        ResponseEntity<Disco> resultado = discoResource.consultarDisco(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertSame(disco, resultado.getBody());
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscoDelegandoParaOhServicoRetornandoNulo() {
        when(discoServiceMock.consultarPorId(1L)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Disco> resultado = discoResource.consultarDisco(1L);

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscosPorGeneroDelegandoParaOhServicoRetornandoListaComDiscos() {
        when(discoServiceMock.consultarDiscosPorGenero(1L, Pageable.unpaged())).thenReturn(paginacaoDiscos);

        ResponseEntity<List<Disco>> resultado = discoResource.consultarDiscosPorGenero(1L, Pageable.unpaged());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(discos, resultado.getBody());
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscosPorGeneroDelegandoParaOhServicoRetornandoListaSemDiscos() {
        atribuirPaginacaoVaziaIhLimpandoListaDiscos();
        when(discoServiceMock.consultarDiscosPorGenero(1L, Pageable.unpaged())).thenReturn(paginacaoDiscos);

        ResponseEntity<List<Disco>> resultado = discoResource.consultarDiscosPorGenero(1L, Pageable.unpaged());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(discos, resultado.getBody());
    }

    private void atribuirPaginacaoVaziaIhLimpandoListaDiscos() {
        this.paginacaoDiscos = Page.empty();
        discos.clear();
    }

}
