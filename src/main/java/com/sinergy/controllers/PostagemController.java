package com.sinergy.controllers;

/**
 * Classe Controlador com metodos CRUD
 * 
 * @author gustavo
 * @since 1.0
 * 
 */

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sinergy.models.Postagem;
import com.sinergy.repositories.PostagemRespository;

@RestController
@RequestMapping("/sinergy/posts")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostagemController {

	@Autowired
	private PostagemRespository repository;

	@GetMapping("/allPosts")
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/legenda/{legenda}")
	public ResponseEntity<List<Postagem>> GetByLegenda(@PathVariable String legenda) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(legenda));
	}

//	@GetMapping("/editado/true")
//	public ResponseEntity<List<Postagem>> GetByEditadoTrue() {
//		return ResponseEntity.status(201).body(repository.findAllByEditadoTrue());
//	}

	@PostMapping("/salvar")
	public ResponseEntity<Postagem> salvar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repository.save(novaPostagem));
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> atualizar(@Valid @RequestBody Postagem atualizarPostagem) {
		return ResponseEntity.status(201).body(repository.save(atualizarPostagem));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Postagem> deletar(@PathVariable(value = "id") Long idPostagem) {
		Optional<Postagem> objetoOptional = repository.findById(idPostagem);

		if (objetoOptional.isPresent()) {
			repository.deleteById(idPostagem);
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}
}
