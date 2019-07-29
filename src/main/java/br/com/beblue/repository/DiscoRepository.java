package br.com.beblue.repository;

import br.com.beblue.domain.Disco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscoRepository extends JpaRepository<Disco, Long> {

    Page<Disco> findDiscosByGenero_IdOrderByDescricaoAsc(Long idGenero, Pageable pageable);
}
