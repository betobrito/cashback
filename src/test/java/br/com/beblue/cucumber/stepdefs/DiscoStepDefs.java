package br.com.beblue.cucumber.stepdefs;

import br.com.beblue.cucumber.datatables.DiscoDataTable;
import br.com.beblue.domain.Disco;
import br.com.beblue.domain.dto.DiscoDTO;
import br.com.beblue.service.DiscoService;
import br.com.beblue.shared.JsonConverter;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Dado("^que foram inseridos generos a base de dados$")
    public void queForamInseridosGenerosAhBaseDeDados() throws Throwable {
        this.contextoHelper.inserirGenero(1L, "POP", "pop");
        this.contextoHelper.inserirGenero(2L, "MPB", "mpb");
        this.contextoHelper.inserirGenero(3L, "CLASSIC", "classical");
        this.contextoHelper.inserirGenero(4L, "ROCK", "rock");
    }

    @E("^que foram inseridos discos$")
    public void queForamInseridosDiscos() throws Throwable {
        this.contextoHelper.inserirDisco(1L, "AC/DC 101", new BigDecimal(20.5), 4L);
        this.contextoHelper.inserirDisco(2L, "Enema Of The State - Blink 182", new BigDecimal(30.5), 4L);
        this.contextoHelper.inserirDisco(3L, "Esplendor - Djavan", new BigDecimal(5.5), 2L);
        this.contextoHelper.inserirDisco(4L, "iCollection - Kid Abelha", new BigDecimal(15.5), 2L);
        this.contextoHelper.inserirDisco(5L, "Uma nora pra cada dia - MC Kevinho", new BigDecimal(4.5), 1L);
    }

    @Dado("que o disco informado possua id {string}")
    public void queODiscoInformadoPossuaId(String idDisco) throws Exception {
        this.actions = mockGet("/api/discos/{id}", idDisco);
    }

    @Entao("deveria retornar um disco com descricao {string}")
    public void deveriaRetornarUmDiscoComDescricao(String descricao) throws Exception {
        this.actions.andExpect(status().isOk());
        this.actions.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(descricao));
    }

    @E("que pertenca ao genero {string}")
    public void quePertencaAoGenero(String genero) throws Exception {
        this.actions.andExpect(status().isOk());
        this.actions.andExpect(MockMvcResultMatchers.jsonPath("$.genero.descricao").value(genero));
    }

    @Entao("deveria retornar um exception com status 404")
    public void deveriaRetornarUmExceptionComStatus() throws Exception {
        this.actions.andExpect(status().isNotFound());
    }

    @Dado("que o genero rock possua id {string} deveria retornar os {string} primeiros registros ordenados por {string}")
    public void queOGeneroInformadoPossuaIdDeveriaRetornarOsPrimeirosRegistrosOrdenadosPorDescricao(String idGenero, String registros, String descricao) throws Exception {
        this.actions = mockGet("/api/discos/genero?idGenero={idGenero}&page=0&size={registros}&sort={descricao},asc", idGenero, registros, descricao);
        this.actions.andExpect(status().isOk());
    }

    @Entao("^deveria retornar dois discos:$")
    public void deveriaRetornarDiscos(DataTable dataTable) throws Exception{
        List<DiscoDTO> discos = JsonConverter.asJsonToClassList(this.actions.andReturn().getResponse().getContentAsString(), DiscoDTO.class);
        this.actions.andExpect(status().isOk());
        this.actions.andExpect(MockMvcResultMatchers.jsonPath("$.[*].descricao").isNotEmpty());
        verificandoSeRetornoContemOsRegistrosEsperados(dataTable.column(0), discos);
    }

    private void verificandoSeRetornoContemOsRegistrosEsperados(List<String> objetosEsperados, List<DiscoDTO> discos) {
        for(int indice = 0; indice < objetosEsperados.size(); indice++){
            String descricao = discos.get(indice).getDescricao();
            assertTrue(String.format("Deveria ter retornado o objeto %s na lista", descricao), objetosEsperados.contains(descricao));
        }
    }

    @Entao("deveria retornar uma lista vazia")
    public void deveriaRetornarUmaListaVazia() throws Exception{
        this.actions.andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isEmpty());
    }
}
