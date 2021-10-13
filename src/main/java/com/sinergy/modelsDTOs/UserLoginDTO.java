package com.sinergy.modelsDTOs;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author George
 * @since 1.0
 *
 */
public class UserLoginDTO {

	private @NotBlank @Email(message = "Campo deve ser um email") String email;
	private @NotBlank @Size(min = 5, max = 15, message = "Senha deve ter de 5 á 15 caracteres") String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
