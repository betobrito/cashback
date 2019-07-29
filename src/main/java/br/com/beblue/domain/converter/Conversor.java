package br.com.beblue.domain.converter;


public interface Conversor<ObjetoEntrada, ObjetoSaida> {

    ObjetoSaida converter(ObjetoEntrada objetoEntrada);

}
