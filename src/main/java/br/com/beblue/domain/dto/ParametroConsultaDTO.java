package br.com.beblue.domain.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.time.LocalDate;

public class ParametroConsultaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private int pagina;
    private int tamanho;

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public PageRequest getPaginacao() {
        return PageRequest.of(getPagina(), getTamanho());
    }
}
