package com.sinergy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.models.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	// public List<Tema> findAllByDoacaoContainingIgnoreCase(Boolean doacao);
	// public List<Tema> findAllByVoluntariadoContainingIgnoreCase(Boolean voluntariado);
	// public List<Tema> findAllByInformativoContainingIgnoreCase(Boolean informativo);
	// public List<Tema> findAllByAnyContainingIgnoreCase(Boolean any);

}
