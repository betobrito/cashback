package br.com.beblue.service;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Genero;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.repository.GeneroRepository;
import br.com.beblue.service.impl.SpotifyServiceImpl;
import br.com.beblue.web.rest.SpotifyResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static br.com.beblue.util.Constantes.MensagemSistema.MSG_BASE_ALIMENTADA_COM_SUCESSO;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyServiceTest {

    public static final int NUMERO_DE_INVOCACOES_CINQUENTA = 50;
    @Mock
    private GeneroRepository generoRepositoryMock;

    @Mock
    private DiscoRepository discoRepositoryMock;

    private SpotifyService spotifyService;
    private List<Genero> generos;
    private Genero genero;

    @Before
    public void inicializar() {
        this.spotifyService = new SpotifyServiceImpl(generoRepositoryMock, discoRepositoryMock);
        generos = new ArrayList<>();
        genero = new Genero();
        generos.add(genero);
    }

    @Test
    public void deveriaChamarMetodAlimentarBaseDelegandoParaOhRepositorio() {
        when(generoRepositoryMock.findAll()).thenReturn(generos);

        spotifyService.alimentarBaseDiscosPorGenero();

        verify(generoRepositoryMock).findAll();
        verify(discoRepositoryMock, times(NUMERO_DE_INVOCACOES_CINQUENTA)).save(any(Disco.class));
    }

    @Test
    public void deveriaChamarMetodAlimentarBaseDelegandoParaOhRepositorioLancandoRuntimeException() {
        when(generoRepositoryMock.findAll()).thenReturn(generos);
        when(discoRepositoryMock.save(any(Disco.class))).thenThrow(new RuntimeException());

        try {
            spotifyService.alimentarBaseDiscosPorGenero();
            fail("Não deveria passar por este método");
        } catch (Exception e){
            assertTrue(e instanceof RuntimeException);
        }
    }
}
