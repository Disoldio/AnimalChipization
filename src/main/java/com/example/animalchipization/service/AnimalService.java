package com.example.animalchipization.service;

import com.example.animalchipization.domain.*;
import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.SearchAnimalDTO;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.mapper.AnimalMapper;
import com.example.animalchipization.repository.*;
import com.example.animalchipization.util.CriteriaManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AnimalService {
    private final ModelMapper modelMapper;
    private final AnimalRepository animalRepository;
    private final LocationRepository locationRepository;
    private final TypeRepository typeRepository;
    private final AccountRepository accountRepository;
    private final CriteriaManager criteriaManager;
    private final EntityRepository entityRepository;
    private final TypeService typeService;
    private final AnimalMapper animalMapper;

    public AnimalDTO getById(Long id){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        AnimalDTO dto = modelMapper.map(animal, AnimalDTO.class);
        var ids = animal.getVisitedLocations().stream()
                .map(VisitedLocation::getId)
                .toList();
        dto.setVisitedLocationsIds(ids);
        var types = animal.getTypes().stream()
                .map(Type::getId)
                .toList();
        dto.setAnimalTypesIds(types);

        return dto;
    }

    public AnimalDTO createAnimal(AnimalDTO dto){
        Animal animal = new Animal();
        if(dto.getAnimalTypesIds() == null || dto.getAnimalTypesIds().isEmpty()){
            throw new IllegalStateException();
        }
        List<Type> types = typeService.findAllByIds(dto.getAnimalTypesIds());

        modelMapper.map(dto, animal);

        Account account = accountRepository.findById(dto.getChipperId())
                .orElseThrow(() -> new NotFoundException());
        Location location = locationRepository.findById(dto.getChippingLocationId())
                        .orElseThrow(() -> new NotFoundException());

        animal.setChippingLocation(location);
        animal.setChipper(account);
        animal.setTypes(types);
        animal = animalRepository.save(animal);
        AnimalDTO animalDTO = modelMapper.map(animal, AnimalDTO.class);
        List<Long> ids = animal.getTypes().stream()
                .map(type -> type.getId())
                .toList();

        animalDTO.setAnimalTypesIds(ids);
        animalDTO.setChippingLocationId(animal.getChippingLocation().getId());
        animalDTO.setChipperId(account.getId());

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
        CriteriaQuery<Animal> animalCriteriaQuery = criteriaManager.buildAnimalCriteria(
                dto.getChipperId(),
                dto.getChippingLocationId(),
                dto.getGender(),
                dto.getLifeStatus(),
                dto.getStartDateTime(),
                dto.getEndDateTime());

        List<Animal> list = entityRepository.list(animalCriteriaQuery, dto.getFrom(), dto.getSize());

        return list.stream().map(animalMapper::toDto).toList();
    }

    public AnimalDTO updateAnimal(Long id, AnimalDTO dto){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        Account chipperAccount = accountRepository.findById(dto.getChipperId())
                .orElseThrow(NotFoundException::new);
        Location chippingLocation = locationRepository.findById(dto.getChippingLocationId())
                .orElseThrow(NotFoundException::new);
        List<VisitedLocation> sortedVisitedLocations = animal.getVisitedLocations().stream()
                .sorted(Comparator.comparing(VisitedLocation::getId))
                .toList();
        if(!sortedVisitedLocations.isEmpty()){
            VisitedLocation visitedLocation = sortedVisitedLocations.get(0);
            if(visitedLocation != null && visitedLocation.getLocation().equals(chippingLocation)){
                throw new DataIntegrityViolationException("Новый Chipping равен первой VisitLocation");
            }
        }
        if(dto.getAnimalTypesIds() != null && !dto.getAnimalTypesIds().isEmpty()){
            List<Type> types = typeService.findAllByIds(dto.getAnimalTypesIds());
            animal.setTypes(types);
        }

        animal.setWeight(dto.getWeight());
        animal.setLength(dto.getLength());
        animal.setHeight(dto.getHeight());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(dto.getLifeStatus());
        animal.setChippingDateTime(dto.getChippingDateTime());
        animal.setChipper(chipperAccount);
        animal.setChippingLocation(chippingLocation);
        if(dto.getLifeStatus().equals(LifeStatus.DEAD) && animal.getDeathDateTime() == null){
            animal.setDeathDateTime(LocalDateTime.now());
        }

        Animal saveAnimal = animalRepository.save(animal);
        AnimalDTO saveDto = modelMapper.map(saveAnimal, AnimalDTO.class);
        saveDto.setAnimalTypesIds(animal.getTypes().stream()
                .map(type -> type.getId())
                .toList());
        return saveDto;
    }

    public void deleteAnimal(Long id){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if(!animal.getVisitedLocations().isEmpty()){
            throw new IllegalStateException();
        }

        animalRepository.deleteById(id);
    }
}
