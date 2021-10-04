package com.sinergy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.models.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	/**
	 * Buscar pela opçao booleana doacao
	 * 
	 * @author Gustavo
	 * @param doacao
	 * @return lista com tema doacao
	 * 
	 */
	public List<Tema> findAllByDoacao(Boolean doacao);

	/**
	 * Buscar pela opçao booleana voluntariado
	 * 
	 * @author Gustavo
	 * @param voluntariado
	 * @return lista com tema voluntariado
	 * 
	 */
	public List<Tema> findAllByVoluntariado(Boolean voluntariado);

	/**
	 * Buscar pela opçao booleana informativo
	 * 
	 * @author Gustavo
	 * @param informativo
	 * @return lista com tema informativo
	 * 
	 */
	public List<Tema> findAllByInformativo(Boolean informativo);

	/**
	 * Buscar pela opçao booleana any
	 * 
	 * @author Gustavo
	 * @param any
	 * @return lista com tema any
	 * 
	 */
	public List<Tema> findAllByAny(Boolean any);

}
