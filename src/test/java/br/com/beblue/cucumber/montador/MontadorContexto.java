package br.com.beblue.cucumber.montador;

import br.com.beblue.cucumber.util.ContextoHelper;

public interface MontadorContexto {

    void adicionarGeneros();
    void adicionarDiscos();

    ContextoHelper getContextoHelper();
}
