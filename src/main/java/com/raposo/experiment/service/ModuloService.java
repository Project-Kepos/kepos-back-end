package com.raposo.experiment.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	Logger logger = LogManager.getLogger(getClass());

	@Autowired
	IModuloRepository moduloRepository;

	@Autowired
	IDendroRepository dendroRepository;

	// TODO: Revisar necessidade deste método
	public List<Modulo> consultaTodosModulos() {
		logger.info("Consultando todos os Modulos");

		return moduloRepository.findAll();
	}

	public Modulo consultaPorId(Long id) {
		logger.info("Consultando Modulo por id");

		var modulo = moduloRepository.findById(id);

		if (modulo.isEmpty()) {
			throw new EntityNotFoundException("Módulo não encontrado com o id fornecido.");
		}

		return modulo.get();
	}

	public List<Modulo> consultaPorDendro(String id) {
		logger.info("Consultando Modulo por Dendro");

		if (!dendroRepository.existsById(id)) {
			throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
		}

		return moduloRepository.findAllByDendro_Id(id);
	}

	// TODO: Verifica se de fato o limite de módulos será 4 por dendro
	// TODO: Impedir valores null pós testes
	public Modulo cadastrarModulo(ModuloDTO json) {
		logger.info("Cadastrando Modulo");

		var dendro = dendroRepository.findById(json.idDendro());

		if (dendro.isEmpty()) {
			throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
		}

		if (dendro.get().getModules().size() >= 4) {
			throw new ErroCustomizado("O limite máximo de módulos por dendro são 4.");
		}

		var modulo = new Modulo(json.name(), json.desc(), json.humidity(), json.humidityLevel(), dendro.get());

		return moduloRepository.save(modulo);
	}

	// TODO: Impedir valores null pós testes
	public Modulo atualizarModulo(Long id, ModuloDTO json) {
		logger.info("Atualizando Modulo");

		var modulo = moduloRepository.findById(id);

		if (modulo.isEmpty()) {
			throw new EntityNotFoundException("Módulo não encontrado com o id fornecido.");
		}

		modulo.get().atualizarModulo(json);

		return modulo.get();
	}

	public void deletarModulo(Long id) {
		logger.info("Deletando Modulo");

		moduloRepository.deleteById(id);
	}
}
