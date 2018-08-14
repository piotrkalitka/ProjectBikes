package com.piotrkalitka.ProjectBikes.repository;

import com.piotrkalitka.ProjectBikes.model.Station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    boolean existsByName(String name);

    boolean existsById(Long id);

    Optional<Station> findByName(String name);

}