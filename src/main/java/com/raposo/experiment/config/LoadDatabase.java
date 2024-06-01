package com.raposo.experiment.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.IDendroRepository;
import com.raposo.experiment.model.IModuloRepository;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Modulo;
import com.raposo.experiment.model.Usuario;

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
			var usuario = salvarUsuarios();
			var dendro = salvarDendros(usuario);
			salvarModulos(dendro);
		};
	}

	private Usuario salvarUsuarios() {
		var senha = passwordEncoder.encode("12345");
		var novoUsuario = new Usuario("Usuario da Silva", "usuario@email.com", senha);

		repositoryUsuario.save(novoUsuario);
		logger.info("Usuário teste carregado no banco de dados: " + novoUsuario);
		return novoUsuario;
	}

	private Dendro salvarDendros(Usuario user) {
		List<Modulo> modulos = new ArrayList<>();

		Dendro dendro01 = new Dendro(1L, "Dendro 01", 0, 31.22, 0.0, user, modulos);
		Dendro dendro02 = new Dendro(2L, "Dendro 02", 45, 28.22, 0.0, user, modulos);
		Dendro dendro03 = new Dendro(3L, "Dendro 03", 90, 16.71, 0.0, user, modulos);
		Dendro dendro04 = new Dendro(4L, "Dendro 04", 180, 22.44, 0.0, user, modulos);

		dendroRepository.saveAll(Arrays.asList(dendro01, dendro02, dendro03, dendro04));

		logger.info("Dendros carregados no banco de dados");

		return dendro01;
	}

	private void salvarModulos(Dendro dendro) {
		var modulo1 = new Modulo("Salsa", "Módulo de salsa", 0.0, dendro);
		var modulo2 = new Modulo("Manjericão", "Módulo de Manjericão", 0.0, dendro);

		moduloRepository.saveAll(Arrays.asList(modulo1, modulo2));
	}

}