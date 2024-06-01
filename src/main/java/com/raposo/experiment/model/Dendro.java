package com.raposo.experiment.model;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Dendro {
	
	@Id
	private Long id;

	private String name;

	private int position;

	private Double temperature;

	private Double moisture;

	@ManyToOne
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario user;

	@OneToMany(mappedBy = "dendro")
	private List<Modulo> modules;

	public Dendro() {
	}

	public Dendro(Long id, String name, int position, Double temperature, Double moisture, Usuario user,
			List<Modulo> modules) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.temperature = temperature;
		this.moisture = moisture;
		this.user = user;
		this.modules = modules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getMoisture() {
		return moisture;
	}

	public void setMoisture(Double moisture) {
		this.moisture = moisture;
	}

	public List<Modulo> getModules() {
		return modules;
	}

	public void setModules(List<Modulo> modules) {
		this.modules = modules;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dendro other = (Dendro) obj;
		return Objects.equals(id, other.id);
	}

}
