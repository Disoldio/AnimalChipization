package com.example.animalchipization.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "animal_types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type")
    private String type;

}
