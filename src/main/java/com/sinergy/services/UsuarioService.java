package com.sinergy.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sinergy.models.Usuario;
import com.sinergy.modelsDTOs.CredentialsDTO;
import com.sinergy.modelsDTOs.UserLoginDTO;
import com.sinergy.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private @Autowired UsuarioRepository repository;

	/**
	 * Método estatico que criptografa a senha inserida
	 * 
	 * @param senha
	 * @return senha criptografada
	 * @since 1.0
	 * @author Abner Werley Silva
	 * 
	 */

	private static String encriptadorDeSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);

	}

	/**
	 * Método usado para checar possível duplicidade de e-mail, se não houver,
	 * encriptografa a senha e salva o usuário
	 * 
	 * @param usuarioNovo do tipo Usuario
	 * @return optional vazio se o email existir, se não existir, salva o usuário
	 * @author Abner
	 * @since 1.0
	 * 
	 */

	public Optional<Object> cadastraUsuario(Usuario usuarioNovo) { // vai o emailinserido no banco de dados return
		return repository.findByEmail(usuarioNovo.getEmail()).map(usuarioExistente -> {
			return Optional.empty(); // se não existir, o optional é vazio
		}).orElseGet(() -> {
			usuarioNovo.setSenha(encriptadorDeSenha(usuarioNovo.getSenha())); // encriptografa a senha
			return Optional.ofNullable(repository.save(usuarioNovo)); // se existir, vai salvar esse usuário
		});
	}
	
	/**
	 * Metodo utilizado para atualizar usuario no banco
	 * 
	 * @param usuarioParaAtualizar do tipo Usuario
	 * @return Optional com Usuario atualizado
	 * @author Abner
	 * @since 1.0
	 * 
	 */

	public Optional<Usuario> atualizaUsuario(Usuario usuarioParaAtualizar) {
		return repository.findById(usuarioParaAtualizar.getIdUsuario()).map(resp -> {
			resp.setNome(usuarioParaAtualizar.getNome());
			resp.setFoto(usuarioParaAtualizar.getFoto());
			resp.setEmail(usuarioParaAtualizar.getEmail());
			resp.setDescricao(usuarioParaAtualizar.getDescricao());
			resp.setSenha(encriptadorDeSenha(usuarioParaAtualizar.getSenha()));
			return Optional.ofNullable(repository.save(resp));

		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	/**
	 * Metodo estatico utilizado para gerar token formato Basic
	 * 
	 * <p>
	 * estrutura ex. gustavo@email.com:134652
	 * <p>
	 * estruturaBase64 ex. cGFtZWxhQGVtYWlsLmNvbToxMzQ2NTI
	 * <p>
	 * estruturaBasic = Basic cGFtZWxhQGVtYWlsLmNvbToxMzQ2NTI
	 * 
	 * @param email
	 * @param senha
	 * @return Token no formato Basic para autenticação
	 * @since 1.0
	 * @author Abner
	 * 
	 */

	private static String geradorDeToken(String email, String senha) {
		String estrutura = email + ":" + senha;
		byte[] estruturaBase64 = Base64.encodeBase64(estrutura.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(estruturaBase64);
	}

	/**
	 * Metodo utilizado para pegar credenciais do usuario com Tokem (Formato Basic),
	 * este método sera utilizado para retornar ao front o token utilizado para ter
	 * acesso aos dados do usuario e mantelo logado no sistema
	 * 
	 * @param usuarioParaAutenticar do tipo UsuarioLoginDTO necessario email e senha
	 *                              para validar
	 * @return ResponseEntity com CredenciaisDTO preenchido com informações mais o
	 *         Token
	 * @since 1.0
	 * @author Abner
	 * 
	 */

	public ResponseEntity<CredentialsDTO> pegaCredenciais(UserLoginDTO usuariopraAutenticar) {
		return repository.findByEmail(usuariopraAutenticar.getEmail()).map(resp -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(usuariopraAutenticar.getSenha(), resp.getSenha())) {
				CredentialsDTO objetoCredentials = new CredentialsDTO();

				objetoCredentials
						.setToken(geradorDeToken(usuariopraAutenticar.getEmail(), usuariopraAutenticar.getSenha()));
				objetoCredentials.setIdUsuario(resp.getIdUsuario());
				objetoCredentials.setNome(resp.getNome());
				objetoCredentials.setEmail(resp.getEmail());
				objetoCredentials.setSenha(resp.getSenha());
				objetoCredentials.setTipo(resp.getTipo());
				objetoCredentials.setFoto(resp.getFoto());
				objetoCredentials.setDescricao(resp.getDescricao());

				return ResponseEntity.status(201).body(objetoCredentials);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha Incorreta.");
			}
		}).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail não cadastrado");
		});
	}

}
