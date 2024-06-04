package com.raposo.experiment.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raposo.experiment.config.error.ErroCustomizado;
import com.raposo.experiment.dto.DendroDTO;
import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.IDendroRepository;
import com.raposo.experiment.model.IUsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DendroService implements IDendroService {

    @Autowired
    IDendroRepository dendroRepository;

    @Autowired
    IUsuarioRepository usuarioRepository;

    Logger logger = LogManager.getLogger(getClass());

    // TODO: Revisar necessidade deste método
    public List<Dendro> consultaTodasDendros() {
        logger.info("Consultando todos os Dendros");

        return dendroRepository.findAll();
    }

    // TODO: Revisar necessidade deste método
    public List<Dendro> consultaDendrosPorNome(String nome) {
        logger.info("Consultando Dendros por nome");

        return dendroRepository.findByName(nome);
    }

    public List<Dendro> consultaDendrosPorUsuario(Long userId) {
        return dendroRepository.findAllByUser_Id(userId);
    }

    public Dendro consultaDendroPorId(String id) {
        logger.info("Consultando Dendro por id");

        var dendro = dendroRepository.findById(id);

        if (dendro.isEmpty()) {
            throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
        }

        return dendro.get();
    }

    // TODO: Impedir valores null pós testes
    public Dendro cadastrarDendro(DendroDTO json) {
        logger.info("Cadastrando Dendro");

        if (json.id() == null) {
            throw new ErroCustomizado("O id da dendro é obrigatório");
        }

        var dendro = new Dendro(json.id(), json.name(), json.temperature(), json.humidity(), json.luminosity());

        return dendroRepository.save(dendro);
    }

    public Dendro adicionarUsuario(Long userId, DendroDTO json) {
        if (json.id() == null) {
            throw new ErroCustomizado("O id da dendro é obrigatório.");
        }

        var dendro = dendroRepository.findById(json.id());

        if (dendro.isEmpty()) {
            throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
        }

        if (dendro.get().getUser() != null) {
            throw new ErroCustomizado("A dendro já possui um usuário associado");
        }

        var user = usuarioRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado com o id fornecido.");
        }

        dendro.get().setUser(user.get());
        return dendro.get();
    }

    // TODO: Impedir valores null pós testes
    public Dendro atualizarDendro(String id, DendroDTO json) {
        logger.info("Atualizando Dendro");

        var dendro = dendroRepository.findById(id);

        if (dendro.isEmpty()) {
            throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
        }

        dendro.get().atualizarDendro(json);
        

        return dendro.get();
    }
    public Dendro removerUsuarioDendro(DendroDTO json) {
        logger.info("Atualizando Dendro");

        var dendro = dendroRepository.findById(json.id());

        if (dendro.isEmpty()) {
            throw new EntityNotFoundException("Dendro não encontrada com o id fornecido.");
        }

            dendro.get().setUser(null);
        
        

        return dendro.get();
    }

    public void deletarDendro(String id) {
        logger.info("Deletando Dendro");

        dendroRepository.deleteById(id);
    }

}
