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

    @Override
    public void adicionarCashBackSemanal() {
        this.contextoHelper.inserirCashBack(ID_UM, ID_UM, DOMINGO, new BigDecimal(VINTE_CINCO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DOIS, ID_UM, SEGUNDA, new BigDecimal(SETE_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_TRES, ID_UM, TERCA, new BigDecimal(SEIS_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_QUATRO, ID_UM, QUARTA, new BigDecimal(DOIS_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_CINCO, ID_UM, QUINTA, new BigDecimal(DEZ_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_SEIS, ID_UM, SEXTA, new BigDecimal(QUINZE_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_SETE, ID_UM, SABADO, new BigDecimal(VINTE_PORCENTO));

        this.contextoHelper.inserirCashBack(ID_OITO, ID_DOIS, DOMINGO, new BigDecimal(TRINTA_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_NOVE, ID_DOIS, SEGUNDA, new BigDecimal(CINCO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DEZ, ID_DOIS, TERCA, new BigDecimal(DEZ_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_ONZE, ID_DOIS, QUARTA, new BigDecimal(QUINZE_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DOZE, ID_DOIS, QUINTA, new BigDecimal(VINTE_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_TREZE, ID_DOIS, SEXTA, new BigDecimal(VINTE_CINCO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_QUATORZE, ID_DOIS, SABADO, new BigDecimal(TRINTA_PORCENTO));

        this.contextoHelper.inserirCashBack(ID_QUINZE, ID_TRES, DOMINGO, new BigDecimal(TRINTA_CINCO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DEZESEIS, ID_TRES, SEGUNDA, new BigDecimal(TRES_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DEZESETE, ID_TRES, TERCA, new BigDecimal(CINCO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DEZOITO, ID_TRES, QUARTA, new BigDecimal(OITO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_DEZENOVE, ID_TRES, QUINTA, new BigDecimal(TREZE_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_VINTE, ID_TRES, SEXTA, new BigDecimal(DEZOITO_PORCENTO));
        this.contextoHelper.inserirCashBack(ID_VINTE_UM, ID_TRES, SABADO, new BigDecimal(VINTE_CINCO_PORCENTO));
    }

}
