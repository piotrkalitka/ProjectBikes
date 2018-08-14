package com.piotrkalitka.ProjectBikes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.sql.rowset.serial.SerialArray;
import javax.validation.constraints.NotBlank;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "stations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = ALL, mappedBy = "station")
    private Set<Stand> stands = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "station")
    private Set<Bike> bikes = new HashSet<>();

    public Station() {
    }

    public Station(@NotBlank String name) {
        this.name = name;
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

    public Set<Stand> getStands() {
        return stands;
    }

    public void setStands(Set<Stand> stands) {
        this.stands = stands;
    }

    public Set<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(Set<Bike> bikes) {
        this.bikes = bikes;
    }
}