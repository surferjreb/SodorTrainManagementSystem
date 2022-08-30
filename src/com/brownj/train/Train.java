package com.brownj.train;

import com.brownj.route.Route;

public class Train {

    private final Route myRoute;
    private final String trainName;
    private final Tender tender;
    private final int trainId;

    private Boolean tenderConnected;

    private int waterLevel = 100;
    private int fuelLevel = 100;
    private int trainSpeed = 0;
    private double opHours = 0;
    private double totalOpHours;

    public Train(String trainName, int trainId, Route route){
        this.tenderConnected = false;
        this.trainName = trainName;
        this.myRoute = route;
        this.trainId = trainId;
        this.tender = new Tender();
        this.totalOpHours = 0;
    } //Constructor

    public Train(String trainName, int trainId, Route route, int totalOpHours){
        this(trainName, trainId, route);
        this.totalOpHours = totalOpHours;
    } // Constructor

    public Boolean getNeedFuel() {
        return fuelLevel < 25;
    }

    public Boolean getNeedWater() {
        return waterLevel < 75;
    }

    public Boolean getMoving() {
        return trainSpeed > 0;
    }

    public int getTrainSpeed() {
        return trainSpeed;
    }

    public int getTrainId() {
        return trainId;
    }

    public Boolean getTenderConnected() {
        return tenderConnected;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public double getOpHours() {
        return opHours;
    }

    public double getTotalOpHours() {
        return totalOpHours;
    }

    public void setTrainSpeed(int trainSpeed) {
        if(trainSpeed >= 0) {
            this.trainSpeed = trainSpeed;
        }
    }

    public void setTenderConnected(Boolean tenderConnected) {
        this.tenderConnected = tenderConnected;
    }

    public void resetWaterLevel() {
        this.waterLevel = 100;
    }

    public void resetFuelLevel() {
        this.fuelLevel = 100;
    }

    public void setOpHours(double opHours) {
        this.opHours = opHours;
    }

    public void setTotalOpHours(double totalOpHours) {
        this.totalOpHours = totalOpHours;
    }


}
