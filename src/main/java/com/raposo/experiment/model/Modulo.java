package com.raposo.experiment.model;

import java.util.Objects;

import com.raposo.experiment.dto.ModuloDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Modulo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String desc;

	private Integer humidity;

	private Integer humidityLevel;

	@ManyToOne
	@JoinColumn(name = "dendro_id", referencedColumnName = "id")
	private Dendro dendro;

	// Constructors
	public Modulo() {
	}

	public Modulo(String name, String desc, Integer humidity, Integer humidityLevel, Dendro dendro) {
		this.name = name;
		this.humidity = humidity;
		this.humidityLevel = humidityLevel;
		this.dendro = dendro;
	}

	// Getters and Setters
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Integer getHumidityLevel() {
		return humidityLevel;
	}

	public void setHumidityLevel(Integer humidityLevel) {
		this.humidityLevel = humidityLevel;
	}

	public Dendro getDendro() {
		return dendro;
	}

	public void setDendro(Dendro dendro) {
		this.dendro = dendro;
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
		Modulo other = (Modulo) obj;
		return Objects.equals(id, other.id);
	}

	public void atualizarModulo(ModuloDTO json) {
		if (json.name() != null) {
			name = json.name();
		}

		if (json.desc() != null) {
			desc = json.desc();
		}

		if (json.humidity() != null) {
			humidity = json.humidity();
		}

		if (json.humidityLevel() != null) {
			humidityLevel = json.humidityLevel();
		}
	}

}
