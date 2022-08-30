package com.brownj.station;

public class HubStation extends Station{
    private final String HUB_STATION = "Hub Station: ";
    private final double FUEL_AMOUNT = 50000d;
    private final double WATER_AMOUNT = 100000d;
    private double fuelLevel;
    private double waterLevel;

    protected HubStation(String stationName){
        super(stationName);
        fuelLevel = FUEL_AMOUNT;
        waterLevel = WATER_AMOUNT;
    }

    protected double getFuelLevel() {
        return fuelLevel;
    }

    protected double getWaterLevel() {
        return waterLevel;
    }

    protected void lowerFuelLevel(double fuelUsed) {
        if(fuelUsed < fuelLevel) {
            this.fuelLevel -= fuelUsed;
        }
    }

    protected void adjustWaterLevel(double waterUsed) {
        if(waterUsed < waterLevel) {
            this.waterLevel -= waterUsed;
        }
    }

    protected void setWaterLevel(double waterLevel) {
        if(waterLevel > 0) {
            this.waterLevel = waterLevel;
        }
    }

    protected void resetWater(){
        this.waterLevel = WATER_AMOUNT;
    }

    protected void resetFuel(){
        this.fuelLevel = FUEL_AMOUNT;
    }

    protected boolean needsFuel(){
        return fuelLevel <= (FUEL_AMOUNT/4);
    }

    protected boolean needsWater(){
        return waterLevel <= (WATER_AMOUNT/4);
    }

    @Override
    protected boolean isHubStation(){
        return true;
    }

//==========================================================================================
//
//    public static void main(String[] args) {
//        HubStation mainStation = new HubStation("crovans gate");
//        Station station = new Station("ballahoo");
//
//        System.out.println(mainStation);
//        System.out.println(mainStation.isHubStation());
//        System.out.println(mainStation.getStationStatus());
//        System.out.println(station);
//        System.out.println(station.getStationStatus());
//    }
}
