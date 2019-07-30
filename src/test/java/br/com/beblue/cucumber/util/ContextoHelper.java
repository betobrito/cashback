package br.com.beblue.cucumber.util;

import java.math.BigDecimal;

public interface ContextoHelper {

    void limparDadosTestes();
    void inserirDisco(long id, String descricao, BigDecimal preco, long idGenero);
    void inserirGenero(long id, String descricao, String idSpotify);
    void inserirCashBack(long id, long idGenero, Integer diaSemana, BigDecimal porcentagem);
    Integer consultarQuantidadeRegistrosNaBaseDiscos();
}
