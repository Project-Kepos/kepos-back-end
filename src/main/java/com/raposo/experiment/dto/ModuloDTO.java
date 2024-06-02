package com.raposo.experiment.dto;

import com.raposo.experiment.model.Modulo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModuloDTO(
		Long id,
		
		@NotBlank(message = "O nome é obrigatório.")
		String name,
		
		String desc,
		
		Integer humidity,
		
		Integer humidityLevel,
		
		@NotNull(message = "O id da dendro associada é obrigatório")
		String idDendro
) {
	
	public ModuloDTO(Modulo modulo) {
		this(modulo.getId(), modulo.getName(), modulo.getDesc(), modulo.getHumidity(), modulo.getHumidityLevel(), modulo.getDendro().getId());
	}

}
