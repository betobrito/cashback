package br.com.beblue.web.rest;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Venda;
import br.com.beblue.domain.converter.impl.ConversorVenda;
import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.domain.dto.VendaDTO;
import br.com.beblue.service.VendaService;
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

import static br.com.beblue.shared.ConstantesTeste.ID_UM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VendaResourceTest {

    @Mock
    private VendaService vendaServiceMock;

    @Mock
    private ConversorVenda conversorVendaMock;

    private VendaResource vendaResource;
    private Optional<Venda> optionalVenda;
    private Venda venda;
    private VendaDTO vendaDTO;
    private List<Venda> vendas;
    private Page<Venda> paginacaoVendas;
    private ParametroConsultaDTO parametroConsultaDTO;

    @Before
    public void inicializar() {
        this.vendaResource = new VendaResource(vendaServiceMock, conversorVendaMock);
        this.venda = new Venda();
        this.vendaDTO = new VendaDTO();
        this.optionalVenda = Optional.of(venda);
        this.vendas = new ArrayList<>();
        vendas.add(venda);
        this.paginacaoVendas = new PageImpl<>(vendas);
        this.parametroConsultaDTO = new ParametroConsultaDTO();
    }

    @Test
    public void deveriaChamarMetodoConsultarVendaDelegandoParaOhServicoRetornandoVenda() {
        when(vendaServiceMock.consultarVenda(ID_UM)).thenReturn(optionalVenda);

        ResponseEntity<Venda> resultado = vendaResource.consultarVenda(ID_UM);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertSame(venda, resultado.getBody());
    }

    @Test
    public void deveriaChamarMetodoConsultarVendaDelegandoParaOhServicoRetornandoNulo() {
        when(vendaServiceMock.consultarVenda(ID_UM)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Venda> resultado = vendaResource.consultarVenda(ID_UM);

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
    }

    @Test
    public void deveriaChamarMetodoConsultarVendasPorPeriodoDelegandoParaOhServicoRetornandoListaComVendas() {
        when(vendaServiceMock.consultarVendasPorPeriodo(parametroConsultaDTO)).thenReturn(paginacaoVendas);

        ResponseEntity<List<Venda>> resultado = vendaResource.consultarVendasPorPeriodo(parametroConsultaDTO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(vendas, resultado.getBody());
    }

    @Test
    public void deveriaChamarMetodoConsultarVendasPorPeriodoDelegandoParaOhServicoRetornandoListaSemVendas() {
        atribuirPaginacaoVaziaIhLimpandoListaVendas();
        when(vendaServiceMock.consultarVendasPorPeriodo(parametroConsultaDTO)).thenReturn(paginacaoVendas);

        ResponseEntity<List<Venda>> resultado = vendaResource.consultarVendasPorPeriodo(parametroConsultaDTO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(vendas, resultado.getBody());
    }

    private void atribuirPaginacaoVaziaIhLimpandoListaVendas() {
        this.paginacaoVendas = Page.empty();
        vendas.clear();
    }
}
