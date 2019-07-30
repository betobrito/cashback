package br.com.beblue.util;

public interface Constantes {

    interface MensagemLog {
        String MSG_LOG_REQUISICAO_PARA_SALVAR_O_DISCO = "Requisição para salvar o disco : {}";
        String MSG_LOG_TOKEN_DO_SPOTIFY_IRA_EXPIRAR_EM_X = "Token do spotify irá expirar em: %s";
        String MSG_LOG_EXECUTAR_LOGANDO_EXCECAO = "executarLogandoExcecao: {}";
        String MSG_LOG_OCORREU_UM_ERRO_AO_ALIMENTAR_A_BASE = "Ocorreu um erro ao alimentar a base.";
        String MSG_LOG_REQUISICAO_PARA_ALIMENTAR_BASE_DE_DISCOS_POR_GENERO_CONSUMINDO_DO_SPOTIFY = "Requisição para alimentar base de discos por genero, consumindo do spotify";
        String MSG_LOG_REQUISICAO_PARA_RECUPERAR_DISCO_COM_ID = "Requisição para recuperar disco com id : {}";
        String MSG_LOG_REQUISICAO_PARA_CONSULTAR_DISCOS_POR_GENERO = "Requisição para consultar discos por genero : {}";
        String MSG_LOG_REST_REQUISICAO_PARA_SALVAR_VENDA = "REST requisição para salvar Venda : {}";
        String MSG_LOG_REST_REQUISICAO_PARA_CONSULTAR_VENDAS = "REST requisição para consultar Vendas";
        String REST_REQUISICAO_PARA_CONSULTAR_VENDA_POR_ID = "REST requisição para consultar Venda por id: {}";
    }

    interface MensagemSistema {
        String MSG_BASE_ALIMENTADA_COM_SUCESSO = "Base alimentada com sucesso.";
        String MSG_PERMITIDO_APENAS_NOVAS_VENDAS = "Permitido apenas novas vendas.";
        String MSG_ERRO_AO_RECUPERAR_DISCO_DE_ID_X = "Erro ao recuperar disco de id: %s";
        String MSG_ALBUM_ARTISTA = "%s - %s";
    }

    interface Spotify {
        String CLIENT_ID = "ddd6a822f27646a0b36045641b29341d";
        String CLIENT_SECRET = "1fed1041ddfe4611970acf0e4b268ecf";
        Integer LIMITE_REGISTROS_50 = 50;
        String QUERY_SPOTIFY_ANO_2019 = "year:2019";
    }

    interface Outros {
        String PARAMETRO_ID_GENERO = "idGenero";
        String NOME_ENTIDADE_VENDA = "Venda";
        String ATRIBUTO_IDEXISTS = "idexists";
        int LIMITE_100_REAIS_RANDOM = 10001;
        int ESCALA_DOIS_DIGITOS = 2;
        int PRIMEIRO_REGISTRO = 0;
    }

}
