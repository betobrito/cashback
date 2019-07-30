package br.com.beblue.cucumber.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ContextoHelper {

    void limparDadosTestes();
    void inserirDisco(long id, String descricao, BigDecimal preco, long idGenero);
    void inserirGenero(long id, String descricao, String idSpotify);
    void inserirCashBack(long id, long idGenero, Integer diaSemana, BigDecimal porcentagem);
    Integer consultarQuantidadeRegistrosNaBaseDiscos();

    void inserirVenda(long id, BigDecimal valorCashback, LocalDate dataVenda);
    void inserirItemVenda(long id, long idVenda, long idDisco, int quantidade, BigDecimal valorCashback);
}
