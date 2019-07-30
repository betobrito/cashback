package br.com.beblue.cucumber.montador;

import br.com.beblue.cucumber.util.ContextoHelper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static br.com.beblue.shared.ConstantesTeste.*;

@Component
public class MontadorContextoImpl  implements MontadorContexto{

    private ContextoHelper contextoHelper;

    public MontadorContextoImpl(ContextoHelper contextoHelper) {
        this.contextoHelper = contextoHelper;
    }

    @Override
    public void adicionarGeneros() {
        this.contextoHelper.inserirGenero(ID_UM, POP, POP);
        this.contextoHelper.inserirGenero(ID_DOIS, MPB, MPB);
        this.contextoHelper.inserirGenero(ID_TRES, CLASSIC, CLASSIC);
        this.contextoHelper.inserirGenero(ID_QUATRO, ROCK, ROCK);
    }

    @Override
    public void adicionarDiscos() {
        this.contextoHelper.inserirDisco(ID_UM, DESCRICAO_AC_DC_101, new BigDecimal(PRECO_20_5), ID_QUATRO);
        this.contextoHelper.inserirDisco(ID_DOIS, DESCRICAO_ENEMA_OF_THE_STATE_BLINK_182, new BigDecimal(PRECO_30_5), ID_QUATRO);
        this.contextoHelper.inserirDisco(ID_TRES, DESCRICAO_ESPLENDOR_DJAVAN, new BigDecimal(PRECO_5_5), ID_DOIS);
        this.contextoHelper.inserirDisco(ID_QUATRO, DESCRICAO_I_COLLECTION_KID_ABELHA, new BigDecimal(PRECO_15_5), ID_DOIS);
        this.contextoHelper.inserirDisco(ID_CINCO, DESCRICAO_UMA_NORA_PRA_CADA_DIA_MC_KEVINHO, new BigDecimal(PRECO_4_5), ID_UM);
    }

}
