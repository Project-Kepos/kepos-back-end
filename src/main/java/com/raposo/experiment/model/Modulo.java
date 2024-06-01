package com.raposo.experiment.model;

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
    private int humidity;
    private int humidityLevel;
    private String desc;

    @ManyToOne
    @JoinColumn(name = "dendro_id", referencedColumnName = "id")
    private Dendro dendro;

    // Constructors
    public Modulo() {
    }

    public Modulo(String name, int humidity, int humidityLevel, String desc, Dendro dendro) {
        this.id = id;
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHumidityLevel() {
        return humidityLevel;
    }

    public void setHumidityLevel(int humidityLevel) {
        this.humidityLevel = humidityLevel;
    }

    public Dendro getDendro() {
        return dendro;
    }

    public void setDendro(Dendro dendro) {
        this.dendro = dendro;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
