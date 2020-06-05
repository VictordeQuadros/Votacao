package br.com.compasso.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.votacao.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

}
