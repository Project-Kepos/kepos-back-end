package com.raposo.experiment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raposo.experiment.config.error.ErroCustomizado;
import com.raposo.experiment.dto.ModuloDTO;
import com.raposo.experiment.model.IDendroRepository;
import com.raposo.experiment.model.IModuloRepository;
import com.raposo.experiment.model.Modulo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ModuloService implements IModuloService {

	@Autowired
	IModuloRepository moduloRepository;

	@Autowired
	IDendroRepository dendroRepository;

	public List<Modulo> listarModulosPorDendro(Long idDendro) {
		if (!dendroRepository.existsById(idDendro)) {
			throw new EntityNotFoundException();
		}

		return moduloRepository.findByDendroId(idDendro);
	}

	public Modulo listarPorId(Long id) {
		var modulo = moduloRepository.findById(id);

		if (modulo.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return modulo.get();
	}

	public Modulo cadastrarModulo(ModuloDTO json) {
		var dendro = dendroRepository.findById(json.dendroId());

		if (dendro.isEmpty()) {
			throw new EntityNotFoundException();
		}

		if (dendro.get().getModules().size() >= 4) {
			throw new ErroCustomizado("O limite máximo de módulos por dendro são 4.");
		}

		var modulo = criarModelModulo(json);
		return moduloRepository.save(modulo);
	}

	public Modulo atualizarModulo(ModuloDTO json) {
		if (json.id() == null) {
			throw new ErroCustomizado("O id do módulo é obrigatório.");
		}

		if (!moduloRepository.existsById(json.id())) {
			throw new EntityNotFoundException();
		}

		if (!dendroRepository.existsById(json.dendroId())) {
			throw new EntityNotFoundException();
		}

		var modulo = criarModelModulo(json);
		return moduloRepository.save(modulo);
	}

	public void deletarModulo(Long id) {
		moduloRepository.deleteById(id);
	}

	private Modulo criarModelModulo(ModuloDTO json) {
		var modulo = new Modulo();
		var dendro = dendroRepository.findById(json.dendroId()).get();

		modulo.setId(json.id());
		modulo.setName(json.name());
		modulo.setDescription(json.description());
		modulo.setMoisture(json.moisture());
		modulo.setDendro(dendro);

		return modulo;
	}

}
