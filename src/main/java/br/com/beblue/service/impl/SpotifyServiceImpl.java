package br.com.beblue.service.impl;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Genero;
import br.com.beblue.repository.DiscoRepository;
import br.com.beblue.repository.GeneroRepository;
import br.com.beblue.service.SpotifyService;
import br.com.beblue.util.ExecutorUtils;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_OCORREU_UM_ERRO_AO_ALIMENTAR_A_BASE;
import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_TOKEN_DO_SPOTIFY_IRA_EXPIRAR_EM_X;
import static br.com.beblue.util.Constantes.MensagemSistema.MSG_ALBUM_ARTISTA;
import static br.com.beblue.util.Constantes.Outros.*;
import static br.com.beblue.util.Constantes.Spotify.*;
import static br.com.beblue.util.ExecutorUtils.executarIgnorandoExcecaoComValorDefault;
import static br.com.beblue.util.ExecutorUtils.executarLogandoExcecao;

@Service
public class SpotifyServiceImpl implements SpotifyService {

    private final Logger log = LoggerFactory.getLogger(SpotifyService.class);

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(CLIENT_ID)
        .setClientSecret(CLIENT_SECRET)
        .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
        .build();

    private final GeneroRepository generoRepository;
    private final DiscoRepository discoRepository;

    public SpotifyServiceImpl(GeneroRepository generoRepository, DiscoRepository discoRepository) {
        this.generoRepository = generoRepository;
        this.discoRepository = discoRepository;
    }

    @Override
    public void alimentarBaseDiscosPorGenero() {
        List<Genero> generos = generoRepository.findAll();
        inserirDiscosPorGenero(generos);
    }

    private void inserirDiscosPorGenero(List<Genero> generos) {
        Integer contadorDiscos = 0;
        for(Genero genero : generos){
            gerarTokenSpotify();
            persistindoAlbunsRetornados(genero, contadorDiscos);
            contadorDiscos = atribuirProximosCinquentaDiscos(contadorDiscos);
        }
    }

    private void persistindoAlbunsRetornados(Genero genero, Integer limiteDiscosConsulta) {
        SearchResult retornoConsulta = consultaSpotify(limiteDiscosConsulta);
        executarLogandoExcecao(() -> {
            if(retornoConsulta != null){
                for(AlbumSimplified album : retornoConsulta.getAlbums().getItems()){
                    discoRepository.save(gerarDisco(genero, album));
                }
            }
        });
    }

    private Disco gerarDisco(Genero genero, AlbumSimplified album) {
        return new Disco()
            .descricao(String.format(MSG_ALBUM_ARTISTA, album.getName(), getNomeArtista(album)))
            .genero(genero).preco(getPrecoRandomico());
    }

    private SearchResult consultaSpotify(Integer contadorDiscos) {
        return executarIgnorandoExcecaoComValorDefault(() -> {
            SearchItemRequest build = spotifyApi.searchItem(QUERY_SPOTIFY_ANO_2019, ModelObjectType.ALBUM.getType())
                .market(CountryCode.BR)
                .limit(LIMITE_REGISTROS_50)
                .offset(contadorDiscos)
                .build();
            return build.execute();
        }, null);
    }

    private void gerarTokenSpotify() {
        executarLogandoExcecao(() -> {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            log.info(String.format(MSG_LOG_TOKEN_DO_SPOTIFY_IRA_EXPIRAR_EM_X, clientCredentials.getExpiresIn()));
        });
    }

    private Integer atribuirProximosCinquentaDiscos(Integer contador) {
        return contador + LIMITE_REGISTROS_50;
    }

    private String getNomeArtista(AlbumSimplified album) {
        return album.getArtists()[PRIMEIRO_REGISTRO].getName();
    }

    private BigDecimal getPrecoRandomico() {
        return new BigDecimal(BigInteger.valueOf(new Random().nextInt(LIMITE_100_REAIS_RANDOM)), ESCALA_DOIS_DIGITOS);
    }
}
