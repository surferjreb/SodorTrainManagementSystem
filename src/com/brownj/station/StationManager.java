/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StationManager {
    private static final String DB_NAME = "stms.db";
    private static final String CONNECTION_STRING =
            "jdbc:sqlite:/home/capt_bart/JavaProgramming/SodorTrainManagementSystem/" + DB_NAME;
    private static final String TABLE_STATIONS = "stations";
    private static final String COLUMN_STATIONS_ID = "stationID";
    private static final String COLUMN_STATIONS_NAME = "stationName";
    private static final String COLUMN_STATIONS_STATUS = "status";
    private static final String COLUMN_STATIONS_HUB = "hubStation";
    private static final String COLUMN_STATIONS_MAIN = "mainStation";
    private static final String COLUMN_STATIONS_FUEL = "fuelLevel";
    private static final String COLUMN_STATIONS_WATER = "waterLevel";
    private static final int ORDER_BY_NONE = 1;
    private static final int ORDER_BY_ASC = 2;
    private static final int ORDER_BY_DESC = 3;

    public static final String QUERY_FOR_STATIONS = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_HUB + " IS \"false\" AND " + COLUMN_STATIONS_MAIN + " IS \"false\"";

    public static final String QUERY_FOR_MAIN_STATIONS = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_HUB + " IS \"false\" AND " + COLUMN_STATIONS_MAIN + " IS \"true\"";

    public static final String QUERY_FOR_HUB_STATIONS = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_HUB + " IS \"true\"";

    public static final String QUERY_STATION_BY_ID = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_ID + " == ? AND " + COLUMN_STATIONS_MAIN +
            " IS \"false\" AND " + COLUMN_STATIONS_HUB + " IS \"false\"";

    public static final String QUERY_MAIN_BY_ID = "";

    public static final String QUERY_HUB_BY_ID = "";

    public static final String QUERY_STATION_BY_NAME = "";

    public static final String QUERY_MAIN_BY_NAME = "";

    public static final String QUERY_HUB_BY_NAME = "";

    private Connection conn;
    private List<Station> stations = new ArrayList<>();
    private List<MainStation> mainStations = new ArrayList<>();
    private List<HubStation> hubStations = new ArrayList<>();

    private PreparedStatement queryStationID;

    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryStationID = conn.prepareStatement(QUERY_STATION_BY_ID);
            return true;
        } catch(SQLException se){
            System.out.println(se.getMessage());
        }

        return false;
    }

    public boolean close(){
        try{
            if(queryStationID != null){
                queryStationID.close();
            }

            if(conn != null){
                conn.close();
            }
            return true;
        } catch(SQLException se){
            System.out.println("Error Closing connection: " + se.getMessage());
        }

        return false;
    }

    private List<Station> getStations() {
        // return an array list of station objects

        try(Statement st = conn.createStatement();
            ResultSet results = st.executeQuery(QUERY_FOR_STATIONS)){
            List<Station> stations = new ArrayList<>();
            while(results.next()){
                Station station = new Station();
                station.setStationID(results.getInt(COLUMN_STATIONS_ID));
                station.setStationName(results.getString(COLUMN_STATIONS_NAME));
                station.setHubStation(results.getString(COLUMN_STATIONS_HUB));
                station.setMainStation(results.getString(COLUMN_STATIONS_MAIN));
                String temp = results.getString(COLUMN_STATIONS_FUEL);
                temp = results.getString(COLUMN_STATIONS_WATER);
                stations.add(station);
            }

            return stations;
        } catch(SQLException se){
            System.out.println("This went wrong: " + se.getMessage());
        }

        return null;
    }

    private List<MainStation> getMainStations() {
        // return an array list of station objects

        try(Statement st = conn.createStatement();
            ResultSet results = st.executeQuery(QUERY_FOR_MAIN_STATIONS)){
            List<MainStation> stations = new ArrayList<>();
            while(results.next()){
                MainStation station = new MainStation();
                station.setStationID(results.getInt(COLUMN_STATIONS_ID));
                station.setStationName(results.getString(COLUMN_STATIONS_NAME));
                station.setHubStation(results.getString(COLUMN_STATIONS_HUB));
                station.setMainStation(results.getString(COLUMN_STATIONS_MAIN));
                station.setFuelLevel(results.getDouble(COLUMN_STATIONS_FUEL));
                station.setWaterLevel(results.getDouble(COLUMN_STATIONS_WATER));
                stations.add(station);
            }

            return stations;
        } catch(SQLException se){
            System.out.println("This went wrong: " + se.getMessage());
        }

        return null;
    }

    private List<HubStation> getHubStations() {
        // return an array list of station objects

        try(Statement st = conn.createStatement();
            ResultSet results = st.executeQuery(QUERY_FOR_HUB_STATIONS)){
            List<HubStation> stations = new ArrayList<>();
            while(results.next()){
                HubStation station = new HubStation();
                station.setStationID(results.getInt(COLUMN_STATIONS_ID));
                station.setStationName(results.getString(COLUMN_STATIONS_NAME));
                station.setHubStation(results.getString(COLUMN_STATIONS_HUB));
                station.setMainStation(results.getString(COLUMN_STATIONS_MAIN));
                station.setFuelLevel(results.getDouble(COLUMN_STATIONS_FUEL));
                station.setWaterLevel(results.getDouble(COLUMN_STATIONS_WATER));
                stations.add(station);
            }

            return stations;
        } catch(SQLException se){
            System.out.println("This went wrong: " + se.getMessage());
        }

        return null;
    }

    public void setAllStations(){
        try {
            if(this.open()) {
                this.stations = getStations();
                this.mainStations = getMainStations();
                this.hubStations = getHubStations();
            }
        }catch(Exception e){
            System.out.println("Oh no: " + e.getMessage());
        } finally {
            this.close();
        }
    }

    public List<Station> getStationList(){
        return new ArrayList<>(stations);
    }

    public List<MainStation> getMainStationList(){
        return new ArrayList<>(mainStations);
    }

    public List<HubStation> getHubStationList(){
        return new ArrayList<>(hubStations);
    }

    private StringBuilder setOrderBy(int orderBy, String query){
        StringBuilder stringBuilder = new StringBuilder(query);

        switch(orderBy){
            case ORDER_BY_ASC:  stringBuilder.append(" ORDER BY ");
                stringBuilder.append(COLUMN_STATIONS_NAME);
                stringBuilder.append(" ASC");
                break;
            case ORDER_BY_DESC: stringBuilder.append(" ORDER BY ");
                stringBuilder.append(COLUMN_STATIONS_NAME);
                stringBuilder.append(" DESC");
                break;
            default:  stringBuilder.append(" ORDER BY ");
                stringBuilder.append(COLUMN_STATIONS_ID);
        }

        return stringBuilder;
    }

