package com.brownj.station;

import java.util.ArrayList;
import java.util.List;

public class StationManager {
    private List<Station> stations;
    private List<HubStation> hubStations;

    public StationManager(){
        stations = new ArrayList<>();
        hubStations = new ArrayList<>();
    }

    public Station createStation(String stationName){
        return new Station(stationName);
    }

    public HubStation createHubStation(String stationName){
        return new HubStation(stationName);
    }

    public List<Station> getStations() {
        return new ArrayList<>(stations);
    }

    public void addStation(String stationName, boolean isHub) {
        if(isHub){
            HubStation hubStation = createHubStation(stationName);
            if(!hubStations.contains(hubStation)){
                hubStations.add(hubStation);
            }

        } else {
            Station station = createStation(stationName);
            if (!stations.contains(station)) {
                stations.add(station);
            }
        }
    }

    public boolean deleteStation(String stationName){
        Station station = getStation(stationName);
        if(station != null){
            stations.remove(station);
            return true;
        }

        return false;
    }

    public boolean deleteHubStation(String stationName){
        HubStation hubStation = getHubStation(stationName);
        if(hubStation != null){
            stations.remove(hubStation);
            return true;
        }

        return false;
    }

    public String getStationStatus(String stationName) {
        Station station = getStation(stationName);
        if(station != null){
            return station.getStationStatus();
        }

        return null;
    }

    public String getHubStationStatus(String stationName) {
        HubStation station = getHubStation(stationName);
        if(station != null){
            return station.getStationStatus();
        }

        return null;
    }

    public void openAllStations(){
        // open all stations in list
        for(Station station: stations){
            station.openStation();
        }
    }

    public void closeAllStations(){
        // close all stations in list
        for(Station station: stations){
            station.closeStation();
        }
    }

    public String openStation(String stationName){
        Station station = getStation(stationName);

        if(station != null){
            station.openStation();
            return station.getStationStatus();
        }

        return null;
    }

    public String closeStation(String stationName){

        Station station = getStation(stationName);
        if(station != null){
            station.closeStation();
            return station.getStationStatus();
        }

        return null;
    }

    public boolean getHubStationFuelStatus(String stationName){
        HubStation hubStation = getHubStation(stationName);
        if(hubStation != null){
            return hubStation.needsFuel();
        }

        return false;
    }

    public boolean getHubStationWaterStatus(String stationName){
        HubStation hubStation = (HubStation) getStation(stationName);
        if(hubStation != null){
            return hubStation.needsWater();
        }

        return false;
    }

    public void adjustHubFuel(String stationName, double fuelAmount){
        try {
            HubStation hubStation = (HubStation) getStation(stationName);
            if (hubStation != null) {
                hubStation.lowerFuelLevel(fuelAmount);
            }

        }catch(Exception e) {
            System.out.println("Not a hub Station");
        }
    }

    public void adjustHubWater(String stationName, double waterAmount){
        try {
            HubStation hubStation = (HubStation) getStation(stationName);
            if (hubStation != null) {
                hubStation.adjustWaterLevel(waterAmount);
            }

        }catch(Exception e) {
            System.out.println("Not a hub Station");
        }
    }

    private Station getStation(String stationName) {
        for (Station station : stations) {
            if (station.toString().equalsIgnoreCase(stationName)) {
                return station;
            }
        }

        return null;
    }

    private HubStation getHubStation(String stationName) {
        for (HubStation hubStation : hubStations) {
            if (hubStation.toString().equalsIgnoreCase(stationName)) {
                return hubStation;
            }
        }

        return null;
    }

//=========================================================================
//    public static void main(String[] args) {
//        StationManager stationManager = new StationManager();
//        stationManager.addStation("crovans gate", true);
//        stationManager.addStation("ballahoo", false);
//
//        System.out.println(stationManager.getStationStatus("ballahoo"));
//        System.out.println(stationManager.getHubStationStatus("crovans gate"));
//        System.out.println(stationManager.getStationStatus("ballahoo"));
//        System.out.println(stationManager.getStationStatus("kirk"));
//        System.out.println(stationManager.getStationStatus("kirk"));
//        System.out.println(stationManager.getHubStationStatus("abbey"));
//
//        stationManager.adjustHubFuel("crovans gate", 45000);
//        stationManager.adjustHubWater("crovans gate", 75000);
//        stationManager.adjustHubFuel("abbey", 45000);
//        stationManager.adjustHubFuel("drake", 45000);
//
//        System.out.println(stationManager.getHubStationFuelStatus("crovans gate"));
//        System.out.println(stationManager.getHubStationWaterStatus("crovans gate"));
//
//        System.out.println();
//
//    }
}
