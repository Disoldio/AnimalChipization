package com.example.animalchipization.service;

import com.example.animalchipization.domain.*;
import com.example.animalchipization.dto.*;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.InaccessibleEntityException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.*;
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
    private LocationRepository locationRepository;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private VisitedLocationsRepository visitedLocationsRepository;
    @Autowired
    private CriteriaManager criteriaManager;
    @Autowired
    private EntityRepository entityRepository;

    public AnimalDTO getById(Long id){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        AnimalDTO dto = modelMapper.map(animal, AnimalDTO.class);

        var ids = animal.getVisitedLocations().stream()
                .map(VisitedLocation::getId)
                .toList();
        dto.setVisitedLocationsIsd(ids);


        var types = animal.getAnimalTypes().stream()
                .map(AnimalType::getId)
                .toList();
        dto.setAnimalTypesIds(types);

        return dto;
    }

    public VisitedLocationDTO addVisitedLocation(Long animalId, Long locationId){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException());
        if(animal.getLifeStatus() == LifeStatus.DEAD){
            throw new RuntimeException();
        }
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException());

        VisitedLocation visitLoc = new VisitedLocation();

        visitLoc.setAnimal(animal);
        visitLoc.setLocation(location);
        visitLoc.setVisitTime(LocalDateTime.now());
        visitLoc = visitedLocationsRepository.save(visitLoc);

        VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(visitLoc.getId(), visitLoc.getVisitTime(), visitLoc.getLocation().getId());

        return visitedLocationDTO;
    }

    public AnimalDTO createAnimal(AnimalDTO dto){
        Animal animal = new Animal();
        List<AnimalType> types = animalTypeRepository.findAllByIdIn(dto.getAnimalTypesIds());

        if(types.size() != dto.getAnimalTypesIds().size()){
            throw new NotFoundException();
        }

        modelMapper.map(dto, animal);
        animal.setAnimalTypes(types);

        animal = animalRepository.save(animal);
        AnimalDTO animalDTO = modelMapper.map(animal, AnimalDTO.class);
        List<Long> ids = animal.getAnimalTypes().stream()
                .map(type -> type.getId())
                .toList();
        animalDTO.setAnimalTypesIds(ids);

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
