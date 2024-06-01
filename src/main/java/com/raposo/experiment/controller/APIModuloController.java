package com.raposo.experiment.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raposo.experiment.model.Modulo;
import com.raposo.experiment.model.Resposta;
import com.raposo.experiment.service.IModuloService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class APIModuloController {
    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IModuloService moduloService;

    @GetMapping("modulo")
    @Transactional
    public ResponseEntity<Object> consultaModulo() {
        logger.info("Consultando Modulo");

        return ResponseEntity.status(HttpStatus.OK).body(moduloService.consultaModulos());
    }

    @GetMapping("modulo/{id}")
    @Transactional
    public ResponseEntity<Object> consultaPorId(@PathVariable Long id) {
        logger.info("Consultando Modulo por id");

        return ResponseEntity.status(HttpStatus.OK).body(moduloService.consultaPorId(id));
    }

    @GetMapping(value = "modulo", params = "dendro_id")
    @Transactional
    public ResponseEntity<Object> consultaPorDendro(@RequestParam(value = "dendro_id") String dendro_id) {
        logger.info("Consultando Modulo por Dendro");

        return ResponseEntity.status(HttpStatus.OK).body(moduloService.consultaPorDendro(dendro_id));
    }

    @PostMapping("modulo")
    @Transactional
    public ResponseEntity<Object> cadastrarModulo(@RequestBody Modulo modulo) {
        logger.info("Cadastrando Modulo");

        return ResponseEntity.status(HttpStatus.OK).body(moduloService.cadastrarModulo(modulo));
    }

    @PatchMapping("modulo/{id}")
    @Transactional
    public ResponseEntity<Object> atualizarModulo(@PathVariable Long id, @RequestBody Modulo modulo) {
        logger.info("Atualizando Modulo");

        return ResponseEntity.status(HttpStatus.OK).body(moduloService.atualizarModulo(modulo));
    }
    
    @DeleteMapping("modulo/{id}")
    @Transactional
    public ResponseEntity<Object> deletarModulo(@PathVariable Long id, HttpServletRequest req) {
        logger.info("Deletando Modulo");

        moduloService.deletarModulo(id);

         Resposta resposta = new Resposta();

        resposta.setMensagem("Modulo deletado com sucesso");
        resposta.setStatus(HttpStatus.OK);
        resposta.setCaminho(req.getRequestURI().toString());
        resposta.setMetodo(req.getMethod());

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}