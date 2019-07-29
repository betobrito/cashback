package br.com.beblue.repository;

import br.com.beblue.domain.Cashback;
import br.com.beblue.domain.Genero;
import br.com.beblue.domain.Venda;
import br.com.beblue.domain.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashbackRepository extends JpaRepository<Cashback, Long> {

    Cashback findCashbackByGeneroAndDiaSemana(Genero genero, DiaSemana diaSemana);

}
