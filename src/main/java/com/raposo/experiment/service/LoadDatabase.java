package com.raposo.experiment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.IDendroRepository;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Usuario;

@Configuration
public class LoadDatabase {
    Logger logger = LogManager.getLogger(this.getClass());

    @Bean
    CommandLineRunner initDatabase(IDendroRepository dendroRepository, IUsuarioRepository usuarioRepository) {
        return args -> {
            Dendro dendro01 = new Dendro((long) 01, "Dendro 01", 0);
            Dendro dendro02 = new Dendro((long) 02, "Dendro 02", 45);
            Dendro dendro03 = new Dendro((long) 03, "Dendro 03", 90);
            Dendro dendro04 = new Dendro((long) 04, "Dendro 04", 180);

            List<Usuario> usuarios = new ArrayList<>();

        // Adiciona alguns usuários de exemplo
        usuarios.add(new Usuario("João", "joao@example.com", "senha123"));
        usuarios.add(new Usuario("Maria", "maria@example.com", "senha456"));
        usuarios.add(new Usuario("Pedro", "pedro@example.com", "senha789"));

            
            dendroRepository.saveAll(Arrays.asList(dendro01, dendro02, dendro03, dendro04));
            usuarioRepository.saveAll(usuarios);

            logger.info("Dendros carregados no banco de dados");
        };
            
    }
}
