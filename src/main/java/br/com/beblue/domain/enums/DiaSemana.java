package br.com.beblue.domain.enums;

import java.util.Arrays;

public enum DiaSemana {
    DOMINGO(1, "Domingo"),
    SEGUNDA(2,"Segunda feira"),
    TERCA(3,"Terça feira"),
    QUARTA(4,"Quarta feira"),
    QUINTA(5, "Quinta feira"),
    SEXTA(6, "Sexta feira"),
    SABADO(7, "Sábado");

    private Integer dia;
    private String descricao;

    DiaSemana(Integer dia, String descricao) {
        this.dia = dia;
        this.descricao = descricao;
    }

    public Integer obterDia(){
        return dia;
    }

    public static DiaSemana obterEnumDiaSemana(Integer dia){
        return Arrays.stream(values()).filter(diaSemana -> diaSemana.obterDia().equals(dia)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "DiaSemana{" +
            this.descricao +
            '}';
    }
}
