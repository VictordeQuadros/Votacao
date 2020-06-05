package br.com.compasso.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.votacao.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>{

	List<Associado> findByCpf(String cpf);
}
