package br.com.beblue.web.rest;

import br.com.beblue.CashbackApp;
import br.com.beblue.domain.Venda;
import br.com.beblue.domain.converter.Conversor;
import br.com.beblue.domain.converter.impl.ConversorVenda;
import br.com.beblue.repository.VendaRepository;
import br.com.beblue.service.VendaService;
import br.com.beblue.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.beblue.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VendaResource} REST controller.
 */
@SpringBootTest(classes = CashbackApp.class)
public class VendaResourceIT {

    private static final BigDecimal DEFAULT_CASH_BACK_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASH_BACK_TOTAL = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATA_VENDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENDA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ConversorVenda conversor;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVendaMockMvc;

    private Venda venda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendaResource vendaResource = new VendaResource(vendaService, conversor);
        this.restVendaMockMvc = MockMvcBuilders.standaloneSetup(vendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createEntity(EntityManager em) {
        Venda venda = new Venda()
            .cashBackTotal(DEFAULT_CASH_BACK_TOTAL)
            .dataVenda(DEFAULT_DATA_VENDA);
        return venda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createUpdatedEntity(EntityManager em) {
        Venda venda = new Venda()
            .cashBackTotal(UPDATED_CASH_BACK_TOTAL)
            .dataVenda(UPDATED_DATA_VENDA);
        return venda;
    }

    @BeforeEach
    public void initTest() {
        venda = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenda() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venda)))
            .andExpect(status().isCreated());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate + 1);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getValorTotalCashback()).isEqualTo(DEFAULT_CASH_BACK_TOTAL);
        assertThat(testVenda.getDataVenda()).isEqualTo(DEFAULT_DATA_VENDA);
    }

    @Test
    @Transactional
    public void createVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda with an existing ID
        venda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venda)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCashBackTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendaRepository.findAll().size();
        // set the field null
        venda.setValorTotalCashback(null);

        // Create the Venda, which fails.

        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venda)))
            .andExpect(status().isBadRequest());

        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataVendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendaRepository.findAll().size();
        // set the field null
        venda.setDataVenda(null);

        // Create the Venda, which fails.

        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venda)))
            .andExpect(status().isBadRequest());

        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendas() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].cashBackTotal").value(hasItem(DEFAULT_CASH_BACK_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].dataVenda").value(hasItem(DEFAULT_DATA_VENDA.toString())));
    }

    @Test
    @Transactional
    public void getVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", venda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venda.getId().intValue()))
            .andExpect(jsonPath("$.cashBackTotal").value(DEFAULT_CASH_BACK_TOTAL.intValue()))
            .andExpect(jsonPath("$.dataVenda").value(DEFAULT_DATA_VENDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVenda() throws Exception {
        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenda() throws Exception {
        // Initialize the database
        vendaService.realizarVenda(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda
        Venda updatedVenda = vendaRepository.findById(venda.getId()).get();
        // Disconnect from session so that the updates on updatedVenda are not directly saved in db
        em.detach(updatedVenda);
        updatedVenda
            .cashBackTotal(UPDATED_CASH_BACK_TOTAL)
            .dataVenda(UPDATED_DATA_VENDA);

        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVenda)))
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getValorTotalCashback()).isEqualTo(UPDATED_CASH_BACK_TOTAL);
        assertThat(testVenda.getDataVenda()).isEqualTo(UPDATED_DATA_VENDA);
    }

    @Test
    @Transactional
    public void updateNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Create the Venda

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venda)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenda() throws Exception {
        // Initialize the database
        vendaService.realizarVenda(venda);

        int databaseSizeBeforeDelete = vendaRepository.findAll().size();

        // Delete the venda
        restVendaMockMvc.perform(delete("/api/vendas/{id}", venda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venda.class);
        Venda venda1 = new Venda();
        venda1.setId(1L);
        Venda venda2 = new Venda();
        venda2.setId(venda1.getId());
        assertThat(venda1).isEqualTo(venda2);
        venda2.setId(2L);
        assertThat(venda1).isNotEqualTo(venda2);
        venda1.setId(null);
        assertThat(venda1).isNotEqualTo(venda2);
    }
}
