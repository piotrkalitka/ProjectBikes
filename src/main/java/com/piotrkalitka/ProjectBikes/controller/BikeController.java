package com.piotrkalitka.ProjectBikes.controller;

import com.piotrkalitka.ProjectBikes.exception.ApiException;
import com.piotrkalitka.ProjectBikes.exception.ResourceNotFoundException;
import com.piotrkalitka.ProjectBikes.model.Bike;
import com.piotrkalitka.ProjectBikes.model.Stand;
import com.piotrkalitka.ProjectBikes.model.Station;
import com.piotrkalitka.ProjectBikes.payload.ReturnBikePayload;
import com.piotrkalitka.ProjectBikes.repository.BikeRepository;
import com.piotrkalitka.ProjectBikes.repository.StandRepository;
import com.piotrkalitka.ProjectBikes.repository.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    StandRepository standRepository;

    @PatchMapping("/{bikeId}/rent")
    public ResponseEntity<?> rentBike(@PathVariable("bikeId") Long bikeId) {
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new ResourceNotFoundException("bike"));

        if (bike.isRented()) {
            throw new ApiException("Bike is already rented");
        } else if (bike.getStand() == null) {
            //bike not left in stand can not be rented
            throw new ApiException("Can not rent bike not from stand");
        }

        bike.setStand(null);
        bike.setStation(null);
        bikeRepository.save(bike);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{bikeId}/return")
    public ResponseEntity<?> returnBike(@PathVariable("bikeId") Long bikeId, @Valid @RequestBody ReturnBikePayload returnBikePayload) {

        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new ResourceNotFoundException("bike"));
        Station station = stationRepository.findById(returnBikePayload.getStationId()).orElseThrow(() -> new ResourceNotFoundException("station"));

        if (!bike.isRented()) {
            throw new ApiException("Bike is not rented");
        }

        if (returnBikePayload.getStandId() != null) {
            // case when there is free stand and client points where is returning bike

            Stand stand = standRepository.findById(returnBikePayload.getStandId()).orElseThrow(() -> new ResourceNotFoundException("stand"));

            if (stand.isOccupied()) {
                throw new ApiException("Stand is occupied");
            } else if (!standRepository.existsByIdAndStationId(returnBikePayload.getStandId(), returnBikePayload.getStationId())) {
                throw new ApiException("Stand is not in this station");
            }


            bike.setStation(station);
            bike.setStand(stand);

            bikeRepository.save(bike);

        } else {
            //case when there is not free stand, so client leave bike not connected to stand

            List<Stand> stands = standRepository.findByStationId(returnBikePayload.getStationId());
            for (Stand stand : stands) {
                if (!stand.isOccupied()) throw new ApiException("There is at least one free stand");
            }

            bike.setStation(station);

            bikeRepository.save(bike);
        }
        return ResponseEntity.ok().build();
    }
}