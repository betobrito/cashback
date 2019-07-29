package br.com.beblue.repository;

import br.com.beblue.domain.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

}
