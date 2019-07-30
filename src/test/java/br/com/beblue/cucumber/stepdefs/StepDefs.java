package br.com.beblue.cucumber.stepdefs;

import br.com.beblue.CashbackApp;
import br.com.beblue.cucumber.montador.ContextoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static br.com.beblue.shared.JsonConverter.asJsonToClass;
import static br.com.beblue.web.rest.TestUtil.convertObjectToJsonBytes;
import static br.com.beblue.web.rest.TestUtil.executarBlocoComValorDefault;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(properties = "spring.datasource.type=com.zaxxer.hikari.HikariDataSource")
@WebAppConfiguration
@ContextConfiguration(classes = CashbackApp.class)
@AutoConfigureMockMvc
public abstract class StepDefs {

    @Autowired
    private MockMvc restMockMvc;

    protected ResultActions actions;

    @Inject
    protected ContextoHelper contextoHelper;

    @Inject
    protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    protected MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private ResultActions perform(Object content, MockHttpServletRequestBuilder request) throws Exception {
        return restMockMvc.perform(request.content(convertObjectToJsonBytes(content))
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8));
    }

    private ResultActions performGet(String url, MediaType contentType, MediaType accept, Object... uriVars) throws Exception {
        return restMockMvc.perform(MockMvcRequestBuilders.get(url, uriVars)
                            .contentType(contentType)
                            .accept(accept));
    }

    protected ResultActions mockGet(String url, Object... uriVars) throws Exception {
        return performGet(url, APPLICATION_JSON_UTF8, APPLICATION_JSON_UTF8, uriVars);
    }

    private Object obterNovaInstancia(Class resourceClass, Object service) {
        try {
            Class<?> serviceClasse = service.getClass().getInterfaces()[2];
            return resourceClass.getConstructor(serviceClasse).newInstance(service);
        } catch (Exception e) {
            return null;
        }
    }

//    protected void setarMockMvc(Class resourceClass, Object service) {
//        restMockMvc = obterMockMvcBuilder(resourceClass, service).build();
//    }

    protected void mockPost(String url, Object content, Object... urlVars) throws Exception {
        actions = perform(content, post(url, urlVars));
    }

    protected List<String> obterMensagensDeErroRetornada() {
        Map erroRetornado = obterObjetoRetornado(Map.class);
        List<Map<String, String>> fieldErrors = (List) erroRetornado.get("fieldErrors");
        return getMenssageError(erroRetornado, fieldErrors, "message");
    }

    protected List<String> obterMensagensDeErroRetornadaNoDetail() {
        Map erroRetornado = obterObjetoRetornado(Map.class);
        List<Map<String, String>> fieldErrors = (List) erroRetornado.get("fieldErrors");
        return getMenssageError(erroRetornado, fieldErrors, "detail");
    }

    private List<String> getMenssageError(Map erroRetornado, List<Map<String, String>> fieldErrors, String field) {
        if (fieldErrors != null) {
            return fieldErrors.stream().map(f -> f.get(field)).collect(toList());
        }

        String mensagem = (String) erroRetornado.get(field);
        return newArrayList(mensagem);
    }

    protected <T> T obterObjetoRetornado(Class<T> classe) {
        return executarBlocoComValorDefault(() -> asJsonToClass(obterResposta().getContentAsString(), classe), null);
    }

    protected MockHttpServletResponse obterResposta() {
        MockHttpServletResponse resposta = actions.andReturn().getResponse();
        resposta.setCharacterEncoding("UTF-8");
        return resposta;
    }
}
