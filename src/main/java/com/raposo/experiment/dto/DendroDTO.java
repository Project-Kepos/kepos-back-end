package com.raposo.experiment.dto;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.Usuario;

import jakarta.validation.constraints.NotBlank;

public record DendroDTO(
		
		String id,
		
		String name,
		
		Double temperature,
		
		Double humidity,
		
		Integer luminosity
) {
	
	public DendroDTO(Dendro dendro) {
		this(dendro.getId(), dendro.getName(), dendro.getTemperature(), dendro.getHumidity(), dendro.getLuminosity());
	}
	
}
