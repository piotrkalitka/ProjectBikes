package com.piotrkalitka.ProjectBikes.controller;

import com.piotrkalitka.ProjectBikes.exception.ApiException;
import com.piotrkalitka.ProjectBikes.exception.ResourceNotFoundException;
import com.piotrkalitka.ProjectBikes.model.Stand;
import com.piotrkalitka.ProjectBikes.model.Station;
import com.piotrkalitka.ProjectBikes.repository.StandRepository;
import com.piotrkalitka.ProjectBikes.repository.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javassist.NotFoundException;

@RestController
@RequestMapping("/station/{stationId}/stand")
public class StandController {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    StandRepository standRepository;


    @GetMapping("/{standId}")
    public ResponseEntity<?> getStand(@PathVariable("standId") Long standId){
        Stand stand = standRepository.findById(standId).orElseThrow(() -> new ResourceNotFoundException("stand"));
        return ResponseEntity.ok(stand);
    }

    @GetMapping()
    public ResponseEntity<?> getStands(@PathVariable("stationId") Long stationId){
        if (!stationRepository.existsById(stationId)){
            throw new ResourceNotFoundException("station");
        }
        List<Stand> stands = standRepository.findByStationId(stationId);
        return ResponseEntity.ok(stands);
    }

    @PostMapping()
    public ResponseEntity<?> createStand(@PathVariable("stationId") Long stationId) {
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new ResourceNotFoundException("station"));

        Stand stand = new Stand();
        stand.setStation(station);

        Set<Stand> stands = station.getStands();
        stands.add(stand);
        station.setStands(stands);

        stationRepository.save(station);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{standId}")
    public ResponseEntity<?> deleteStand(@PathVariable("standId") Long standId){
        Stand stand = standRepository.findById(standId).orElseThrow(() -> new ResourceNotFoundException("stand"));

        if (stand.isOccupied()) {
            throw new ApiException("Stand is occupied. Move the bike first");
        }

        Station station = stand.getStation();
        station.getStands().removeIf(setStand -> setStand.getId().equals(standId));

        stationRepository.save(station);
        standRepository.delete(stand);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
