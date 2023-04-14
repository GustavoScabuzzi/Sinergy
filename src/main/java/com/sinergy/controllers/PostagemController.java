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

import netscape.javascript.JSObject;
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
import com.sinergy.repositories.PostagemRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/postagens")
@Api(tags = "Controladores de Postagem", description = "Métodos CRUD da classe 'Postagem'")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	@ApiOperation(value = "Busca todas as postagens no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista de postagens"),
			@ApiResponse(code = 204, message = "Sem postagens no sistema")
	})
	@GetMapping("/todos")
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

//	@GetMapping("/{id_postagem}")
//	public ResponseEntity<Postagem> GetById(@PathVariable Long id_Postagem) {
//		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
//	}
	
	@ApiOperation(value = "Busca postagem por ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna postagem existente"),
			@ApiResponse(code = 204, message = "Não existe postagens com esse ID")
	})
	@GetMapping("/{id_postagem}")
	public ResponseEntity<Postagem> GetById(@PathVariable(value = "id_postagem") Long idPostagem) {
		Optional<Postagem> objetoOptional = repository.findById(idPostagem);

		if (objetoOptional.isPresent()) {
			return ResponseEntity.status(200).body(objetoOptional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	@ApiOperation(value = "Busca tema por titulo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista com temas que contenham a palavras digitada"),
			@ApiResponse(code = 204, message = "Não existe tema com esse nome")
	})
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@ApiOperation(value = "Cadastra nova postagem no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna postagem cadastrada"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	@PostMapping("/salvar")
	public ResponseEntity<Postagem> salvar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repository.save(novaPostagem));
	}

	@ApiOperation(value = "Atualizar postagem existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna postagem atualizada"),
			@ApiResponse(code = 400, message = "ID de postagem invalido")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> atualizar(@Valid @RequestBody Postagem atualizarPostagem) {
		return ResponseEntity.status(201).body(repository.save(atualizarPostagem));
	}

	@ApiOperation(value = "Deletar postagem existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "postagem Deletada"),
			@ApiResponse(code = 400, message = "ID de postagem invalido!")
	})
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

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return null;
	}
}
