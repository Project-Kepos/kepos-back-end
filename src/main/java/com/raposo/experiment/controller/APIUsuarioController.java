package com.raposo.experiment.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.Usuario;
import com.raposo.experiment.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class APIUsuarioController {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping(value = "usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {
        logger.info("Cadastrando usuario");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.cadastrarUsuario(usuario));
    }

    @GetMapping("usuario")
    @Transactional
    public ResponseEntity<Object> consultaDendro() {
        logger.info("Listando usuarios no sistema");

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }
    
}
