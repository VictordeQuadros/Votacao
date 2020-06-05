package br.com.compasso.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.votacao.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>{

	List<Pauta> findByTitulo(String titulo);
}
