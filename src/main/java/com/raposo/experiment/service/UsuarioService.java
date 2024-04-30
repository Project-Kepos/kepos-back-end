package com.raposo.experiment.service;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Optional;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Usuario;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    IUsuarioRepository usuarioRepository;

    Logger logger = LogManager.getLogger(getClass());

    public void setDendroRepository(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        logger.info("cadastrar usuario");
        return Optional.of(usuarioRepository.save(usuario));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        logger.info("Consultando todos os Dendros");
        return usuarioRepository.findAll();
    }
    
}
