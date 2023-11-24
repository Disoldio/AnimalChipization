package com.example.animalchipization.domain;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "length")
    private Float length;
    @Column(name = "height")
    private Float height;
    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column(name = "lifeStatus")
    @Enumerated(value = EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    @Column(name = "chippingDateTime")
    private LocalDateTime chippingDateTime;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account chipper;
    @ManyToOne
    @JoinColumn(name = "chippingLocationId")
    private Location chippingLocationId;
    @Column(name = "deathDateTime")
    private LocalDateTime deathDateTime;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "animal_types_rel",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "types_id"))
    private List<AnimalType> animalTypes;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "animal")
    private ArrayList<VisitedLocation> visitedLocations = new ArrayList<>();
}
