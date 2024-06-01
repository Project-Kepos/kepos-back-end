package com.raposo.experiment.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuloRepository extends JpaRepository<Modulo, Long>{

	public List<Modulo> findByDendroId(Long idDendro);
	
}
