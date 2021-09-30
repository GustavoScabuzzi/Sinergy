package com.sinergy.App.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinergy.App.model.Postagem;

@Repository
public interface RepositorioPostagem extends JpaRepository<Postagem, Long> {

	/**
	 * Metodo utilizado para realizar pesquisa pela coluna titulo da tabela Postagem
	 * 
	 * @param legenda
	 * @return lista com as Postagens a partir da palavra digitada
	 * @author Gustavo
	 * @since 1.0
	 */
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	
	/**
	 * Metodo utilizado para realizar pesquisa pela coluna Editado da tabela Postagem
	 * 
	 * @param editado
	 * @return lista com as Postagens a partir do valor true
	 * @author Gustavo
	 * @since 1.0
	 */
	public List<Postagem> findAllByEditadoTrue();
	
	/**
	 * Metodo utilizado para realizar pesquisa pela coluna Editado da tabela Postagem
	 * 
	 * @param editado
	 * @return lista com as Postagens a partir do valor false
	 * @author Gustavo
	 * @since 1.0
	 */
	public List<Postagem> findAllByEditadoFalse();
	
}
