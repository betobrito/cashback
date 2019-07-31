package br.com.beblue.service;

import br.com.beblue.domain.Disco;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.service.impl.DiscoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.beblue.shared.ConstantesTeste.ID_UM;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscoServiceTest {

    @Mock
    private DiscoRepository discoRepositoryMock;

    private DiscoService discoService;
    private Optional<Disco> optionalDisco;
    private Disco disco;
    private List<Disco> discos;
    private Page<Disco> paginacaoDiscos;

    @Before
    public void inicializar() {
        this.discoService = new DiscoServiceImpl(discoRepositoryMock);
        disco = new Disco();
        this.optionalDisco = Optional.of(disco);
        this.discos = new ArrayList<>();
        discos.add(disco);
        this.paginacaoDiscos = new PageImpl<>(discos);
    }

    @Test
    public void deveriaChamarMetodoSalvarDiscoDelegandoParaOhRepositorio() {
        when(discoRepositoryMock.save(disco)).thenReturn(disco);

        Disco retorno = discoService.salvar(this.disco);

        assertSame(this.disco, retorno);
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscoPorIdDelegandoParaOhRepositorioRetornandoUmDisco() {
        when(discoRepositoryMock.findById(ID_UM)).thenReturn(optionalDisco);

        Optional<Disco> retorno = discoService.consultarPorId(ID_UM);

        assertSame(this.disco, retorno.get());
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscoPorIdDelegandoParaOhRepositorioRetornandoNulo() {
        when(discoRepositoryMock.findById(ID_UM)).thenReturn(Optional.ofNullable(null));

        Optional<Disco> retorno = discoService.consultarPorId(ID_UM);

        assertSame(Optional.empty(), retorno);
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscosPorGeneroDelegandoParaOhServicoRetornandoListaComDiscos() {
        when(discoRepositoryMock.findDiscosByGenero_IdOrderByDescricaoAsc(ID_UM, Pageable.unpaged())).thenReturn(paginacaoDiscos);

        Page<Disco> retorno = discoService.consultarDiscosPorGenero(ID_UM, Pageable.unpaged());

        assertEquals(paginacaoDiscos, retorno);
    }

    @Test
    public void deveriaChamarMetodoConsultarDiscosPorGeneroDelegandoParaOhServicoRetornandoListaSemDiscos() {
        atribuirPaginacaoVaziaIhLimpandoListaDiscos();
        when(discoRepositoryMock.findDiscosByGenero_IdOrderByDescricaoAsc(ID_UM, Pageable.unpaged())).thenReturn(paginacaoDiscos);

        Page<Disco> retorno = discoService.consultarDiscosPorGenero(ID_UM, Pageable.unpaged());

        assertEquals(paginacaoDiscos, retorno);
    }

    private void atribuirPaginacaoVaziaIhLimpandoListaDiscos() {
        this.paginacaoDiscos = Page.empty();
        discos.clear();
    }

}
