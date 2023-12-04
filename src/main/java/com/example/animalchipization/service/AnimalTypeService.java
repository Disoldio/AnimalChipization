package com.example.animalchipization.service;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.domain.Type;
import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.ChangeTypeDTO;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.repository.AnimalRepository;
import com.example.animalchipization.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AnimalTypeService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private Mapper<Animal, AnimalDTO> mapper;

    public AnimalDTO addTypeToAnimal(Long animalId, Long typeId){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NotFoundException::new);
        Type type = typeRepository.findById(typeId)
                .orElseThrow(NotFoundException::new);
        List<Type> types = animal.getTypes();
        if(types.contains(type)){
            throw new AlreadyExistException();
        }
        types.add(type);
        Animal saveAnimal = animalRepository.save(animal);

        return mapper.toDto(saveAnimal);
    }

    public AnimalDTO updateTypeToAnimal(Long animalId, ChangeTypeDTO changeTypeDTO){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NotFoundException::new);
        Type oldType = typeRepository.findById(changeTypeDTO.getOldTypeId())
                .orElseThrow(NotFoundException::new);
        Type newType = typeRepository.findById(changeTypeDTO.getNewTypeId())
                .orElseThrow(NotFoundException::new);
        List<Type> types = animal.getTypes();
        if(!animal.getTypes().contains(oldType)){
            throw new NotFoundException();
        }
        if(animal.getTypes().contains(newType)){
            throw new AlreadyExistException();
        }

        int i = types.indexOf(oldType);
        types.set(i, newType);
        Animal saveAnimal = animalRepository.save(animal);

        return mapper.toDto(saveAnimal);
    }

    public AnimalDTO deleteTypeToAnimal(Long animalId, Long typeId){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NotFoundException::new);
        Type type = typeRepository.findById(typeId)
                .orElseThrow(NotFoundException::new);
        List<Type> types = animal.getTypes();
        if(!types.contains(type)){
            throw new NotFoundException();
        }
        if(types.size() == 1){
            throw new DataIntegrityViolationException("he is one");
        }

        types.remove(type);
        Animal saveAnimal = animalRepository.save(animal);

        return mapper.toDto(saveAnimal);
    }
}
