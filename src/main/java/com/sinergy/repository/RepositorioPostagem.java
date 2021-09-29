package com.sinergy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sinergy.model.Postagem;

public interface RepositorioPostagem extends JpaRepository<Postagem, Long> {

	/**
	 * Metodo utilizado para realizar pesquisa pela coluna legenda da tabela Postagem
	 * 
	 * @param legenda
	 * @return lista com as Postagens a partir da palavra digitada
	 * @author Gustavo
	 * @since 1.0
	 */
	public List<Postagem> findAllByLegendaContainingIgnoreCase(String titulo);
	
	/**
	 * Metodo utilizado para realizar pesquisa pela coluna Editado da tabela Postagem
	 * 
	 * @param editado
	 * @return lista com as Postagens a partir do valor true ou false
	 * @author Gustavo
	 * @since 1.0
	 */
	public List<Postagem> findAllByEditadoContainingIgnoreCase(Boolean editado);
	
}
