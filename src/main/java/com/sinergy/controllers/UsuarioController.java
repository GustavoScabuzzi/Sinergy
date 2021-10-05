package com.sinergy.controllers;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("api/v1/usario")
@CrossOrigin("*")
public class UsuarioController {

	private UsuarioRepository repositorio;

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

		Optional<Usuario> optionalId = repositorio.findById(idUsario);

		if (optionalId.isPresent()) {
			return ResponseEntity.status(200).body(optionalId.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	@PostMapping("/salvar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario usuarioNovo) {
		return ResponseEntity.status(201).body(repositorio.save(usuarioNovo));
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioNovo) {
		return ResponseEntity.status(201).body(repositorio.save(usuarioNovo));
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Usuario> deletar(@PathVariable(value = "id_usuario") Long idUsuario) {
		Optional<Usuario> optionalDeletar = repositorio.findById(idUsuario);

		if (optionalDeletar.isPresent()) {
			repositorio.deleteById(idUsuario);
			;
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(400).build();
		}

	}
}
