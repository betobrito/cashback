package br.com.beblue.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import static br.com.beblue.util.Constantes.Outros.VALOR_CASHBACK_INICIAL_ZERO;

@Entity
@Table(name = "item_venda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_venda", referencedColumnName = "id", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_disco", referencedColumnName = "id", nullable = false)
    private Disco disco;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor_cashback", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorCashBack;

    public ItemVenda() {
        this.valorCashBack = new BigDecimal(VALOR_CASHBACK_INICIAL_ZERO);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorCashBack() {
        return valorCashBack;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public ItemVenda disco(Disco disco) {
        this.disco = disco;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ItemVenda quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setValorCashBack(BigDecimal valorCashBack) {
        this.valorCashBack = valorCashBack;
    }

    public ItemVenda valorCashBack(BigDecimal valorCashBack) {
        this.valorCashBack = valorCashBack;
        return this;
    }

    public ItemVenda somarAoCashBackItem(BigDecimal valorCashback) {
        this.valorCashBack = this.valorCashBack.add(valorCashback);
        return this;
    }

    public BigDecimal valorTotalItemVenda(){
        return this.getDisco().getPreco().multiply(new BigDecimal(this.getQuantidade()));
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Objects.equals(id, itemVenda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
            "id=" + id +
            ", venda=" + venda +
            ", disco=" + disco +
            ", quantidade=" + quantidade +
            ", valorCashBack=" + valorCashBack +
            '}';
    }
}
