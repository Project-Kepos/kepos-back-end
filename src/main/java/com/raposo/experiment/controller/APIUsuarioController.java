package com.raposo.experiment.controller;

import com.raposo.experiment.config.DadosTokenJWT;
import com.raposo.experiment.config.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import com.raposo.experiment.config.error.ErroCustomizadoDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@RequestMapping("/api/v1/usuario")
public class APIUsuarioController {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IUsuarioService usuarioService;

    @Autowired /* Injetando classe para realizar login e autenticação */
    private AuthenticationManager manager;

    @Autowired /* Injetando serviço de geração de token jwt para autenticação */
    private TokenService tokenService;

    private final String idclie = "idCliente";

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {
        try{
        logger.info("Cadastrando usuario");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.cadastrarUsuario(usuario));
        }
        catch(Exception e){
            logger.info("Falha ao cadastrar");
            return ResponseEntity.badRequest().body(new ErroCustomizadoDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object>efetuarLogin(@RequestBody Usuario usuario){
        try {
            var token = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
            var authentication = manager.authenticate(token);

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErroCustomizadoDTO("E-mail ou senha inválidos"));
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Object> consultaUsuario() {
        logger.info("Listando usuarios no sistema");

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }
    
}
