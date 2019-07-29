package br.com.beblue.repository;

import br.com.beblue.domain.Disco;
import br.com.beblue.domain.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the Venda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findVendasByDataVendaBetweenOrderByDataVendaDesc(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);
}
