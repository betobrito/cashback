package br.com.beblue.cucumber.montador;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Genero;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ContextoHelper {

    private JdbcTemplate jdbcTemplate;

    public ContextoHelper(DataSource dataSource) {
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
}
