package br.com.beblue.service.calculador.impl;

import br.com.beblue.domain.Cashback;
import br.com.beblue.domain.Disco;
import br.com.beblue.domain.ItemVenda;
import br.com.beblue.domain.Venda;
import br.com.beblue.domain.enums.DiaSemana;
import br.com.beblue.repository.CashbackRepository;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.service.calculador.CalculadorCashback;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

import static br.com.beblue.util.Constantes.MensagemSistema.MSG_ERRO_AO_RECUPERAR_DISCO_DE_ID_X;
import static br.com.beblue.util.ExecutorUtils.executarLogandoExcecao;

@Component
public class CalculadorCashbackImpl implements CalculadorCashback {

    private final CashbackRepository cashbackRepository;
    private final DiscoRepository discoRepository;

    public CalculadorCashbackImpl(CashbackRepository cashbackRepository, DiscoRepository discoRepository) {
        this.cashbackRepository = cashbackRepository;
        this.discoRepository = discoRepository;
    }

    private BigDecimal obterValorCashback(ItemVenda itemVenda) {
        Cashback cashback = cashbackRepository.findCashbackByGeneroAndDiaSemana(itemVenda.getDisco().getGenero(), getDiaSemana());
        return itemVenda.valorTotalItemVenda().multiply(cashback.getPorcentagem());
    }

    @Override
    public void calcular(Venda venda) {
        venda.getItensVenda().forEach(itemVenda -> executarLogandoExcecao(() -> {
            recuperarGeneroDisco(itemVenda);
            ItemVenda itemCalculado = itemVenda.somarAoCashBackItem(obterValorCashback(itemVenda));
            venda.somarAoCashBackTotal(itemCalculado.getValorCashBack());
            atribuirVendaAoItem(venda, itemCalculado);
        }));
    }

    private void atribuirVendaAoItem(Venda venda, ItemVenda itemCalculado) {
        itemCalculado.setVenda(venda);
    }

    private void recuperarGeneroDisco(ItemVenda itemVenda) {
        Optional<Disco> disco = discoRepository.findById(itemVenda.getDisco().getId());
        itemVenda.setDisco(disco.orElseThrow(() -> new RuntimeException(String.format(MSG_ERRO_AO_RECUPERAR_DISCO_DE_ID_X, itemVenda.getDisco().getId()))));
    }

    private DiaSemana getDiaSemana() {
        return DiaSemana.obterEnumDiaSemana(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }
}
