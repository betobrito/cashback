package br.com.beblue.service.calculador;

import br.com.beblue.domain.*;
import br.com.beblue.domain.enums.DiaSemana;
import br.com.beblue.repository.CashbackRepository;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.service.calculador.impl.CalculadorCashbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.beblue.service.VendaServiceTest.MSG_NAO_DEVERIA_PASSAR_AQUI;
import static br.com.beblue.shared.ConstantesTeste.*;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_ERRO_AO_RECUPERAR_DISCO_DE_ID_X;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculadorCashbackTest {

    public static final BigDecimal CASH_BACK_ESPERADO_0_45 = new BigDecimal(0.45000000000000002498001805406602215953171253204345703125).round(MathContext.DECIMAL32);

    @Mock
    private CashbackRepository cashbackRepositoryMock;

    @Mock
    private DiscoRepository discoRepositoryMock;

    private CalculadorCashback calculadorCashback;
    private Venda venda;
    private Disco disco;
    private List<ItemVenda> itensVenda;
    private ItemVenda itemVenda;
    private Optional<Disco> optionalDisco;
    private Genero genero;
    private Cashback cashback;

    @Before
    public void inicializar() {
        this.calculadorCashback = new CalculadorCashbackImpl(cashbackRepositoryMock, discoRepositoryMock);
        montarAmbienteParaCalculador();
    }

    private void montarAmbienteParaCalculador() {
        this.venda = new Venda();
        this.itensVenda = new ArrayList<>();
        this.itemVenda = new ItemVenda();
        this.genero = new Genero();
        this.disco = new Disco().id(ID_UM).genero(genero).preco(new BigDecimal(PRECO_4_5));
        this.itemVenda.disco(disco).quantidade(QUANTIDADE_UM);
        this.itensVenda.add(itemVenda);
        this.venda.setItensVenda(itensVenda);
        this.optionalDisco = Optional.of(disco);
        this.cashback = new Cashback().porcentagem(new BigDecimal(DEZ_PORCENTO));
    }

    @Test
    public void deveriaChamarMetodoCalcularRetornandoValorCashBackTotalDeQuarentaEhCincoCentavos() {
        when(discoRepositoryMock.findById(ID_UM)).thenReturn(optionalDisco);
        when(cashbackRepositoryMock.findCashbackByGeneroAndDiaSemana(any(Genero.class), any(DiaSemana.class))).thenReturn(cashback);

        calculadorCashback.calcular(this.venda);

        assertFalse(venda.getItensVenda().isEmpty());
        assertNotNull(venda.getValorTotalCashback());
        assertEquals(CASH_BACK_ESPERADO_0_45, venda.getValorTotalCashback().round(MathContext.DECIMAL32));
    }

    @Test
    public void deveriaChamarMetodoCalcularRetornandoValorCashBackIndividualDeQuarentaEhCincoCentavos() {
        when(discoRepositoryMock.findById(ID_UM)).thenReturn(optionalDisco);
        when(cashbackRepositoryMock.findCashbackByGeneroAndDiaSemana(any(Genero.class), any(DiaSemana.class))).thenReturn(cashback);

        calculadorCashback.calcular(this.venda);

        assertFalse(venda.getItensVenda().isEmpty());
        assertNotNull(venda.getItensVenda().get(0));
        assertNotNull(venda.getItensVenda().get(0).getValorCashBack());
        assertEquals(CASH_BACK_ESPERADO_0_45, venda.getItensVenda().get(0).getValorCashBack().round(MathContext.DECIMAL32));
    }

    @Test
    public void deveriaChamarMetodoCalcularRetornandoExceptionComMensagemErroRecuperarDiscoComIdUm() {
        try {
            when(discoRepositoryMock.findById(ID_UM)).thenReturn(Optional.empty());
            calculadorCashback.calcular(this.venda);
            fail(MSG_NAO_DEVERIA_PASSAR_AQUI);
        } catch (RuntimeException e) {
            Assert.assertEquals(String.format(MSG_ERRO_AO_RECUPERAR_DISCO_DE_ID_X, ID_UM), e.getCause().getMessage());
        }
    }
}
