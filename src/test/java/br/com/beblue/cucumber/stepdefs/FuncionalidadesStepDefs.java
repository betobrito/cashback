package br.com.beblue.cucumber.stepdefs;

import br.com.beblue.domain.dto.DiscoDTO;
import br.com.beblue.shared.JsonConverter;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static br.com.beblue.shared.ConstantesTeste.DUZENTOS_REGISTROS;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_BASE_ALIMENTADA_COM_SUCESSO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FuncionalidadesStepDefs extends StepDefs {

    @Before
    public void inicializar() {
        contextoHelper.limparDadosTestes();
    }

    @Dado("^que foram inseridos generos a base de dados$")
    public void queForamInseridosGenerosAhBaseDeDados() throws Throwable {
        montadorContexto.adicionarGeneros();
    }

    @E("^que foram inseridos discos$")
    public void queForamInseridosDiscos() throws Throwable {
        montadorContexto.adicionarDiscos();
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

    @Dado("que o genero informado possua id {string} deveria retornar os {string} primeiros registros ordenados por {string}")
    public void queOGeneroInformadoPossuaIdDeveriaRetornarOsPrimeirosRegistrosOrdenadosPorDescricao(String idGenero, String registros, String descricao) throws Exception {
        this.actions = mockGet("/api/discos/genero?idGenero={idGenero}&page=0&size={registros}&sort={descricao}", idGenero, registros, descricao);
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

    @Dado("^que foram persistidos quatro generos$")
    public void queForamPersistidosQuatroGeneros() {
        montadorContexto.adicionarGeneros();
    }

    @E("que foram persistidos cashback semanal para cada genero")
    public void queForamPersistidosCashbackSemanalParaCadaGenero() {
        montadorContexto.adicionarCashBackSemanal();
    }

    @Dado("que foi solicitado alimentar base de discos")
    public void queFoiSolicitadoAlimentarBaseDeDiscos() throws Exception {
        this.actions = mockGet("/api/spotify");
    }

    @Entao("ao verificar base de discos deveria haver duzentos registros")
    public void aoVerificarBaseDeDiscosDeveriaHaverDuzentosRegistros()  throws Exception {
        this.actions.andExpect(status().isOk());
        this.actions.andExpect(MockMvcResultMatchers.jsonPath("$").value(MSG_BASE_ALIMENTADA_COM_SUCESSO));

        Integer quantidadeRegistrosConsultada = contextoHelper.consultarQuantidadeRegistrosNaBaseDiscos();
        assertEquals(DUZENTOS_REGISTROS, quantidadeRegistrosConsultada);
    }

    @E("que foram realizadas vendas")
    public void queForamRealizadasVendas() {

    }
}
