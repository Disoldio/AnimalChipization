package com.example.animalchipization.service;

import com.example.animalchipization.domain.*;
import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.SearchAnimalDTO;
import com.example.animalchipization.dto.VisitedLocationDTO;
import com.example.animalchipization.exception.NotFoundException;
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
        private final AnimalTypeRepository animalTypeRepository;
        private final VisitedLocationsRepository visitedLocationsRepository;
        private final AccountRepository accountRepository;
        private final CriteriaManager criteriaManager;
        private final EntityRepository entityRepository;
        private final  AnimalTypeService animalTypeService;

    public AnimalDTO getById(Long id){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        AnimalDTO dto = modelMapper.map(animal, AnimalDTO.class);

        var ids = animal.getVisitedLocations().stream()
                .map(VisitedLocation::getId)
                .toList();
        dto.setVisitedLocationsIds(ids);


        var types = animal.getAnimalTypes().stream()
                .map(AnimalType::getId)
                .toList();
        dto.setAnimalTypesIds(types);

        return dto;
    }

    public List<VisitedLocationDTO> allVisitedLocation(Long id){
        return visitedLocationsRepository.findAllByAnimal(id).stream()
                .map(entity -> new VisitedLocationDTO(entity.getId(), entity.getVisitTime(), entity.getLocation().getId()))
                .toList();

    }

    public VisitedLocationDTO addVisitedLocation(Long animalId, Long locationId){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException());
        List<Long> locIds = animal.getVisitedLocations().stream()
                .map(loc -> loc.getLocation().getId())
                .toList();

        if(animal.getChippingLocationId().getId().equals(locationId)){
            throw new DataIntegrityViolationException("he`s already her");
        }
        if(locIds.contains(locationId)){
            throw new DataIntegrityViolationException("this animal visited this location");
        }

        if(animal.getLifeStatus() == LifeStatus.DEAD){
            throw new DataIntegrityViolationException("he`s dead");
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

        if(dto.getAnimalTypesIds() == null || dto.getAnimalTypesIds().isEmpty()){
            throw new IllegalStateException();
        }
        List<AnimalType> types = animalTypeService.findAllByIds(dto.getAnimalTypesIds());


        modelMapper.map(dto, animal);

        Account account = accountRepository.findById(dto.getChipperId())
                .orElseThrow(() -> new NotFoundException());

        Location location = locationRepository.findById(dto.getChippingLocationId())
                        .orElseThrow(() -> new NotFoundException());

        animal.setChippingLocationId(location);
        animal.setChipper(account);
        animal.setAnimalTypes(types);

        animal = animalRepository.save(animal);
        AnimalDTO animalDTO = modelMapper.map(animal, AnimalDTO.class);

        List<Long> ids = animal.getAnimalTypes().stream()
                .map(type -> type.getId())
                .toList();

        animalDTO.setAnimalTypesIds(ids);
        animalDTO.setChippingLocationId(animal.getChippingLocationId().getId());
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
        CriteriaQuery<Animal> animalCriteriaQuery = criteriaManager.buildCriteria(Animal.class, map);

        List<Animal> list = entityRepository.list(animalCriteriaQuery, dto.getFrom(), dto.getSize());
        List<AnimalDTO> listDTO = list.stream().map(animal -> modelMapper.map(animal, AnimalDTO.class)).toList();

        return listDTO;
    }

    public AnimalDTO updateAnimal(Long id, AnimalDTO dto){
        Animal animal = animalRepository.getById(id);

        if(dto.getAnimalTypesIds() != null && !dto.getAnimalTypesIds().isEmpty()){
            List<AnimalType> types = animalTypeService.findAllByIds(dto.getAnimalTypesIds());
            animal.setAnimalTypes(types);
        }

        animal.setWeight(dto.getWeight());
        animal.setLength(dto.getLength());
        animal.setHeight(dto.getHeight());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(dto.getLifeStatus());
        animal.setChippingDateTime(dto.getChippingDateTime());
       // animal.setChipperId(animalDTO.getChipperId());
       // animal.setChippingLocationId(animalDTO.getChippingLocationId());
        animal.setDeathDateTime(dto.getDeathDateTime());

        //animal.setVisitedLocations(dto.getVisitedLocationsIds());

        Animal saveAnimal = animalRepository.save(animal);

        return modelMapper.map(saveAnimal, AnimalDTO.class);
    }

    public VisitedLocationDTO updateVisitedLocation(Long animalId, VisitedLocationDTO dto){
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NotFoundException::new);

        Location chippingLocation = animal.getChippingLocationId();
        Long chippingId = chippingLocation.getId();

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        VisitedLocation visitedLocation = visitedLocationsRepository.findById(dto.getId())
                .orElseThrow(NotFoundException::new);

        List<VisitedLocation> sortedVisitedLocations = visitedLocations.stream()
                .sorted((vl1, vl2) -> vl2.getId().compareTo(vl1.getId()))
                .toList();
        int i = sortedVisitedLocations.indexOf(visitedLocation);
        boolean i1 = sortedVisitedLocations.get(i+1).getLocation().equals(dto.getLocationPointId());
        boolean i2 = sortedVisitedLocations.get(i-1).getLocation().equals(dto.getLocationPointId());

        if(i == 0){
            if(i1){
                throw new DataIntegrityViolationException("i = 0");
            }
        }
        if(i == sortedVisitedLocations.size()){
            if(i2){
                throw new DataIntegrityViolationException("i = max size");
            }
        }
        if(i != 0 || i != sortedVisitedLocations.size()){
            if(i1){
                throw new DataIntegrityViolationException("i = 0");
            }
            if(i2){
                throw new DataIntegrityViolationException("i = max size");
            }
        }

        if(sortedVisitedLocations.get(i+1).getLocation().getId().equals(dto.getLocationPointId()) || sortedVisitedLocations.get(i-1).getLocation().getId().equals(dto.getLocationPointId())){
            throw new DataIntegrityViolationException("2");
        }








        if(!visitedLocations.contains(visitedLocation)){
            throw new NotFoundException();
        }

        if(chippingId.equals(dto.getLocationPointId())){
            VisitedLocation firstVisitedLocation = visitedLocations.stream()
                    .sorted((vl1, vl2) -> vl2.getId().compareTo(vl1.getId()))
                    .findFirst()
                    .orElseThrow(NotFoundException::new);

            if(firstVisitedLocation.getId().equals(dto.getId())){
                throw new DataIntegrityViolationException("");
            }
        }

        Location location = locationRepository.findById(dto.getLocationPointId())
                .orElseThrow(NotFoundException::new);

        visitedLocation.setLocation(location);

        VisitedLocation saveLocation = visitedLocationsRepository.save(visitedLocation);

        return new VisitedLocationDTO(saveLocation.getId(), saveLocation.getVisitTime(), saveLocation.getLocation().getId());
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
