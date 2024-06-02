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

import com.raposo.experiment.dto.DendroDTO;
import com.raposo.experiment.model.Resposta;
import com.raposo.experiment.service.IDendroService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/dendro")
public class APIDendroController {

	@Autowired
	IDendroService dendroService;

	// TODO: Revisar necessidade deste método
	@GetMapping
	public List<DendroDTO> consultaTodasDendros() {
		var dendros = dendroService.consultaTodasDendros();

		return dendros.stream().map(DendroDTO::new).toList();
	}

	// TODO: Revisar necessidade deste método
	@GetMapping(params = "nome")
	public List<DendroDTO> consultaPorNome(@RequestParam String nome) {
		var dendros = dendroService.consultaDendrosPorNome(nome);

		return dendros.stream().map(DendroDTO::new).toList();
	}

	@GetMapping("/usuario")
	public List<DendroDTO> consultaTodasDendrosPorUsuario(HttpServletRequest request) {
		var userId = request.getAttribute("userId");
		var dendros = dendroService.consultaDendrosPorUsuario((Long) userId);

		return dendros.stream().map(DendroDTO::new).toList();
	}

	@GetMapping("/{id}")
	public DendroDTO consultaPorId(@PathVariable String id) {
		var dendro = dendroService.consultaDendroPorId(id);

		return new DendroDTO(dendro);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DendroDTO> cadastrarDendro(@RequestBody DendroDTO json, HttpServletRequest request,
			UriComponentsBuilder uriBuilder) {
		var userId = request.getAttribute("userId");
		var dendro = dendroService.cadastrarDendro(json, (Long) userId);
		var uri = uriBuilder.path("/api/v1/dendro/{id}").buildAndExpand(dendro.getId()).toUri();

		return ResponseEntity.created(uri).body(new DendroDTO(dendro));
	}

	@PatchMapping("/{id}")
	@Transactional
	public DendroDTO atualizarDendro(@PathVariable String id, @RequestBody DendroDTO json) {
		var dendro = dendroService.atualizarDendro(id, json);

		return new DendroDTO(dendro);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> deletarDendro(@PathVariable String id, HttpServletRequest request) {
		dendroService.deletarDendro(id);

		Resposta resposta = new Resposta();

		resposta.setMensagem("Dendro deletado com sucesso");
		resposta.setStatus(HttpStatus.OK);
		resposta.setCaminho(request.getRequestURI().toString());
		resposta.setMetodo(request.getMethod());

		return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}

}