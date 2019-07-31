package br.com.beblue.service;

import br.com.beblue.domain.Venda;
import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.domain.dto.VendaDTO;
import br.com.beblue.repository.VendaRepository;
import br.com.beblue.service.calculador.CalculadorCashback;
import br.com.beblue.service.impl.VendaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.beblue.shared.ConstantesTeste.ID_UM;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_PERMITIDO_APENAS_NOVAS_VENDAS;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceTest {

    public static final String MSG_NAO_DEVERIA_PASSAR_AQUI = "Não deveria passar aqui.";
    @Mock
    private VendaRepository vendaRepositoryMock;

    @Mock
    private CalculadorCashback calculadorCashback;

    private VendaService vendaService;
    private Optional<Venda> optionalVenda;
    private Venda venda;
    private VendaDTO vendaDTO;
    private List<Venda> vendas;
    private Page<Venda> paginacaoVendas;
    private ParametroConsultaDTO parametroConsultaDTO;

    @Before
    public void inicializar() {
        this.vendaService = new VendaServiceImpl(calculadorCashback, vendaRepositoryMock);
        this.venda = new Venda().id(ID_UM);
        this.vendaDTO = new VendaDTO().id(ID_UM);
        this.optionalVenda = Optional.of(venda);
        this.vendas = new ArrayList<>();
        vendas.add(venda);
        this.paginacaoVendas = new PageImpl<>(vendas);
        gerarParametroDTO();
    }

    private void gerarParametroDTO() {
        this.parametroConsultaDTO = new ParametroConsultaDTO();
        this.parametroConsultaDTO.setPagina(0);
        this.parametroConsultaDTO.setTamanho(10);
    }

    @Test
    public void deveriaChamarMetodoConsultarVendaDelegandoParaOhRepositorioRetornandoVenda() {
        when(vendaRepositoryMock.findById(ID_UM)).thenReturn(optionalVenda);

        Optional<Venda> resultado = vendaService.consultarVenda(ID_UM);

        assertSame(this.venda, resultado.get());
    }

    @Test
    public void deveriaChamarMetodoConsultarVendaDelegandoParaOhRepositorioRetornandoNulo() {
        when(vendaRepositoryMock.findById(ID_UM)).thenReturn(Optional.ofNullable(null));

        Optional<Venda> resultado= vendaService.consultarVenda(ID_UM);

        assertEquals(Optional.empty(), resultado);
    }


    @Test
    public void deveriaChamarMetodoConsultarVendasPorPeriodoDelegandoParaOhRepositorioRetornandoVendas() {
        when(vendaRepositoryMock.findVendasByDataVendaBetweenOrderByDataVendaDesc(parametroConsultaDTO.getDataInicial(), parametroConsultaDTO.getDataFinal(), PageRequest.of(0, 10))).thenReturn(paginacaoVendas);

        Page<Venda> retorno = vendaService.consultarVendasPorPeriodo(parametroConsultaDTO);

        assertEquals(vendas, retorno.getContent());
    }

    @Test
    public void deveriaChamarMetodoConsultarVendasPorPeriodoDelegandoParaOhRepositorioRetornandoSemVendas() {
        this.paginacaoVendas = Page.empty();
        when(vendaRepositoryMock.findVendasByDataVendaBetweenOrderByDataVendaDesc(parametroConsultaDTO.getDataInicial(), parametroConsultaDTO.getDataFinal(), PageRequest.of(0, 10))).thenReturn(paginacaoVendas);

        Page<Venda> retorno = vendaService.consultarVendasPorPeriodo(parametroConsultaDTO);

        assertEquals(Page.empty(), retorno);
    }

    @Test
    public void deveriaChamarMetodoRealizarVendaRetornandoExceptionPermitidoApenasNovasVendas() {
        try {
            vendaService.realizarVenda(venda.id(ID_UM));
            fail(MSG_NAO_DEVERIA_PASSAR_AQUI);
        } catch (RuntimeException e) {
            assertEquals(MSG_PERMITIDO_APENAS_NOVAS_VENDAS, e.getMessage());
        }
    }

}
