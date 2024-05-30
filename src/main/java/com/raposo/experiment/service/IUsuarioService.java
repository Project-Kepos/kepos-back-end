package com.raposo.experiment.service;
import java.util.List;

import com.raposo.experiment.dto.LoginDTO;
import com.raposo.experiment.dto.UsuarioDTO;
import com.raposo.experiment.model.Usuario;

public interface IUsuarioService {
	public Usuario listaUsuarioLogado(Long id);
	public List<Usuario> listaTodosUsuarios();
    public Usuario cadastrarUsuario(UsuarioDTO json);
    public String realizarLogin(LoginDTO json);
    public Usuario atualizarUsuario(UsuarioDTO json);
    public void deletarUsuario(Long id);
}
