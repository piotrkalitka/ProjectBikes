package com.piotrkalitka.ProjectBikes.payload;

import javax.validation.constraints.NotBlank;

public class ReturnBikePayload {

    private Long stationId;
    private Long standId;

    public ReturnBikePayload() {
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getStandId() {
        return standId;
    }

    public void setStandId(Long standId) {
        this.standId = standId;
    }
}
