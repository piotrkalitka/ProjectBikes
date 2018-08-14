package com.piotrkalitka.ProjectBikes.payload;

import com.piotrkalitka.ProjectBikes.model.Station;

import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;

public class UpdateStationPayload {

    @Size(min = 3, max = 64)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station updateStation(Station station) {
        if (!StringUtils.isEmpty(name)) {
            station.setName(name);
        }
        return station;
    }

}