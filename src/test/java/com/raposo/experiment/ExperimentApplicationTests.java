package com.raposo.experiment;

import com.raposo.experiment.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ExperimentApplicationTests {

	@Autowired
	private UsuarioService usuarioService;
	@Test
	void contextLoads() {
	}

	//Dayvid
	@Test
	public void testListagem(){
		Logger logger = LogManager.getLogger(getClass());

		var response = usuarioService.listarUsuarios();
		Assertions.assertTrue(response.isEmpty());

	}

	//Alberto
   	@Autowired
	UsuarioService us = new UsuarioService();
	@Test
	public void userRegisterTest(){
		Usuario u = new Usuario("João","joao@gmail.com","1234567");
		var response = us.cadastrarUsuario(u);
		Assertions.assertTrue(response.isPresent());
	}


}
