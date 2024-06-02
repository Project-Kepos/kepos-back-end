package com.raposo.experiment.service;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import com.raposo.experiment.config.error.ErroCustomizado;
import com.raposo.experiment.config.security.TokenService;
import com.raposo.experiment.dto.LoginDTO;
import com.raposo.experiment.dto.UsuarioDTO;
import com.raposo.experiment.model.IUsuarioRepository;
import com.raposo.experiment.model.Usuario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService implements IUsuarioService {
	@Autowired
	IUsuarioRepository usuarioRepository;

	/* Injetando classe para criptografar senha */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/* Injetando classe para realizar login e autenticação */
	@Autowired
	private AuthenticationManager manager;

	/* Injetando serviço de geração de token jwt para autenticação */
	@Autowired
	private TokenService tokenService;

	Logger logger = LogManager.getLogger(getClass());

	public Usuario listaUsuarioLogado(Long id) {
		logger.info("Listando usuario pelo token");
		var usuario = usuarioRepository.findById(id);

		if (usuario.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return usuario.get();
	}

	// TODO: Verificar necessidade deste método
	public List<Usuario> listaTodosUsuarios() {
		logger.info("Listando usuarios no sistema");
		return usuarioRepository.findAll();
	}

	public Usuario cadastrarUsuario(UsuarioDTO json) {
		logger.info("cadastrar usuario");

		if (usuarioRepository.existsByEmail(json.email())) {
			throw new ErroCustomizado("E-mail já cadastrado no sistema.");
		}

		var novoUsuario = criarModelUsuario(json);
		return usuarioRepository.save(novoUsuario);
	}

	public Usuario atualizarUsuario(UsuarioDTO json) {
		if (json.id() == null) {
			throw new ErroCustomizado("O id do usuário é obrigatório.");
		}

		if (!usuarioRepository.existsById(json.id())) {
			throw new EntityNotFoundException();
		}

		var usuario = criarModelUsuario(json);
		return usuarioRepository.save(usuario);
	}

	public void deletarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	public String realizarLogin(LoginDTO json) {
		try {
			var token = new UsernamePasswordAuthenticationToken(json.email(), json.senha());
			var authentication = manager.authenticate(token);

			return tokenService.gerarToken((Usuario) authentication.getPrincipal());
		} catch (AuthenticationException e) {
			throw new ErroCustomizado("E-mail ou senha inválidos.");
		}
	}

	private Usuario criarModelUsuario(UsuarioDTO json) {
		var usuario = new Usuario();

		usuario.setId(json.id());
		usuario.setNome(json.nome());
		usuario.setEmail(json.email());
		usuario.setSenha(passwordEncoder.encode(json.senha()));
		usuario.setDendros(json.dendros());

		return usuario;
	}
}
