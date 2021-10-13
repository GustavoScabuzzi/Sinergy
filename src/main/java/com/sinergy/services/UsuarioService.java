package com.sinergy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinergy.models.Usuario;
import com.sinergy.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private @Autowired UsuarioRepository repositorio;

	/**
	 * Método usado para checar possível duplicidade de e-mail
	 * 
	 * @param usuarioNovo do tipo Usuario
	 * @return Optional com Usuario cadastrado caso email não seja existente
	 * @author Abner
	 * @since 1.0
	 * 
	 */
	public Optional<Object> ChecandoEmail(Usuario usuarioNovo) {  // vai o email inserido no banco de dados
		return repositorio.findByEmail(usuarioNovo.getEmail()).map(usuarioExistente -> {
			return Optional.empty(); // se não existir, o optional é vazio
		}).orElseGet(() -> {
			return Optional.ofNullable(repositorio.save(usuarioNovo)); // se existir, vai salvar esse usuário
		});
	}

}
