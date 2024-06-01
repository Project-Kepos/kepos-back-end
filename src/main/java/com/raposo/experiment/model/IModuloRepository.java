package com.raposo.experiment.model;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuloRepository extends JpaRepository<Modulo, Long>{
    public Optional<Modulo> findById(Long id);
    
    public List<Modulo> findByDendroId(Dendro dendro);
}