package br.com.beblue.web.rest;

import br.com.beblue.service.SpotifyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.beblue.util.Constantes.MensagemSistema.MSG_BASE_ALIMENTADA_COM_SUCESSO;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyResourceTest {

    @Mock
    private SpotifyService spotifyServiceMock;

    private SpotifyResource spotifyResource;

    @Before
    public void inicializar() {
        this.spotifyResource = new SpotifyResource(spotifyServiceMock);
    }

    @Test
    public void deveriaChamarMetodAlimentarBaseDelegandoParaOhServico() {
        ResponseEntity<String> resultado = spotifyResource.alimentarBaseDiscosPorGenero();

        verify(spotifyServiceMock).alimentarBaseDiscosPorGenero();
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertSame(MSG_BASE_ALIMENTADA_COM_SUCESSO, resultado.getBody());
    }

    @Test
    public void deveriaChamarMetodAlimentarBaseDelegandoParaOhServicoLancandoRuntimeException() {
        doThrow(new RuntimeException()).when(spotifyServiceMock).alimentarBaseDiscosPorGenero();

        try {
            spotifyResource.alimentarBaseDiscosPorGenero();
            fail("Não deveria passar por este método");
        } catch (Exception e){
            assertTrue(e instanceof RuntimeException);
        }
    }
}
