package com.example.animalchipization.service;

import com.example.animalchipization.domain.*;
import com.example.animalchipization.dto.*;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.InaccessibleEntityException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AnimalRepository;
import com.example.animalchipization.repository.EntityRepository;
import com.example.animalchipization.util.CriteriaManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimalService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CriteriaManager criteriaManager;
    @Autowired
    private EntityRepository entityRepository;

    public AnimalDTO getById(Long id){
        return animalRepository.findById(id)
                .map(type -> modelMapper.map(type, AnimalDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public AnimalDTO createAnimal(AnimalDTO dto){
        Animal animal = new Animal();

        modelMapper.map(dto, animal);

        animal = animalRepository.save(animal);
        AnimalDTO animalDTO = modelMapper.map(animal, AnimalDTO.class);

        return animalDTO;
    }

    public List<AnimalDTO> search(SearchAnimalDTO dto){
        Map<String, Object> map = new HashMap<>();
        map.put("startDateTime", dto.getStartDateTime());
        map.put("endDateTime", dto.getEndDateTime());
        map.put("chipperId", dto.getChipperId());
        map.put("chippingLocationId", dto.getChippingLocationId());
        map.put("gender", dto.getGender());
        map.put("lifeStatus", dto.getLifeStatus());
        CriteriaQuery<Animal> animalCriteriaQuery = criteriaManager.buildCriteria(Animal.class, map);

        List<Animal> list = entityRepository.list(animalCriteriaQuery, dto.getFrom(), dto.getSize());
        List<AnimalDTO> listDTO = list.stream().map(animal -> modelMapper.map(animal, AnimalDTO.class)).toList();

        return listDTO;
    }

    public AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO){
        Animal animal = animalRepository.getById(id);

        animal.setWeight(animalDTO.getWeight());
        animal.setLength(animalDTO.getLength());
        animal.setHeight(animalDTO.getHeight());
        animal.setGender(animalDTO.getGender());
        animal.setLifeStatus(animalDTO.getLifeStatus());
        animal.setChippingDateTime(animalDTO.getChippingDateTime());
        animal.setChipperId(animalDTO.getChipperId());
        animal.setChippingLocationId(animalDTO.getChippingLocationId());
        animal.setDeathDateTime(animalDTO.getDeathDateTime());
//        animal.setAnimalTypes(animalDTO.getAnimalTypes());
//        animal.setVisitedLocations(animalDTO.getVisitedLocations());

        Animal saveAnimal = animalRepository.save(animal);
        AnimalDTO dto = modelMapper.map(saveAnimal, AnimalDTO.class);
        return dto;
    }

    public void deleteAnimal(Long id){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new InaccessibleEntityException());

        animalRepository.deleteById(id);
    }
}
