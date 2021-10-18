package com.sinergy.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe tema do projeto Sinergy
 *
 * @author George
 * @vs 1.0
 * 
 */

@Entity
@Table(name = "tema")
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTema;

	@OneToMany(mappedBy = "temaRelacionado", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "temaRelacionado" })
	private List<Postagem> postagens = new ArrayList<>();

	@NotBlank
	@Size(min = 5, max = 100)
	private String tema;

	@NotNull
	private Boolean doacao;

	@NotNull
	private Boolean voluntariado;

	@NotNull
	private Boolean informativo;

	@NotNull
	private Boolean any;

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Boolean getDoacao() {
		return doacao;
	}

	public void setDoacao(Boolean doacao) {
		this.doacao = doacao;
	}

	public Boolean getVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(Boolean voluntariado) {
		this.voluntariado = voluntariado;
	}

	public Boolean getInformativo() {
		return informativo;
	}

	public void setInformativo(Boolean informativo) {
		this.informativo = informativo;
	}

	public Boolean getAny() {
		return any;
	}

	public void setAny(Boolean any) {
		this.any = any;
	}

}
