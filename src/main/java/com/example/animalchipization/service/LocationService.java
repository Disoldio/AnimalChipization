package com.example.animalchipization.service;

import com.example.animalchipization.domain.AnimalType;
import com.example.animalchipization.domain.Location;
import com.example.animalchipization.dto.AnimalTypeDTO;
import com.example.animalchipization.dto.LocationDTO;
import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.NotFoundException;
import com.example.animalchipization.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocationRepository locationsRepository;

    public LocationDTO createLocation(LocationDTO dto){
        Location location = new Location();

        locationsRepository.findByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude())
                .ifPresent(loc -> {throw new AlreadyExistException();});

        modelMapper.map(dto, location);

        location = locationsRepository.save(location);
        LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);

        return locationDTO;
    }

    public LocationDTO getById(Long id){
        return locationsRepository.findById(id)
                .map(type -> modelMapper.map(type, LocationDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public LocationDTO updatePoint(Long id, LocationDTO locationDTO){
        Location location = locationsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        locationsRepository.findByLatitudeAndLongitude(locationDTO.getLatitude(), locationDTO.getLongitude())
                .ifPresent(type -> {throw new AlreadyExistException();});


        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());

        Location saveLocation = locationsRepository.save(location);
        LocationDTO dto = modelMapper.map(saveLocation, LocationDTO.class);
        return dto;
    }

    public void deletePoint(Long id){
        Location location = locationsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        locationsRepository.delete(location);
    }
}
