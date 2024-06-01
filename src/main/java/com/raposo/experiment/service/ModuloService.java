package com.raposo.experiment.service;

import java.util.List;
import java.util.Optional;

import com.raposo.experiment.model.Modulo;
import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.IDendroRepository;
import com.raposo.experiment.model.IModuloRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuloService implements IModuloService{

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public IModuloRepository getModuloRepository() {
        return this.moduloRepository;
    }

    public void setModuleRepository(IModuloRepository moduleRepository) {
        this.moduloRepository = moduloRepository;
    }

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IModuloRepository moduloRepository;

    @Autowired
    IDendroRepository dendroRepository;

    @Override
    public List<Modulo> consultaModulos() {
        logger.info("Consultando todos os Modulos");

        return moduloRepository.findAll();
    }

    @Override
    public Optional<Modulo> consultaPorId(Long id) {
        logger.info("Consultando Modulo por id");

        return moduloRepository.findById(id);
    }

    @Override
    public List<Modulo> consultaPorDendro(String id) {
        logger.info("Consultando Modulo por Dendro");

        return moduloRepository.findByDendroId(dendroRepository.findById(id).get());
    }

    @Override
    public Optional<Modulo> cadastrarModulo(Modulo modulo) {
        logger.info("Cadastrando Modulo");

        return Optional.ofNullable(moduloRepository.save(modulo));
    }

    @Override
    public Optional<Modulo> atualizarModulo(Modulo modulo) {
        logger.info("Atualizando Modulo");
        
        return moduloRepository.findById(modulo.getId())
            .map(record -> {
                record.setName(modulo.getName());
                record.setHumidity(modulo.getHumidity());
                record.setHumidityLevel(modulo.getHumidityLevel());

                return moduloRepository.save(record);
            });
    }

    @Override
    public void deletarModulo(Long id) {
        logger.info("Deletando Modulo");

        moduloRepository.deleteById(id);
    }
}
