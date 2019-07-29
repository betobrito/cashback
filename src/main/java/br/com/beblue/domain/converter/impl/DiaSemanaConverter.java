package br.com.beblue.domain.converter.impl;

import br.com.beblue.domain.enums.DiaSemana;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static br.com.beblue.domain.enums.DiaSemana.obterEnumDiaSemana;

@Converter
public class DiaSemanaConverter implements AttributeConverter<DiaSemana, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DiaSemana diaSemana) {
        return diaSemana == null? null: diaSemana.obterDia();
    }

    @Override
    public DiaSemana convertToEntityAttribute(Integer diaInformado) {
        return diaInformado == null? null: obterEnumDiaSemana(diaInformado);
    }
}
