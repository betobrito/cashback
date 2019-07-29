package br.com.beblue.cucumber.stepdefs;

import br.com.beblue.domain.Genero;
import br.com.beblue.service.DiscoService;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;

public class DiscoStepDefs extends StepDefs {

    private final Logger log = LoggerFactory.getLogger(DiscoStepDefs.class);

    @Inject
    private DiscoService discoService;

    @Before
    public void inicializar() {
        log.info("Limpando base.");
        contextoHelper.limparDadosTestes();
        log.info("Configurando MockMvc: {}", discoService);
    }

    @Dado("^que foram inseridos genero a base de dados$")
    public void queForamInseridosGeneroAhBaseDeDados() throws Throwable {
        this.contextoHelper.inserirGenero(5L, "AAAAAAA", "BBBBBB");
    }

    @E("^que foram inseridos discos$")
    public void queForamInseridosDiscos() throws Throwable {
        this.contextoHelper.inserirDisco(1L, "DISCO TESTE", new BigDecimal(10.5), 5L);
    }

    @Dado("que o disco informado possua id {string}")
    public void queODiscoInformadoPossuaId(String idDisco) {
        System.out.println("TESTE");
    }

}