//    public void addStation(String stationName) {
//        /* adds a station to the appropriate list if it does not exist
//           in the list already.  This is done by setting the isHub to true.
//        */
//    }
//
//    public boolean deleteStation(String stationName){
//        // removes a station from the list
//    }
//
//    public boolean deleteHubStation(String stationName){
//        // removes a Hub Station from the list if it exists.  Returns boolean if successful.
//    }
//
//    public String getStationStatus(String stationName) {
//        // gets the status of a station. i.e.: OPEN, CLOSED
//
//    }
//
//    public String getHubStationStatus(String stationName) {
//        // return hub station status if the hub station exists
//    }
//
//    public void openAllStations(){
//        // open all stations in list
//    }
//
//    public void closeAllStations(){
//        // close all stations in list
//    }
//
//    public void openAllHubStations(){
//        // open all hub stations in list
//    }
//
//    public void closeAllHubStations(){
//        // close all hub stations in list
//    }
//
//    public String openStation(String stationName){
//        //change a specific stations status to open
//    }
//
//    public String closeStation(String stationName){
//
//    }
//
//    public String openHubStation(String stationName){
//        //change a specific hub stations status to open
//    }
//
//    public String closeHubStation(String stationName){
//        // change a specific hub stations status to closed
//
//    }
//
//    public boolean getHubStationFuelStatus(String stationName){
//        // get fuel status for hub station if it exists
//    }
//
//    public boolean getHubStationWaterStatus(String stationName){
//        // get hub water status if the hub station exists
//    }
//
//    public void adjustHubFuel(String stationName, double fuelAmount){
//        // adjust fuel level for hub if the hub exists
//    }
//
//    public void adjustHubWater(String stationName, double waterAmount){
//        // adjusts hub station water amount if the hub station exists
//    }

//=========================================================================
    public static void main(String[] args) {
        StationManager stationManager = new StationManager();
        List<Station> stations;
        List<MainStation> mainStations;
        List<HubStation> hubStations;
            stationManager.setAllStations();
            stations = stationManager.getStationList();
            mainStations = stationManager.getMainStationList();
            hubStations = stationManager.getHubStationList();
            if (stations != null) {
                System.out.println("Total: " + stations.size());
                for (Station station : stations) {
                    System.out.println(station.toString());
                }
            }
            System.out.println("=========================================================================");
            if (mainStations != null) {
                System.out.println("Total: " + mainStations.size());
                for (MainStation station : mainStations) {
                    System.out.printf("%d: %s\n",station.getStationID(), station.toString());
                }
            }
            System.out.println("=========================================================================");
            if (hubStations != null) {
                System.out.println("Total: " + hubStations.size());
                for (HubStation station : hubStations) {
                    System.out.printf("%d: %s\n",station.getStationID(), station.toString());
                }
            }


        System.out.println("=========================================================================");

    }

//    private Station createStation(String stationName){
//        // used to create a station
//        //TODO: creates new station in database
//    }
}
