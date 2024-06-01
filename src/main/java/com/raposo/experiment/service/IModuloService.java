package com.raposo.experiment.service;

import java.util.List;
import java.util.Optional;

import com.raposo.experiment.model.Dendro;
import com.raposo.experiment.model.Modulo;

public interface IModuloService {
    public List<Modulo> consultaModulos();
    public Optional<Modulo> consultaPorId(Long id);

    public Optional<Modulo> cadastrarModulo(Modulo modulo);

    public Optional<Modulo> atualizarModulo(Modulo modulo);

    public List<Modulo> consultaPorDendro(String dendro_id);

    public void deletarModulo(Long id);
}
