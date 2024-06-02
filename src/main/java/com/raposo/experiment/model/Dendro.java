package com.raposo.experiment.model;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raposo.experiment.dto.DendroDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Dendro {

	@Id
	private String id;

	private String name;

	private Double temperature;

	private Double humidity;

	private Integer luminosity;

	@JsonIgnore
	@OneToMany(mappedBy = "dendro")
	private List<Modulo> modules;

	@JsonIgnore
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario user;

	// Constructors
	public Dendro() {
	}

	public Dendro(String id, String name, Double temperature, Double humidity, Integer luminosity) {
		this.id = id;
		this.name = name;
		this.temperature = temperature;
		this.humidity = humidity;
		this.luminosity = luminosity;
	}

	public Dendro(String id, String name, Double temperature, Double humidity, Integer luminosity, List<Modulo> modules,
			Usuario user) {
		this.id = id;
		this.name = name;
		this.temperature = temperature;
		this.humidity = humidity;
		this.luminosity = luminosity;
		this.modules = modules;
		this.user = user;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Integer getLuminosity() {
		return luminosity;
	}

	public void setLuminosity(Integer luminosity) {
		this.luminosity = luminosity;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public List<Modulo> getModules() {
		return modules;
	}

	public void setModules(List<Modulo> modules) {
		this.modules = modules;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
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

	public void atualizarDendro(DendroDTO json) {
		if (json.name() != null) {
			name = json.name();
		}

		if (json.temperature() != null) {
			temperature = json.temperature();
		}

		if (json.luminosity() != null) {
			luminosity = json.luminosity();
		}

		if (json.humidity() != null) {
			humidity = json.humidity();
		}
	}
}
