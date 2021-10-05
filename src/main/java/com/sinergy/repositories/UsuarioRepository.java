package com.sinergy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * 
	 * @author Pedro
	 * @param  email
	 * @return optional com usuário
	 * 
	 */
	
	public Optional<Usuario> findByEmail(String email);

	/**
	 * 
	 * @author Pedro
	 * @param  nome
	 * @return lista com usuário
	 * 
	 */
	
	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

}