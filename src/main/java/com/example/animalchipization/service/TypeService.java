package com.example.animalchipization.service;

import com.example.animalchipization.domain.Type;
import com.example.animalchipization.dto.TypeDTO;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TypeRepository typeRepository;

    public TypeDTO createType(TypeDTO dto){
        Type animalType = new Type();

        typeRepository.findByType(dto.getType())
                .ifPresent(type -> {throw new AlreadyExistException();});

        modelMapper.map(dto, animalType);

        animalType = typeRepository.save(animalType);
        TypeDTO typeDTO = modelMapper.map(animalType, TypeDTO.class);

        return typeDTO;
    }

    public TypeDTO getById(Long id){
        return typeRepository.findById(id)
                .map(type -> modelMapper.map(type, TypeDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public List<Type> findAllByIds(List<Long> ids){
        return ids.stream()
                .map(id -> typeRepository.findById(id))
                .map(opt -> opt.orElseThrow(NotFoundException::new))
                .toList();
    }

    public TypeDTO updateAnimalType(Long id, TypeDTO typeDTO){
        Type animalType = typeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        typeRepository.findByType(typeDTO.getType())
                .ifPresent(type -> {throw new AlreadyExistException();});

        animalType.setType(typeDTO.getType());

        Type saveType = typeRepository.save(animalType);
        TypeDTO dto = modelMapper.map(saveType, TypeDTO.class);
        return dto;
    }

    public void deleteAnimalType(Long id){
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        typeRepository.delete(type);
    }



}
