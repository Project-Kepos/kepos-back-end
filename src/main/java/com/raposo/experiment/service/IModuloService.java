package com.raposo.experiment.service;

import java.util.List;

import com.raposo.experiment.dto.ModuloDTO;
import com.raposo.experiment.model.Modulo;

public interface IModuloService {
	
	public List<Modulo> consultaTodosModulos();

	public Modulo consultaPorId(Long id);

	public List<Modulo> consultaPorDendro(String id);

	public Modulo cadastrarModulo(ModuloDTO json);

	public Modulo atualizarModulo(Long id, ModuloDTO json);

	public void deletarModulo(Long id);

}
