package com.example.animalchipization.service;

import com.example.animalchipization.domain.Location;
import com.example.animalchipization.dto.LocationDTO;
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

    public LocationDTO createLocation(Double latitude, Double longitude){
        Location location = new Location();
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        location = locationsRepository.save(location);
        LocationDTO locationDTO = new LocationDTO(location.getId(), location.getLatitude(), location.getLongitude());

        return locationDTO;
    }

    public LocationDTO getById(Long id){
        return locationsRepository.findById(id)
                .map(type -> modelMapper.map(type, LocationDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    public LocationDTO updatePoint(Long id, LocationDTO locationDTO){
        Location location = locationsRepository.getById(id);

        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());

        Location saveLocation = locationsRepository.save(location);
        LocationDTO dto = modelMapper.map(saveLocation, LocationDTO.class);
        return dto;
    }

    public void deletePoint(Long id){
        locationsRepository.deleteById(id);
    }
}
