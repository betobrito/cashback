package br.com.beblue.cucumber.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class ContextoHelperImpl implements ContextoHelper{

    private JdbcTemplate jdbcTemplate;

    public ContextoHelperImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void limparDadosTestes() {
        jdbcTemplate.update("delete from public.venda");
        jdbcTemplate.update("delete from public.cashback");
        jdbcTemplate.update("delete from public.disco");
        jdbcTemplate.update("delete from public.genero");
    }

    public void inserirDisco(long id, String descricao, BigDecimal preco, long idGenero) {
        jdbcTemplate.update("insert into public.disco (id, descricao, preco, id_genero) values (?,?,?,?)", id, descricao, preco, idGenero);
    }

    public void inserirGenero(long id, String descricao, String idSpotify) {
        jdbcTemplate.update("insert into public.genero (id, descricao, id_genero_spotify) values (?,?,?)", id, descricao, idSpotify);
    }

    public void inserirCashBack(long id, long idGenero, Integer diaSemana, BigDecimal porcentagem) {
        jdbcTemplate.update("insert into public.cashback (id, id_genero, dia_semana, porcentagem) values (?, ?, ?, ?);", id, idGenero, diaSemana, porcentagem);
    }

    public Integer consultarQuantidadeRegistrosNaBaseDiscos() {
        return jdbcTemplate.queryForObject("select count(*) from public.disco", Integer.class);
    }
}
