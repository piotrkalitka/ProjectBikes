package com.piotrkalitka.ProjectBikes.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateStationPayload {

    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}