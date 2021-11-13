package com.sinergy.controllers;

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

import com.sinergy.models.Tema;
import com.sinergy.repositories.TemaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de Tema
 * 
 * @author Sinergy - Grupo 2
 * @since 1.0
 * @date 29/09/2021
 */

@RestController
@RequestMapping("/tema")
@Api(tags = "Controladores de Tema", description = "Métodos CRUD da classe 'Tema'")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TemaController {

	private @Autowired TemaRepository repositorio;

	@ApiOperation(value = "Busca todos os temas no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista de temas"),
			@ApiResponse(code = 204, message = "Sem temas no sistema")
	})
	@GetMapping("/todos")
	public ResponseEntity<List<Tema>> pegarTodos() {
		List<Tema> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@ApiOperation(value = "Busca tema por ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna tema existente"),
			@ApiResponse(code = 204, message = "Não existe tema com esse ID")
	})
	@GetMapping("/{id_tema}")
	public ResponseEntity<Tema> pegarPorId(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoOptional = repositorio.findById(idTema);

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
	public ResponseEntity<List<Tema>> GetByDoacao(@PathVariable String titulo) {
		return ResponseEntity.ok(repositorio.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@ApiOperation(value = "Busca tema por criador")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista com temas que contenham o criador digitado"),
			@ApiResponse(code = 204, message = "Não existe tema com esse criador")
	})
	@GetMapping("/criador/{criador}")
	public ResponseEntity<List<Tema>> GetByCriador(@PathVariable String criador) {
		return ResponseEntity.ok(repositorio.findAllByCriadorContainingIgnoreCase(criador));
	}

	@ApiOperation(value = "Cadastra novo tema no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna tema cadastrado"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	@PostMapping("/salvar")
	public ResponseEntity<Tema> salvar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));
	}

	@ApiOperation(value = "Atualizar tema existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna tema atualizado"),
			@ApiResponse(code = 400, message = "ID de tema invalido")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<Tema> atualizar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));

	}

	@ApiOperation(value = "Deletar tema existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "tema Deletado"),
			@ApiResponse(code = 400, message = "ID de tema invalido!")
	})
	@DeleteMapping("/deletar/{id_tema}")
	public ResponseEntity<Tema> deletar(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoOptional = repositorio.findById(idTema);

		if (objetoOptional.isPresent()) {
			repositorio.deleteById(idTema);
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(400).build();
		}
	}

}
