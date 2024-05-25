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


}
