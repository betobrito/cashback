package br.com.beblue.cucumber.montador;

import br.com.beblue.cucumber.util.ContextoHelper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MontadorContextoImpl  implements MontadorContexto{

    protected ContextoHelper contextoHelper;

    public MontadorContextoImpl(ContextoHelper contextoHelper) {
        this.contextoHelper = contextoHelper;
    }

    @Override
    public void adicionarGeneros() {
        this.contextoHelper.inserirGenero(1L, "POP", "pop");
        this.contextoHelper.inserirGenero(2L, "MPB", "mpb");
        this.contextoHelper.inserirGenero(3L, "CLASSIC", "classical");
        this.contextoHelper.inserirGenero(4L, "ROCK", "rock");
    }

    @Override
    public void adicionarDiscos() {
        this.contextoHelper.inserirDisco(1L, "AC/DC 101", new BigDecimal(20.5), 4L);
        this.contextoHelper.inserirDisco(2L, "Enema Of The State - Blink 182", new BigDecimal(30.5), 4L);
        this.contextoHelper.inserirDisco(3L, "Esplendor - Djavan", new BigDecimal(5.5), 2L);
        this.contextoHelper.inserirDisco(4L, "iCollection - Kid Abelha", new BigDecimal(15.5), 2L);
        this.contextoHelper.inserirDisco(5L, "Uma nora pra cada dia - MC Kevinho", new BigDecimal(4.5), 1L);
    }

    @Override
    public ContextoHelper getContextoHelper() {
        return this.contextoHelper;
    }


}
