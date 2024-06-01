package com.raposo.experiment.service;

import java.util.List;

import com.raposo.experiment.dto.ModuloDTO;
import com.raposo.experiment.model.Modulo;

public interface IModuloService {
	public List<Modulo> listarModulosPorDendro(Long idDendro);
	public Modulo listarPorId(Long id);
	public Modulo cadastrarModulo(ModuloDTO json);
	public Modulo atualizarModulo(ModuloDTO json);
	public void deletarModulo(Long id);
}
