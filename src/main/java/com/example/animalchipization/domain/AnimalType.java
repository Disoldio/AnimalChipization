package com.example.animalchipization.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_types")
public class AnimalType {
    @Id
    private Long id;
    @Column(name = "type")
    private String type;

}
