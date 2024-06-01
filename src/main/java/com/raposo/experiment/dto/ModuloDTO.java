package com.raposo.experiment.dto;

import com.raposo.experiment.model.Modulo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModuloDTO(
		Long id,
		
		@NotBlank(message = "O nome é obrigatório.")
		String name,
		
		String description,
		
		Double moisture,
		
		@NotNull(message = "O id da dendro associada é obrigatório")
		Long dendroId
) {
	
	public ModuloDTO(Modulo modulo) {
		this(modulo.getId(), modulo.getName(), modulo.getDescription(), modulo.getMoisture(), modulo.getDendro().getId());
	}

}
