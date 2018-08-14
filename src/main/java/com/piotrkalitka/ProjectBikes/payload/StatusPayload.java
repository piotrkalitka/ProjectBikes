package com.piotrkalitka.ProjectBikes.payload;

import com.piotrkalitka.ProjectBikes.model.Station;

import java.util.List;

public class StatusPayload {

    private List<StationStatus> stations;

    public StatusPayload() {
    }

    public StatusPayload(List<StationStatus> stations) {
        this.stations = stations;
    }

    public List<StationStatus> getStations() {
        return stations;
    }

    public void setStations(List<StationStatus> stations) {
        this.stations = stations;
    }


    public static class StationStatus {
        private String name;
        private int standsFree;
        private int standsOccupied;
        private int bikesAvailable;

        public StationStatus() {
        }

        public StationStatus(String name, int standsFree, int standsOccupied, int bikesAvailable) {
            this.name = name;
            this.standsFree = standsFree;
            this.standsOccupied = standsOccupied;
            this.bikesAvailable = bikesAvailable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStandsFree() {
            return standsFree;
        }

        public void setStandsFree(int standsFree) {
            this.standsFree = standsFree;
        }

        public int getStandsOccupied() {
            return standsOccupied;
        }

        public void setStandsOccupied(int standsOccupied) {
            this.standsOccupied = standsOccupied;
        }

        public int getBikesAvailable() {
            return bikesAvailable;
        }

        public void setBikesAvailable(int bikesAvailable) {
            this.bikesAvailable = bikesAvailable;
        }
    }

}
