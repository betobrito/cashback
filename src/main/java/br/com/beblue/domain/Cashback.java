package br.com.beblue.domain;

import br.com.beblue.domain.converter.impl.DiaSemanaConverter;
import br.com.beblue.domain.enums.DiaSemana;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cashback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cashback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id", nullable = false)
    private Genero genero;

    @Convert(converter = DiaSemanaConverter.class)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Column(name = "porcentagem")
    private BigDecimal porcentagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public BigDecimal getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(BigDecimal porcentagem) {
        this.porcentagem = porcentagem;
    }

    public Cashback porcentagem(BigDecimal porcentagem) {
        this.porcentagem = porcentagem;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cashback cashback = (Cashback) o;
        return Objects.equals(id, cashback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cashback{" +
            "id=" + getId() +
            "dia semana=" + getDiaSemana().toString() +
            "porcentagem=" + getPorcentagem() +
            "}";
    }
}
