package com.example.animalchipization.controller;

import com.example.animalchipization.dto.AccountDTO;
import com.example.animalchipization.dto.AnimalDTO;
import com.example.animalchipization.dto.SearchAccountDTO;
import com.example.animalchipization.dto.SearchAnimalDTO;
import com.example.animalchipization.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/{animalId}")
    public AnimalDTO getById(@PathVariable @Min(1) Long animalId){
        return animalService.getById(animalId);
    }

    @GetMapping("/search")
    public List<AnimalDTO> search(@Valid SearchAnimalDTO searchAnimalDTO){
        return animalService.search(searchAnimalDTO);
    }

}
