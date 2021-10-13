package com.sinergy.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.generation.blogpessoal.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinergy.models.Usuario;
import com.sinergy.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private @Autowired UsuarioRepository repository;

	/**
	 * Método usado para checar possível duplicidade de e-mail
	 * 
	 * @param usuarioNovo do tipo Usuario
	 * @return Optional com Usuario cadastrado caso email não seja existente
	 * @author Abner
	 * @since 1.0
	 * 
	 */
	/**
	 * public Optional<Object> ChecandoEmail(Usuario usuarioNovo) { // vai o email
	 * inserido no banco de dados return
	 * repositorio.findByEmail(usuarioNovo.getEmail()).map(usuarioExistente -> {
	 * return Optional.empty(); // se não existir, o optional é vazio
	 * }).orElseGet(() -> { return
	 * Optional.ofNullable(repositorio.save(usuarioNovo)); // se existir, vai salvar
	 * esse usuário }); }
	 */

	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return repository.save(usuario);
	}

	public Optional<UserLogin> Logar(Optional<UserLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);

				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());

				return user;
			}
		}

		return null;
	}

}
