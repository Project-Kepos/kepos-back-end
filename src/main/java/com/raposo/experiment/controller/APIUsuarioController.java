package com.raposo.experiment.controller;

import com.raposo.experiment.config.security.DadosTokenJWT;
import com.raposo.experiment.dto.LoginDTO;
import com.raposo.experiment.dto.UsuarioDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.raposo.experiment.service.IUsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuario")
public class APIUsuarioController {

	@Autowired
	IUsuarioService usuarioService;

	@GetMapping
	public UsuarioDTO listaUsuarioLogado(HttpServletRequest request) {
		var idUsuario = request.getAttribute("userId");
		var usuario = usuarioService.listaUsuarioLogado((Long) idUsuario);

		return new UsuarioDTO(usuario);
	}

	@GetMapping("/todos")
	public List<UsuarioDTO> listaTodosUsuarios() {
		var usuarios = usuarioService.listaTodosUsuarios();

		return usuarios.stream().map(UsuarioDTO::new).toList();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO json,
			UriComponentsBuilder uriBuilder) {
		var usuario = usuarioService.cadastrarUsuario(json);
		var uri = uriBuilder.path("/api/v1/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}

	@PostMapping("/login")
	@Transactional
	public DadosTokenJWT efetuarLogin(@RequestBody @Valid LoginDTO json) {
		var token = usuarioService.realizarLogin(json);

		return new DadosTokenJWT(token);
	}

	@PutMapping
	@Transactional
	public UsuarioDTO atualizarUsuario(@RequestBody @Valid UsuarioDTO json) {
		var usuario = usuarioService.atualizarUsuario(json);

		return new UsuarioDTO(usuario);
	}

	@DeleteMapping
	@Transactional
	public void deletarUsuario(HttpServletRequest request) {
		var idUsuario = request.getAttribute("userId");
		usuarioService.deletarUsuario((Long) idUsuario);
	}

}
