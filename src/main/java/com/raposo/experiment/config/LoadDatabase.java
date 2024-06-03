package com.raposo.experiment.config;

import com.raposo.experiment.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class LoadDatabase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioRepository repositoryUsuario;

    @Autowired
    private IDendroRepository dendroRepository;

    @Autowired
    private IModuloRepository moduloRepository;

    Logger logger = LogManager.getLogger(this.getClass());

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            salvarUsuarios();
            var dendro = salvarDendros();
            salvarModulos(dendro);
        };
    }

    private void salvarUsuarios() {
        var senha = passwordEncoder.encode("12345");
        var novoUsuario = new Usuario("Usuario da Silva", "usuario@email.com", senha);

        repositoryUsuario.save(novoUsuario);
        logger.info("Usuário teste carregado no banco de dados: " + novoUsuario);
    }

    private Dendro salvarDendros() {
        Dendro dendro01 = new Dendro("11111111111", "Dendro 01", 0.0, 31.22, 0);
        Dendro dendro02 = new Dendro("22222222222", "Dendro 02", 45.0, 28.22, 0);
        Dendro dendro03 = new Dendro("33333333333", "Dendro 03", 90.0, 16.71, 0);
        Dendro dendro04 = new Dendro("44444444444", "Dendro 04", 180.0, 22.44, 0);

        dendroRepository.saveAll(Arrays.asList(dendro01, dendro02, dendro03, dendro04));

        logger.info("Dendros carregados no banco de dados");

        return dendro01;
    }

    private void salvarModulos(Dendro dendro) {
        var modulo1 = new Modulo("Alecrim", "Descrição", 0, 0, dendro);
        var modulo2 = new Modulo("Manjericão", "Descrição", 0, 0, dendro);

        moduloRepository.saveAll(Arrays.asList(modulo1, modulo2));
    }

}