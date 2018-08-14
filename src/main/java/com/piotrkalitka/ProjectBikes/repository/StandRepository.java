package com.piotrkalitka.ProjectBikes.repository;

import com.piotrkalitka.ProjectBikes.model.Bike;
import com.piotrkalitka.ProjectBikes.model.Stand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {

    List<Stand> findByStationId(Long stationId);

    boolean existsByIdAndStationId(Long id, Long stationId);

}