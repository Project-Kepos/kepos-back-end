package com.raposo.experiment.service;
import java.util.Optional;
import java.util.List;


import com.raposo.experiment.model.Usuario;

public interface IUsuarioService {
    public Optional<Usuario> cadastrarUsuario(Usuario usuario);
    public List<Usuario> listarUsuarios();
}