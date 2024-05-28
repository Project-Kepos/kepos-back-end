package com.raposo.experiment.controller;

import com.raposo.experiment.config.AutenticacaoService;
import com.raposo.experiment.config.DadosTokenJWT;
import com.raposo.experiment.config.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import com.raposo.experiment.config.error.ErroCustomizadoDTO;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    AutenticacaoService at;

    @Autowired /* Injetando serviço de geração de token jwt para autenticação */
    private TokenService tokenService;

    private final String idclie = "idCliente";

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            logger.info("Cadastrando usuario");

            if (usuarioService.verificarEmail(usuario.getEmail()))
                return ResponseEntity.badRequest().body("Email já cadastrado");

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.cadastrarUsuario(usuario));
        } catch (Exception e) {
            logger.info("Falha ao cadastrar");
            return ResponseEntity.badRequest().body(new ErroCustomizadoDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> efetuarLogin(@RequestBody Usuario usuario) {
        try {
            logger.info("Tentando login ");

            var token = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
            var authentication = manager.authenticate(token);

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (AuthenticationException e) {
            String msg;
            if (!usuarioService.verificarEmail(usuario.getEmail())){ msg ="Conta não encontrada";logger.info(msg);}
            else{ msg = "E-mail ou senha inválidos";}
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErroCustomizadoDTO(msg));
        }
    }

    @PostMapping("/consultaEmail")
    public ResponseEntity<Object> consultaEmail(@RequestBody String email) {
        try {
            return ResponseEntity.ok(usuarioService.verificarEmail(email));
        } catch (Exception e) {
            return ResponseEntity.ok(e);
        }
    }

    @GetMapping("/listar")
    @Transactional
    public ResponseEntity<Object> consultaUsuario() {
        logger.info("Listando usuarios no sistema");

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    @GetMapping
    @Transactional
    // Puxa informações sobre usuario através da coleta do email no token
    public ResponseEntity<Object> buscaDadosUsuario(HttpServletRequest request) {
        logger.info("-------------------------------------------------");
        logger.info("Puxando dados do usuario");
        String token = request.getHeader("Authorization");
        String email = null;
    
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            logger.info("Token "+jwtToken);
            
            try {
                String[] parts = jwtToken.split("\\.");
                String payload = new String(Base64.getDecoder().decode(parts[1]));
                int startIndex = payload.indexOf("\"email\"") + 9;
                int endIndex = payload.indexOf("\"", startIndex);
                email = payload.substring(startIndex, endIndex);
                logger.info("Email  "+email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        if (email != null) {
            try{
            return ResponseEntity.status(HttpStatus.OK).body(at.loadUserByUsername(email));
            }catch (Exception e) {
                return ResponseEntity.ok(e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token JWT ausente, mal formatado ou email não encontrado");
        }
    
    }
    

}
