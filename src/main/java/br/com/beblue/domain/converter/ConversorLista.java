package br.com.beblue.domain.converter;


import java.util.List;

public interface ConversorLista<ObjetoEntrada, ObjetoSaida> {

    List<ObjetoSaida> converter(List<ObjetoEntrada> objetoEntrada);

}
