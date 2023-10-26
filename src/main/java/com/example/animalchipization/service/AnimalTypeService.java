package com.example.animalchipization.service;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.dto.AnimalTypeDTO;
import com.example.animalchipization.exception.InvalidIdException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AnimalTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    public AnimalTypeDTO createType(String type){
        AnimalType animalType = new AnimalType();
        animalType.setType(type);
        animalType = animalTypeRepository.save(animalType);
        AnimalTypeDTO animalTypeDTO = new AnimalTypeDTO(animalType.getId(), animalType.getType());

        return animalTypeDTO;
    }
    public List<AnimalTypeDTO> getAllType(){
        return null;
    }
    public AnimalTypeDTO getById(Long id){
        return animalTypeRepository.findById(id)
                .map(type -> modelMapper.map(type, AnimalTypeDTO.class))
                .orElseThrow(NotFoundException::new);
    }




}