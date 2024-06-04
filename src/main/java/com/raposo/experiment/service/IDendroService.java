package com.raposo.experiment.service;

import java.util.List;

import com.raposo.experiment.dto.DendroDTO;
import com.raposo.experiment.model.Dendro;

public interface IDendroService {

    public List<Dendro> consultaTodasDendros();

    public List<Dendro> consultaDendrosPorNome(String nome);

    public List<Dendro> consultaDendrosPorUsuario(Long userId);

    public Dendro consultaDendroPorId(String id);

    public Dendro cadastrarDendro(DendroDTO json);

    public Dendro adicionarUsuario(Long userId, DendroDTO json);

    public Dendro atualizarDendro(String id, DendroDTO json);

    public void deletarDendro(String id);

    public Dendro removerUsuarioDendro(DendroDTO json);

}
