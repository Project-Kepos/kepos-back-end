package com.raposo.experiment.service;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired /* Injetando classe para criptografar senha, no padrão que o springsecurity exige */
    private PasswordEncoder passwordEncoder;

    Logger logger = LogManager.getLogger(getClass());

    public void setDendroRepository(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        logger.info("cadastrar usuario");
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return Optional.of(usuarioRepository.save(usuario));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        logger.info("Listando usuarios");
        return usuarioRepository.findAll();
    }

    @Override
    public boolean verificarEmail(String email) {
        try {
            return usuarioRepository.existsByEmail(email);
        } catch (Exception e) {
            e.printStackTrace(); 
            return false; 
        }
    }
    
}
