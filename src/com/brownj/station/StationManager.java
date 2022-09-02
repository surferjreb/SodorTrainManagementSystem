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

    private static final String TABLE_HUB_STATIONS = "hubStations";
    private static final String COLUMN_HUB_STATIONS_ID = "stationID";
    private static final String COLUMN_HUB_STATIONS_NAME = "stationName";
    private static final String COLUMN_HUB_STATIONS_STATUS = "status";
    private static final String COLUMN_HUB_STATIONS_FUEL = "fuelLevel";
    private static final String COLUMN_HUB_STATIONS_WATER = "waterLevel";

    private static final String TABLE_MAIN_STATIONS = "mainStations";
    private static final String COLUMN_MAIN_STATIONS_ID = "stationID";
    private static final String COLUMN_MAIN_STATIONS_NAME = "stationName";
    private static final String COLUMN_MAIN_STATIONS_STATUS = "status";
    private static final String COLUMN_MAIN_STATIONS_FUEL = "fuelLevel";
    private static final String COLUMN_MAIN_STATIONS_WATER = "waterLevel";

    private static final int ORDER_BY_NONE = 1;
    private static final int ORDER_BY_ASC = 2;
    private static final int ORDER_BY_DESC = 3;

    // Queries for returning a list of stations, main stations, and hub stations.
    public static final String QUERY_FOR_STATIONS = "SELECT * FROM " + TABLE_STATIONS;

    public static final String QUERY_FOR_MAIN_STATIONS = "SELECT * FROM " + TABLE_MAIN_STATIONS;

    public static final String QUERY_FOR_HUB_STATIONS = "SELECT * FROM " + TABLE_HUB_STATIONS;

    // Queries for getting a station, main station and hub station by station id.
    public static final String QUERY_STATION_BY_ID = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_ID + " == ? ";

    public static final String QUERY_MAIN_BY_ID = "SELECT * FROM " + TABLE_MAIN_STATIONS +
            " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ? ";

    public static final String QUERY_HUB_BY_ID = "SELECT * FROM " + TABLE_HUB_STATIONS +
            " WHERE " + COLUMN_HUB_STATIONS_ID + " == ? ";

    // Queries for returning station type object by station name.
    public static final String QUERY_STATION_BY_NAME = "SELECT * FROM " + TABLE_STATIONS
            + " WHERE " + COLUMN_STATIONS_NAME + " == ?";

    public static final String QUERY_MAIN_BY_NAME = "SELECT * FROM " + TABLE_MAIN_STATIONS
            + " WHERE " + COLUMN_MAIN_STATIONS_NAME + " == ?";

    public static final String QUERY_HUB_BY_NAME = "SELECT * FROM " + TABLE_HUB_STATIONS
            + " WHERE " + COLUMN_HUB_STATIONS_NAME + " == ?";

    // Update fuel values
    public static final String UPDATE_MAIN_LEVELS = "UPDATE " + TABLE_MAIN_STATIONS
            + " SET ? = ? "
            + " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ?";

    public static final String UPDATE_HUB_LEVELS = "UPDATE " + TABLE_HUB_STATIONS
            + " SET ? = ? "
            + " WHERE " + COLUMN_HUB_STATIONS_ID + " == ?";

    private Connection conn;

    private PreparedStatement queryStationID;
    private PreparedStatement queryMainStationID;
    private PreparedStatement queryHubStationID;
    private PreparedStatement queryStationName;
    private PreparedStatement queryMainStationName;
    private PreparedStatement queryHubStationName;
    private PreparedStatement updateMainLevels;
    private PreparedStatement updateHubLevels;

    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryStationID = conn.prepareStatement(QUERY_STATION_BY_ID);
            queryMainStationID = conn.prepareStatement(QUERY_MAIN_BY_ID);
            queryHubStationID = conn.prepareStatement(QUERY_HUB_BY_ID);
            queryStationName = conn.prepareStatement(QUERY_STATION_BY_NAME);
            queryMainStationName = conn.prepareStatement(QUERY_MAIN_BY_NAME);
            queryHubStationName = conn.prepareStatement(QUERY_HUB_BY_NAME);
            updateMainLevels = conn.prepareStatement(UPDATE_MAIN_LEVELS);
            updateHubLevels = conn.prepareStatement(UPDATE_HUB_LEVELS);

            return true;
        } catch(SQLException se){
            System.out.println(se.getMessage());
        }

        return false;
    }

    public boolean close(){
        try{
            if(updateHubLevels != null){
                updateHubLevels.close();
            }
            if(updateMainLevels != null){
                updateMainLevels.close();
            }
            if(queryHubStationName != null){
                queryHubStationName.close();
            }
            if(queryMainStationName != null){
                queryMainStationName.close();
            }
            if(queryHubStationID != null){
                queryHubStationID.close();
            }
            if(queryMainStationID != null){
                queryMainStationID.close();
            }
            if(queryStationName != null){
                queryStationName.close();
            }
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

    public int getStationID(String name){
        try{
            Station station = getStation(name);
            if(station != null){
                return station.getStationID();
            }
        } catch (Exception e){
            System.out.println("Station id find failed: " + e.getMessage());
        }

        return -1;
    }

    public String getStationName(int id){
        try{
            Station station = getStation(id);
            if(station != null){
                return station.toString();
            }
        } catch (Exception e){
            System.out.println("Error getting station name");
        }

        return new String("NONE");
    }

    public String getStationStatus(int id){
        try{
            Station station = getStation(id);
            if(station != null){
                return station.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting station status");
        }

        return null;
    }

    public String getStationStatus(String name){
        try{
            Station station = getStation(name);
            if(station != null){
                return station.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting station status");
        }

        return null;
    }

    public int getMainStationID(String name){
        try{
            MainStation mainStation = getMainStation(name);
            if(mainStation != null){
                return mainStation.getStationID();
            }
        } catch(Exception e){
            System.out.println("Error getting main station id: " + e.getMessage());
        }

        return -1;
    }

    public String getMainStationName(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.toString();
            }
        } catch(Exception e){
            System.out.println("Error retrieving station name: " + e.getMessage());
        }

        return new String("NONE");
    }

    public String getMainStationStatus(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting main station status");
        }

        return null;
    }

    public String getMainStationStatus(String name){
        try{
            MainStation mainStation = getMainStation(name);
            if(mainStation != null){
                return mainStation.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting main station status");
        }

        return null;
    }

    public double getMainStationFuelLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.getFuelLevel();
            }
        } catch(Exception e){
            System.out.println("Error retrieving main station fuel level: " + e.getMessage());
        }

        return 0;
    }

    public double getMainStationWaterLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.getWaterLevel();
            }
        } catch(Exception e){
            System.out.println("Error retrieving main station fuel level: " + e.getMessage());
        }

        return 0;
    }

    public int getHubStationID(String name){
        try{
            HubStation hubStation = getHubStation(name);
            if(hubStation != null){
                return hubStation.getStationID();
            }
        } catch(Exception e){
            System.out.println("Error retrieving hub station id: " + e.getMessage());
        }

        return -1;
    }

    public String getHubStationName(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                return hubStation.toString();
            }
        } catch(Exception e){
            System.out.println("Error retrieving hub station name: " + e.getMessage());
        }

        return new String("NONE");
    }

    public String getHubStationStatus(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                return hubStation.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting hub station status");
        }

        return null;
    }

    public String getHubStationStatus(String name){
        try{
            HubStation hubStation = getHubStation(name);
            if(hubStation != null){
                return hubStation.getStationStatus();
            }
        } catch (Exception e){
            System.out.println("Error getting hub station status");
        }

        return null;
    }

    public double getHubStationFuelLevel(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                return hubStation.getFuelLevel();
            }
        } catch (Exception e){
            System.out.println("Error getting hub station status");
        }

        return -1;
    }

    public double getHubStationWaterLevel(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                return hubStation.getWaterLevel();
            }
        } catch (Exception e){
            System.out.println("Error getting hub station status");
        }

        return -1;
    }

    private List<Station> getStations() {
        // return an array list of station objects
        if(open()) {
            try (Statement st = conn.createStatement();
                 ResultSet results = st.executeQuery(QUERY_FOR_STATIONS)) {
                List<Station> stations = new ArrayList<>();
                while (results.next()) {
                    Station station = new Station();
                    station.setStationID(results.getInt(COLUMN_STATIONS_ID));
                    station.setStationName(results.getString(COLUMN_STATIONS_NAME));
                    station.setStationStatus(results.getString(COLUMN_STATIONS_STATUS));
                    stations.add(station);
                }

                return stations;
            } catch (SQLException se) {
                System.out.println("This went wrong: " + se.getMessage());
            }
        }

        return null;
    }

    private List<MainStation> getMainStations() {
        // return an array list of station objects
        if(open()) {
            try (Statement st = conn.createStatement();
                 ResultSet results = st.executeQuery(QUERY_FOR_MAIN_STATIONS)) {
                List<MainStation> stations = new ArrayList<>();
                while (results.next()) {
                    MainStation station = new MainStation();
                    station.setStationID(results.getInt(COLUMN_MAIN_STATIONS_ID));
                    station.setStationName(results.getString(COLUMN_MAIN_STATIONS_NAME));
                    station.setStationStatus(results.getString(COLUMN_MAIN_STATIONS_STATUS));
                    station.setFuelLevel(results.getDouble(COLUMN_MAIN_STATIONS_FUEL));
                    station.setWaterLevel(results.getDouble(COLUMN_MAIN_STATIONS_WATER));
                    stations.add(station);
                }

                return stations;
            } catch (SQLException se) {
                System.out.println("This went wrong: " + se.getMessage());
            }
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
                station.setStationID(results.getInt(COLUMN_HUB_STATIONS_ID));
                station.setStationName(results.getString(COLUMN_HUB_STATIONS_NAME));
                station.setStationStatus(results.getString(COLUMN_HUB_STATIONS_STATUS));
                station.setFuelLevel(results.getDouble(COLUMN_HUB_STATIONS_FUEL));
                station.setWaterLevel(results.getDouble(COLUMN_HUB_STATIONS_WATER));
                stations.add(station);
            }

            return stations;
        } catch(SQLException se){
            System.out.println("This went wrong: " + se.getMessage());
        }

        return null;
    }

    private Station getStation(int id) {
        if(open()){
            try{
                queryStationID.setInt(1, id);
                ResultSet resultSet = queryStationID.executeQuery();
                if(resultSet.next()){
                    Station station = new Station();
                    station.setStationID(resultSet.getInt(COLUMN_STATIONS_ID));
                    station.setStationName(resultSet.getString(COLUMN_STATIONS_NAME));
                    station.setStationStatus(resultSet.getString(COLUMN_STATIONS_STATUS));
                    return station;
                }

            } catch(SQLException se){
                System.out.println("Error: " + se.getMessage());
            }
        }
        return null;
    }

    private Station getStation(String name){
        if(open()){
            try{
                queryStationName.setString(1, name);
                ResultSet resultSet = queryStationName.executeQuery();
                if(resultSet.next()){
                    Station station = new Station();
                    station.setStationID(resultSet.getInt(COLUMN_STATIONS_ID));
                    station.setStationName(resultSet.getString(COLUMN_STATIONS_NAME));
                    station.setStationStatus(resultSet.getString(COLUMN_STATIONS_STATUS));
                    return station;
                }

            } catch(SQLException se){
                System.out.println("Error: " + se.getMessage());
            }

        }
        return null;
    }

    private MainStation getMainStation(int id){
        if(open()){
            try{
                queryMainStationID.setInt(1, id);
                ResultSet resultSet = queryMainStationID.executeQuery();
                if(resultSet.next()){
                    MainStation mainStation = new MainStation();
                    mainStation.setStationID(resultSet.getInt(COLUMN_MAIN_STATIONS_ID));
                    mainStation.setStationName(resultSet.getString(COLUMN_MAIN_STATIONS_NAME));
                    mainStation.setStationStatus(resultSet.getString(COLUMN_MAIN_STATIONS_STATUS));
                    mainStation.setFuelLevel(resultSet.getDouble(COLUMN_MAIN_STATIONS_FUEL));
                    mainStation.setWaterLevel(resultSet.getDouble(COLUMN_MAIN_STATIONS_WATER));
                    return mainStation;
                }
            } catch (SQLException se){
                System.out.println("Error getting main station by id: " + se.getMessage());
            }
        }
        return null;
    }

    private MainStation getMainStation(String name){
        if(open()){
            try{
                queryMainStationName.setString(1, name);
                ResultSet resultSet = queryMainStationName.executeQuery();
                if(resultSet.next()){
                    MainStation mainStation = new MainStation();
                    mainStation.setStationID(resultSet.getInt(COLUMN_MAIN_STATIONS_ID));
                    mainStation.setStationName(resultSet.getString(COLUMN_MAIN_STATIONS_NAME));
                    mainStation.setStationStatus(resultSet.getString(COLUMN_MAIN_STATIONS_STATUS));
                    mainStation.setFuelLevel(resultSet.getDouble(COLUMN_MAIN_STATIONS_FUEL));
                    mainStation.setWaterLevel(resultSet.getDouble(COLUMN_MAIN_STATIONS_WATER));
                    return mainStation;
                }
            } catch (SQLException se){
                System.out.println("Error getting main station by name: " + se.getMessage());
            }
        }
        return null;
    }

    private HubStation getHubStation(int id){
        if(open()){
            try{
                queryHubStationID.setInt(1, id);
                ResultSet resultSet = queryHubStationID.executeQuery();
                if(resultSet.next()){
                    HubStation hubStation = new HubStation();
                    hubStation.setStationID(resultSet.getInt(COLUMN_HUB_STATIONS_ID));
                    hubStation.setStationName(resultSet.getString(COLUMN_HUB_STATIONS_NAME));
                    hubStation.setStationStatus(resultSet.getString(COLUMN_HUB_STATIONS_STATUS));
                    hubStation.setFuelLevel(resultSet.getDouble(COLUMN_HUB_STATIONS_FUEL));
                    hubStation.setWaterLevel(resultSet.getDouble(COLUMN_HUB_STATIONS_WATER));
                    return hubStation;
                }
            } catch (SQLException se){
                System.out.println("Error getting main station: " + se.getMessage());
            }
        }
        return null;
    }

    private HubStation getHubStation(String name){
        if(open()){
            try{
                queryHubStationName.setString(1, name);
                ResultSet resultSet = queryHubStationName.executeQuery();
                if(resultSet.next()){
                    HubStation hubStation = new HubStation();
                    hubStation.setStationID(resultSet.getInt(COLUMN_HUB_STATIONS_ID));
                    hubStation.setStationName(resultSet.getString(COLUMN_HUB_STATIONS_NAME));
                    hubStation.setStationStatus(resultSet.getString(COLUMN_HUB_STATIONS_STATUS));
                    hubStation.setFuelLevel(resultSet.getDouble(COLUMN_HUB_STATIONS_FUEL));
                    hubStation.setWaterLevel(resultSet.getDouble(COLUMN_HUB_STATIONS_WATER));
                    return hubStation;
                }
            } catch (SQLException se){
                System.out.println("Error getting main station: " + se.getMessage());
            }
        }
        return null;
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
            stations = stationManager.getStations();
            mainStations = stationManager.getMainStations();
            hubStations = stationManager.getHubStations();
            if (stations != null) {
                System.out.println("Station Total: " + stations.size());
            }
            System.out.println("=========================================================================");
            if (mainStations != null) {
                System.out.println("Main Station Total: " + mainStations.size());
            }
            System.out.println("=========================================================================");
            if (hubStations != null) {
                System.out.println("Hub Station Total: " + hubStations.size());
            }


        System.out.println("=========================================================================");
            stationManager.close();
    }

//    private Station createStation(String stationName){
//        // used to create a station
//        //TODO: creates new station in database
//    }
}
