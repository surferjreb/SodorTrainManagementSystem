/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.station;

public class MainStation extends Station {
    private final double FUEL_AMOUNT = 20000d;
    private final double WATER_AMOUNT = 50000d;
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
        if (fuelUsed < fuelLevel) {
            this.fuelLevel -= fuelUsed;
        }
    }

    protected void lowerWaterLevel(double waterUsed) {
        if (waterUsed < waterLevel) {
            this.waterLevel -= waterUsed;
        }
    }

    protected void resetWater() {
        this.waterLevel = WATER_AMOUNT;
    }

    protected void resetFuel() {
        this.fuelLevel = FUEL_AMOUNT;
    }

    protected boolean needsFuel() {
        return fuelLevel <= (FUEL_AMOUNT / 4);
    }

    protected boolean needsWater() {
        return waterLevel <= (WATER_AMOUNT / 4);
    }

}