package com.example.animalchipization.mapper;

public interface Mapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);
}
