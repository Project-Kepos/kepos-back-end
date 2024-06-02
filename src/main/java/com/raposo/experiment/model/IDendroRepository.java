package com.raposo.experiment.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDendroRepository extends JpaRepository<Dendro, String> {

	public Optional<Dendro> findById(String id);

	public List<Dendro> findByName(String name);

	public List<Dendro> findAllByUser_Id(Long userId);

}
