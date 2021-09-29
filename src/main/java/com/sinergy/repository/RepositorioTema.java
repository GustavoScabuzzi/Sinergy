package com.sinergy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.model.Postagem;
import com.sinergy.model.Tema;

public interface RepositorioTema extends JpaRepository<Tema, Long> {

	public List<Tema> findAllByDoacaoContainingIgnoreCase(Boolean doacao);

}
