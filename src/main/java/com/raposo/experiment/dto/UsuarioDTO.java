package com.raposo.experiment.dto;

import java.util.List;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
		Long id,
		
		@NotBlank(message = "O nome é obrigatório.")
		String nome,
		
		@NotBlank(message = "O e-mail é obrigatório.")
		@Email(message = "Insira um e-mail válido.")
		String email,
		
		@NotBlank(message = "A senha é obrigatória.")
		String senha,
		
		List<Dendro> dendros
) {
	
	public UsuarioDTO(Usuario user) {
		this(user.getId(), user.getNome(), user.getEmail(), user.getSenha(), user.getDendros());
	}
	
}
