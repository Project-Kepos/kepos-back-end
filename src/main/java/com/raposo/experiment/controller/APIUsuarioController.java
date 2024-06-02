package com.raposo.experiment.controller;

import com.raposo.experiment.config.security.DadosTokenJWT;
import com.raposo.experiment.dto.LoginDTO;
import com.raposo.experiment.dto.UsuarioDTO;
import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.Usuario;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.raposo.experiment.service.IDendroService;
import com.raposo.experiment.service.IUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuario")
public class APIUsuarioController {
	@Autowired
	IDendroService dendroService;

	@Autowired
	IUsuarioService usuarioService;
	private static final Logger logger = LoggerFactory.getLogger(APIUsuarioController.class);
	@GetMapping
	public UsuarioDTO listaUsuarioLogado(HttpServletRequest request) {
		var idUsuario = request.getAttribute("userId");
		var usuario = usuarioService.listaUsuarioLogado((Long) idUsuario);

		return new UsuarioDTO(usuario);
	}

	// TODO: Verificar necessidade deste método
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

	@PostMapping("/dendro/adicionar")
    @Transactional
    public String adicionaDendroUsuario(@RequestBody String dendroId, HttpServletRequest request) {
        logger.info("Recebido pedido para adicionar Dendro com ID: {}", dendroId);
        
        Dendro dendro = dendroService.consultaDendroPorId(dendroId);
        if (dendro == null) {
            logger.error("Dendro não encontrado para ID: {}", dendroId);
            return "Dendro não encontrado";
        }
        logger.info("Dendro encontrado: {}", dendro.getName());

        Long idUsuario = (Long) request.getAttribute("userId");
        logger.info("ID do Usuário logado: {}", idUsuario);

        Usuario usuario = usuarioService.listaUsuarioLogado(idUsuario);
        if (usuario == null) {
            logger.error("Usuário não encontrado para ID: {}", idUsuario);
            return "Usuario não encontrada";
        }
        logger.info("Usuário encontrado: {}", usuario);

        List<Dendro> dendros = usuario.getDendros();
        dendros.add(dendro);
        usuario.setDendros(dendros);
        usuarioService.atualizarUsuario(new UsuarioDTO(usuario));

        logger.info("Dendro adicionado com sucesso ao usuário: {}", usuario);

        return "Dendro adicionado com sucesso";
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
