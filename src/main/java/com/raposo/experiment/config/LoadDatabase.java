package com.raposo.experiment.config;

import java.util.Arrays;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.IDendroRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Usuario;

@Configuration
public class LoadDatabase {
    @Autowired
    private IUsuarioRepository repositoryUsuario;
    
    // classe para criptografar senha
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;

    Logger logger = LogManager.getLogger(this.getClass());

    CommandLineRunner initDatabase(IDendroRepository dendroRepository) {
        return args -> {
            Dendro dendro01 = new Dendro((long) 01, "Dendro 01", 0);
            Dendro dendro02 = new Dendro((long) 02, "Dendro 02", 45);
            Dendro dendro03 = new Dendro((long) 03, "Dendro 03", 90);
            Dendro dendro04 = new Dendro((long) 04, "Dendro 04", 180);

            dendroRepository.saveAll(Arrays.asList(dendro01, dendro02, dendro03, dendro04));

            logger.info("Dendros carregados no banco de dados");
        };
    }
    
    CommandLineRunner initDatabase(IUsuarioRepository  repository) {
        return args -> {
            var senha = PasswordEncoder.encode("12345");
            var novoUsuario = new Usuario("Usuario da Silva", "usuario@email.com", senha);
            
            repositoryUsuario.save(novoUsuario);
            logger.info("Usuário teste carregado no banco de dados: " + novoUsuario);
        };
    }

}