package com.raposo.experiment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.raposo.experiment.dto.ModuloDTO;
import com.raposo.experiment.model.Resposta;
import com.raposo.experiment.service.IModuloService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/modulo")
public class APIModuloController {

	@Autowired
	IModuloService moduloService;

	// TODO: Revisar necessidade deste método
	@GetMapping
	public List<ModuloDTO> consultaTodosModulos() {
		var modulos = moduloService.consultaTodosModulos();

		return modulos.stream().map(ModuloDTO::new).toList();
	}

	@GetMapping(params = "dendro_id")
	public List<ModuloDTO> consultaPorDendro(@RequestParam String dendro_id) {
		var modulos = moduloService.consultaPorDendro(dendro_id);

		return modulos.stream().map(ModuloDTO::new).toList();
	}

	@GetMapping("/{id}")
	public ModuloDTO consultaPorId(@PathVariable Long id) {
		var modulo = moduloService.consultaPorId(id);

		return new ModuloDTO(modulo);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ModuloDTO> cadastrarModulo(@RequestBody @Valid ModuloDTO json,
			UriComponentsBuilder uriBuilder) {
		var modulo = moduloService.cadastrarModulo(json);
		var uri = uriBuilder.path("/api/v1/modulo/{id}").buildAndExpand(modulo.getId()).toUri();

		return ResponseEntity.created(uri).body(new ModuloDTO(modulo));
	}

	@PatchMapping("/{id}")
	@Transactional
	public ModuloDTO atualizarModulo(@PathVariable Long id, @RequestBody @Valid ModuloDTO json) {
		var modulo = moduloService.atualizarModulo(id, json);

		return new ModuloDTO(modulo);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> deletarModulo(@PathVariable Long id, HttpServletRequest request) {
		moduloService.deletarModulo(id);

		Resposta resposta = new Resposta();

		resposta.setMensagem("Modulo deletado com sucesso");
		resposta.setStatus(HttpStatus.OK);
		resposta.setCaminho(request.getRequestURI().toString());
		resposta.setMetodo(request.getMethod());

		return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}

}
