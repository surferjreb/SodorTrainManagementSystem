/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.station;

public class HubStation extends Station{

    private final double FUEL_AMOUNT = 50000d;
    private final double WATER_AMOUNT = 100000d;
    private double fuelLevel;
    private double waterLevel;

    protected double getFuelLevel() {
        return fuelLevel;
    }

    protected double getWaterLevel() {
        return waterLevel;
    }

    protected void setFuelLevel(double fuelLevel){
        this.fuelLevel = fuelLevel;
    }

    protected void setWaterLevel(double waterLevel){
        this.waterLevel = waterLevel;
    }

    protected void lowerFuelLevel(double fuelUsed) {
        if(fuelUsed < fuelLevel) {
            this.fuelLevel -= fuelUsed;
        }
    }

    protected void lowerWaterLevel(double waterUsed) {
        if(waterUsed < waterLevel) {
            this.waterLevel -= waterUsed;
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
