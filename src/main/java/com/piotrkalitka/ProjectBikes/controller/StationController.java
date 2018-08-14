package com.piotrkalitka.ProjectBikes.controller;

import com.piotrkalitka.ProjectBikes.exception.ApiException;
import com.piotrkalitka.ProjectBikes.exception.ResourceNotFoundException;
import com.piotrkalitka.ProjectBikes.model.Bike;
import com.piotrkalitka.ProjectBikes.model.Stand;
import com.piotrkalitka.ProjectBikes.model.Station;
import com.piotrkalitka.ProjectBikes.payload.CreateStationPayload;
import com.piotrkalitka.ProjectBikes.payload.StatusPayload;
import com.piotrkalitka.ProjectBikes.payload.UpdateStationPayload;
import com.piotrkalitka.ProjectBikes.repository.StationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    StationRepository stationRepository;

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        List<Station> stations = stationRepository.findAll();
        List<StatusPayload.StationStatus> stationStatuses = new ArrayList<>();

        for (Station station : stations) {
            int standsCount = station.getStands().size();

            String name = station.getName();
            int standsFree = calcFreeStands(station.getStands());
            int standsOccupied = standsCount - standsFree;
            int bikesAvailable = calcAvailableBikes(station.getBikes());

            StatusPayload.StationStatus stationStatus = new StatusPayload.StationStatus(name, standsFree, standsOccupied, bikesAvailable);
            stationStatuses.add(stationStatus);
        }

        return ResponseEntity.ok(stationStatuses);
    }

    @GetMapping()
    public ResponseEntity<?> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<?> getStation(@PathVariable("stationId") Long stationId) {
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new ResourceNotFoundException("station"));
        return ResponseEntity.ok(station);
    }

    @PostMapping()
    public ResponseEntity<?> createStation(@Valid @RequestBody CreateStationPayload createStation) {
        if (stationRepository.existsByName(createStation.getName())) {
            throw new ApiException("Station with given name already exists");
        }

        Station station = new Station(createStation.getName());
        station = stationRepository.save(station);
        return ResponseEntity.ok(station);
    }

    @PatchMapping("/{stationId}")
    public ResponseEntity<?> updateStation(@PathVariable("stationId") Long stationId, @Valid @RequestBody UpdateStationPayload createStation) {
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new ResourceNotFoundException("station"));
        station = createStation.updateStation(station);
        station = stationRepository.save(station);
        return ResponseEntity.ok(station);
    }

    @DeleteMapping("/{stationId}")
    public ResponseEntity<?> deleteStation(@PathVariable("stationId") Long stationid) {
        Station station = stationRepository.findById(stationid).orElseThrow(() -> new ResourceNotFoundException("station"));
        stationRepository.delete(station);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private int calcAvailableBikes(Collection<Bike> bikes) {
        //Bike is only available when it is in Stand
        int bikesAvailable = 0;
        for (Bike bike : bikes) {
            if (bike.getStand() != null) bikesAvailable++;
        }
        return bikesAvailable;
    }

    private int calcFreeStands(Collection<Stand> stands) {
        int standsFree = 0;
        for (Stand stand : stands) {
            if (!stand.isOccupied()) standsFree++;
        }
        return standsFree;
    }

}