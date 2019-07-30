package br.com.beblue.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.beblue.util.Constantes.Outros.VALOR_CASHBACK_INICIAL_ZERO;

@Entity
@Table(name = "venda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valor_total_cashback", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorTotalCashback;

    @CreationTimestamp
    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @JsonManagedReference
    @OneToMany(mappedBy = "venda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ItemVenda> itensVenda;

    public Venda() {
        this.valorTotalCashback = new BigDecimal(VALOR_CASHBACK_INICIAL_ZERO);
        this.itensVenda = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getValorTotalCashback() {
        return valorTotalCashback;
    }

    public void setValorTotalCashback(BigDecimal valorTotalCashback) {
        this.valorTotalCashback = valorTotalCashback;
    }

    public Venda cashBackTotal(BigDecimal valorTotalCashback) {
        this.valorTotalCashback = valorTotalCashback;
        return this;
    }

    public void somarAoCashBackTotal(BigDecimal valorTotalCashback) {
        this.valorTotalCashback = this.valorTotalCashback.add(valorTotalCashback);
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public Venda dataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
        return this;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public Venda itensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "Venda{" +
            "id=" + getId() +
            ", valorTotalCashback=" + getValorTotalCashback() +
            ", dataVenda='" + getDataVenda() + "'" +
            "}";
    }
}
