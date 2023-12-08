package com.example.animalchipization.domain;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "life_status")
    @Enumerated(value = EnumType.STRING)
    private LifeStatus lifeStatus = LifeStatus.ALIVE;
    @Column(name = "chipping_date_time")
    private LocalDateTime chippingDateTime;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account chipper;
    @ManyToOne
    @JoinColumn(name = "chipping_location")
    private Location chippingLocation;
    @Column(name = "death_date_time")
    private LocalDateTime deathDateTime;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "animal_types_rel",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<Type> types;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "animal")
    private List<VisitedLocation> visitedLocations = new ArrayList<>();
}
