package com.brownj.train;

public class Tender {
    private int waterLevel;
    private int fuelLevel;

    public Tender(){
        this.fuelLevel = 100;
        this.waterLevel = 100;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void resetWaterLevel() {
        this.waterLevel = 100;
    }

    public void resetFuelLevel() {
        this.fuelLevel = 100;
    }

    public boolean needsWater() {
        return waterLevel < 50;
    }

    public boolean needsFuel() {
        return fuelLevel < 25;
    }

}
