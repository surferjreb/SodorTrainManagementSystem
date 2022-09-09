package com.brownj.station;

public class MainStation extends Station{

    private double fuelLevel;
    private double waterLevel;

    public MainStation(){
        this.stationType = STATION_TYPE.MAIN_STATION.toString();
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

}
