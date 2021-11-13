package com.sinergy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.models.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	/**
	 * Método para buscar por titulo do tema
	 * 
	 * @param titulo
	 * @return Lista com temas que contenham o titulo digitado
 	 * @since 1.0
	 * @author Turma34
	 * 
	 */
	public List<Tema> findAllByTituloContainingIgnoreCase(String titulo);
	
	/**
	 * Método para buscar por criador. Obs: O criador nao tem relacionamento em de tabela,
	 * o nome do criador será passado somente na criaçao do tema a partir dos environments globais.
	 * 
	 * @param criador
	 * @return Lista com temas criado pelo "criador"
 	 * @since 1.0
	 * @author Turma34
	 * 
	 */
	public List<Tema> findAllByCriadorContainingIgnoreCase(String criador);

}
