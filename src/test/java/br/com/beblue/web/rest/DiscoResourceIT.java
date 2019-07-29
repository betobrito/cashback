package br.com.beblue.web.rest;

import br.com.beblue.CashbackApp;
import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Genero;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.service.DiscoService;
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
import java.util.List;

import static br.com.beblue.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CashbackApp.class)
public class DiscoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(25.00);
    public static final long DEFAULT_ID = 5L;
    private static final Genero DEFAULT_GENERO = new Genero().id(DEFAULT_ID).descricao(DEFAULT_DESCRICAO);
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(30.00);
    private static final Genero UPDATED_GENERO = new Genero().id(DEFAULT_ID).descricao(UPDATED_DESCRICAO);

    @Autowired
    private DiscoRepository discoRepository;

    @Autowired
    private DiscoService discoService;

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

    private MockMvc restDiscoMockMvc;

    private Disco disco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscoResource discoResource = new DiscoResource(discoService);
        this.restDiscoMockMvc = MockMvcBuilders.standaloneSetup(discoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    public static Disco createEntity(EntityManager em) {
        Disco disco = new Disco()
            .descricao(DEFAULT_DESCRICAO)
            .preco(DEFAULT_PRECO)
            .genero(DEFAULT_GENERO);
        return disco;
    }

    public static Disco createUpdatedEntity(EntityManager em) {
        Disco disco = new Disco()
            .descricao(UPDATED_DESCRICAO)
            .preco(UPDATED_PRECO)
            .genero(UPDATED_GENERO);
        return disco;
    }

    @BeforeEach
    public void initTest() {
        disco = createEntity(em);
    }

    @Test
    @Transactional
    public void consultarDiscosPorGenero() throws Exception {
        discoRepository.saveAndFlush(disco);

        restDiscoMockMvc.perform(get("/api/discos/genero?idGenero=5&page=0&size=10&sort=descricao,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disco.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void consultarDisco() throws Exception {
        discoRepository.saveAndFlush(disco);

        restDiscoMockMvc.perform(get("/api/discos/{id}", disco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disco.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisco() throws Exception {
        restDiscoMockMvc.perform(get("/api/discos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
