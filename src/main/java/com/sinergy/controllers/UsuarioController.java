package com.sinergy.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.sinergy.models.Usuario;
import com.sinergy.repositories.UsuarioRepository;
import com.sinergy.services.UsuarioService;

@RestController
@RequestMapping("api/v1/usario")
@CrossOrigin("*")
public class UsuarioController {

	private UsuarioRepository repositorio;
	private UsuarioService servico;

	@GetMapping("/todos")
	public ResponseEntity<List<Usuario>> GetAll() {
		List<Usuario> lista = repositorio.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(lista);
		}
	}

	@GetMapping("{id]")
	public ResponseEntity<Usuario> getById(@PathVariable(value = "id_usuario") Long idUsario) {
		return repositorio.findById(idUsario).map(resp -> ResponseEntity.status(200).body(resp)) // usa o métod
																									// findById,
																									// procurando o
																									// idUsuario, se
																									// achar, a resposta
																									// é 200, e
																									// apresenta o
																									// usuário
				.orElse(ResponseEntity.status(400).build()); // se ocorrer algo errado, a resposta é 400
	}

	@PostMapping("/salvar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario usuarioNovo) {
		return servico.cadastraUsuario(usuarioNovo).map(resp -> ResponseEntity.status(201).body(resp))
				.orElse(ResponseEntity.status(400).build());
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioNovo) {
		return ResponseEntity.status(201).body(repositorio.save(usuarioNovo));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Object> deletar(@PathVariable(value = "id_usuario") Long idUsuario) {
		// procurará o Id com o método findByI
		return repositorio.findById(idUsuario).map(resp -> {
			repositorio.deleteById(idUsuario); // vai apagar esse usuário do id inserido
			return ResponseEntity.status(200).build(); // se apagado, status 200
		}).orElse(ResponseEntity.status(400).build()); // se ocorrer algo errado, a resposta é 400
	}
}
