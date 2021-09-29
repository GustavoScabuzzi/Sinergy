package com.sinergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sinergy.model.Tema;

public interface RepositorioTema extends JpaRepository<Tema, Long> {
	
	
}
