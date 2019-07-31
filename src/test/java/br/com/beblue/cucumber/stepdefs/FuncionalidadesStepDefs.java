package br.com.beblue.cucumber.stepdefs;

import br.com.beblue.domain.Venda;
import br.com.beblue.domain.dto.DiscoDTO;
import br.com.beblue.domain.dto.ItemVendaDTO;
import br.com.beblue.domain.dto.ParametroConsultaDTO;
import br.com.beblue.domain.dto.VendaDTO;
import br.com.beblue.shared.JsonConverter;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.beblue.shared.ConstantesTeste.*;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_BASE_ALIMENTADA_COM_SUCESSO;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FuncionalidadesStepDefs extends StepDefs {

    public static final String HIFEN = "-";

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

    @E("que foram realizadas vendas")
    public void queForamRealizadasVendas() {
        montadorContexto.adicionarVenda();
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

    @Dado("que a venda informada possua id {string}")
    public void queAVendaInformadaPossuaId(String idVenda) throws Exception{
        this.actions = mockGet("/api/vendas/{id}", idVenda);
    }

    @Entao("deveria retonar uma venda com data em {string}")
    public void deveriaRetonarUmaVenda(String dataVenda) throws Exception{
        this.actions.andExpect(status().isOk());

        Venda venda = JsonConverter.asJsonToClass(this.actions.andReturn().getResponse().getContentAsString(), Venda.class);
        assertNotNull(dataVenda);
        assertEquals(dataVenda, venda.getDataVenda().toString());
    }

    @Dado("que foi informado um periodo de {string} a {string} e paginacao retornando os primeiros {string} registros")
    public void queFoiInformadoUmPeriodoDeAEPaginacaoRetornandoOsPrimeirosRegistros(String dataInicial, String dataFinal, String quantidadeRegistros) throws Exception{
        ParametroConsultaDTO parametroConsultaDTO = gerarParamentroConsultaDTO(dataInicial, dataFinal, quantidadeRegistros);
        mockPost("/api/vendas/periodo", parametroConsultaDTO);
    }

    @Entao("deveria retonar uma lista com uma venda e com data em {string}")
    public void deveriaRetonarUmaListaComUmaVendaEComDataEm(String data) throws UnsupportedEncodingException {
        String retorno = this.actions.andReturn().getResponse().getContentAsString();
        assertTrue(retorno.contains(data));
    }

    private ParametroConsultaDTO gerarParamentroConsultaDTO(String dataInicial, String dataFinal, String quantidadeRegistros) {
        ParametroConsultaDTO parametroConsultaDTO = new ParametroConsultaDTO();
        String[] dataInicialSplitada = dataInicial.split(HIFEN);
        parametroConsultaDTO.setDataInicial(LocalDate.of(Integer.parseInt(dataInicialSplitada[0]), Integer.parseInt(dataInicialSplitada[1]), Integer.parseInt(dataInicialSplitada[2])));
        String[] dataFinalSplitada = dataFinal.split(HIFEN);
        parametroConsultaDTO.setDataFinal(LocalDate.of(Integer.parseInt(dataFinalSplitada[0]), Integer.parseInt(dataFinalSplitada[1]), Integer.parseInt(dataFinalSplitada[2])));
        parametroConsultaDTO.setPagina(0);
        parametroConsultaDTO.setTamanho(Integer.parseInt(quantidadeRegistros));
        return parametroConsultaDTO;
    }

    @Dado("que foi solicitada a realizacao de uma venda com dois discos do genero rock de 20,5 e 30,5")
    public void queFoiSolicitadaARealizacaoDeUmaVendaComDiscosDoGeneroRockDeE() throws Exception {
        VendaDTO vendaDTO = gerarVendaComItens();

        mockPost("/api/vendas/", vendaDTO);
    }

    private VendaDTO gerarVendaComItens() {
        List<ItemVendaDTO> itens = gerarListaComDoisDiscosGeneroRock();

        VendaDTO vendaDTO = new VendaDTO();
        vendaDTO.setItensVenda(itens);
        return vendaDTO;
    }

    private List<ItemVendaDTO> gerarListaComDoisDiscosGeneroRock() {
        List<ItemVendaDTO> itens = new ArrayList<>();
        DiscoDTO discoUm = new DiscoDTO();
        discoUm.setId(ID_UM);
        DiscoDTO discoDois = new DiscoDTO();
        discoDois.setId(ID_DOIS);
        ItemVendaDTO itemVendaUm = new ItemVendaDTO();
        itemVendaUm.setDisco(discoUm);
        itemVendaUm.setQuantidade(QUANTIDADE_UM);
        ItemVendaDTO itemVendaDois = new ItemVendaDTO();
        itemVendaDois.setDisco(discoDois);
        itemVendaDois.setQuantidade(QUANTIDADE_UM);
        itens.add(itemVendaUm);
        itens.add(itemVendaDois);
        return itens;
    }

    @Entao("deveria calcular o cashback de quinze porcento e armazenar o valor de {string}")
    public void deveriaCalcularOCashbackDeREArmazenarOValorDe(String valorTotalCashBack) throws Exception {
        this.actions.andExpect(status().isCreated());
        String retorno = this.actions.andReturn().getResponse().getContentAsString();
        assertTrue(retorno.contains(valorTotalCashBack));
    }
}
