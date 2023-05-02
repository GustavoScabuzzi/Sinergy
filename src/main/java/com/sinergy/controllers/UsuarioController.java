package com.sinergy.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sinergy.models.Usuario;
import com.sinergy.DTO.CredentialsDTO;
import com.sinergy.DTO.UserLoginDTO;
import com.sinergy.repositories.UsuarioRepository;
import com.sinergy.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuario")
@Api(tags = "USUARIO - Controladores", description = "Métodos CRUD da classe 'Usuario'")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UsuarioController {

	private @Autowired UsuarioRepository repositorio;
	private @Autowired UsuarioService servico;

	@ApiOperation(value = "Busca lista de usuarios no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna com Usuarios"),
			@ApiResponse(code = 204, message = "Não existe usuarios")
	})
	@GetMapping("/todos")
	public ResponseEntity<List<Usuario>> GetAll() {
		List<Usuario> lista = repositorio.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(lista);
		}
	}

	@ApiOperation(value = "Busca lista de usuarios no sistema pelo 'ID'")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna usuario existente ou inexistente"),
			@ApiResponse(code = 400, message = "Retorno inexistente")
	})
	@GetMapping("/{id_usuario}")
	public ResponseEntity<Usuario> getById(@PathVariable(value = "id_usuario") Long idUsario) {
		return repositorio.findById(idUsario).map(resp -> ResponseEntity.status(200).body(resp)).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Id inexistente, insira um id válido para pesquisa");
		});
	}

	@ApiOperation(value = "Cadastra novo usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario cadastrado"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	@PostMapping("/salvar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario usuarioNovo) {
		return servico.cadastraUsuario(usuarioNovo).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"E-mail já cadastrado, cadastre outro e-mail");
				});
	}

	@ApiOperation(value = "Atualizar usuario existente! ok")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario atualizado"),
			@ApiResponse(code = 400, message = "ID de usuario invalido")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioNovo) {
		return servico.atualizaUsuario(usuarioNovo).map(resp -> ResponseEntity.status(201).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Necessário um id válido para alterar.");
				});
	}

	@ApiOperation(value = "Login de usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna credenciais de usuario + Token"),
			@ApiResponse(code = 400, message = "Erro na requisição!")
	})
	@PutMapping("/logar")
	public ResponseEntity<CredentialsDTO> credenciais(@Valid @RequestBody UserLoginDTO usuarioParaAutenticar) {
		return servico.pegaCredenciais(usuarioParaAutenticar);
	}

	@ApiOperation(value = "Deletar usuario existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario Deletado"),
			@ApiResponse(code = 400, message = "ID de usuario invalido!")
	})
	@DeleteMapping("/deletar/{id_usuario}")
	public ResponseEntity<Object> deletar(@PathVariable(value = "id_usuario") Long idUsuario) {
		// procurará o Id com o método findById
		return repositorio.findById(idUsuario).map(resp -> {
			repositorio.deleteById(idUsuario); // vai apagar esse usuário do id inserido
			return ResponseEntity.status(200).build(); // se apagado, status 200
		}).orElse(ResponseEntity.status(400).build()); // se ocorrer algo errado, a resposta é 400
	}
}
