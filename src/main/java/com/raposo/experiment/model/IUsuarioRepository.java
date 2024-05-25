package com.raposo.experiment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
    UserDetails findByEmail(String email);
    boolean existsByEmail(String email);
}
