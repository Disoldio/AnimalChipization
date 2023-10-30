package com.example.animalchipization.service;

import com.example.animalchipization.domain.Account;
import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.SearchAccountDTO;
import com.example.animalchipization.dto.SearchAnimalDTO;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.AnimalRepository;
import com.example.animalchipization.repository.EntityRepository;
import com.example.animalchipization.util.CriteriaManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
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
}
