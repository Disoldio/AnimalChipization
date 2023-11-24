package com.example.animalchipization.service;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.AnimalTypeDTO;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.InaccessibleEntityException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AnimalTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    public AnimalTypeDTO createType(AnimalTypeDTO dto){
        AnimalType animalType = new AnimalType();

        animalTypeRepository.findByType(dto.getType())
                .ifPresent(type -> {throw new AlreadyExistException();});

        modelMapper.map(dto, animalType);

        animalType = animalTypeRepository.save(animalType);
        AnimalTypeDTO animalTypeDTO = modelMapper.map(animalType, AnimalTypeDTO.class);

        return animalTypeDTO;
    }

    public AnimalTypeDTO getById(Long id){
        return animalTypeRepository.findById(id)
                .map(type -> modelMapper.map(type, AnimalTypeDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public List<AnimalType> findAllByIds(List<Long> ids){
        return ids.stream()
                .map(id -> animalTypeRepository.findById(id))
                .map(opt -> opt.orElseThrow(NotFoundException::new))
                .toList();
    }

    public AnimalTypeDTO updateAnimalType(Long id, AnimalTypeDTO animalTypeDTO){
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        animalTypeRepository.findByType(animalTypeDTO.getType())
                .ifPresent(type -> {throw new AlreadyExistException();});

        animalType.setType(animalTypeDTO.getType());

        AnimalType saveAnimalType = animalTypeRepository.save(animalType);
        AnimalTypeDTO dto = modelMapper.map(saveAnimalType, AnimalTypeDTO.class);
        return dto;
    }

    public void deleteAnimalType(Long id){
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        animalTypeRepository.delete(animalType);
    }



}
