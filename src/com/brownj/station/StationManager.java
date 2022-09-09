/**
 *  @author James R. Brown
 *  The Station Manager class handles retrieving station, main station, hub station objects and updating those objects in
 *  the Database tables.
 **/

package com.brownj.station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationManager {
    /**
     * Name of the Database for the system.
     **/
    private static final String DB_NAME = "stms.db";
    /**
     * Connection string for database
     **/
    private static final String CONNECTION_STRING =
            "jdbc:sqlite:/home/capt_bart/JavaProgramming/SodorTrainManagementSystem/" + DB_NAME;

    /**
     * Enum for returning fuel status
     **/
    enum STATION_LEVEL_STATUS {FULL, MEDIUM, EMPTY};
    /**
     *  Table name String for Stations table
     **/
    private static final String TABLE_STATIONS = "stations";
    /**
     *  Stations Table Station Id Column String
     **/
    private static final String COLUMN_STATIONS_ID = "stationID";
    /**
     *  Stations table Station name column String
     **/
    private static final String COLUMN_STATIONS_NAME = "stationName";
    /**
     *  Stations table Station status column String
     **/
    private static final String COLUMN_STATIONS_STATUS = "status";
    /**
     *  Table name String for Hub Stations table
     **/
    private static final String TABLE_HUB_STATIONS = "hubStations";
    /**
     * Hub Stations table Column station id
     **/
    private static final String COLUMN_HUB_STATIONS_ID = "stationID";
    /**
     * Hub stations table Column Station name
     **/
    private static final String COLUMN_HUB_STATIONS_NAME = "stationName";
    /**
     * Hub stations table Column Station status
     **/
    private static final String COLUMN_HUB_STATIONS_STATUS = "status";
    /**
     * Hub stations table Column Station fuel level
     **/
    private static final String COLUMN_HUB_STATIONS_FUEL = "fuelLevel";
    /**
     * Hub stations table Column Station water level
     **/
    private static final String COLUMN_HUB_STATIONS_WATER = "waterLevel";
    /**
     * Table name for Main stations table
     **/
    private static final String TABLE_MAIN_STATIONS = "mainStations";
    /**
     * Main Stations table column station id
     **/
    private static final String COLUMN_MAIN_STATIONS_ID = "stationID";
    /**
     * Main Stations table column station name
     **/
    private static final String COLUMN_MAIN_STATIONS_NAME = "stationName";
    /**
     * Main stations table column station status
     **/
    private static final String COLUMN_MAIN_STATIONS_STATUS = "status";
    /**
     * Main stations table column station fuel level
     **/
    private static final String COLUMN_MAIN_STATIONS_FUEL = "fuelLevel";
    /**
     * Main stations table column station water level
     **/
    private static final String COLUMN_MAIN_STATIONS_WATER = "waterLevel";

//    private static final int ORDER_BY_NONE = 1;
//    private static final int ORDER_BY_ASC = 2;
//    private static final int ORDER_BY_DESC = 3;

    /**
     * Query string for all stations in table stations
     **/
    public static final String QUERY_FOR_STATIONS = "SELECT * FROM " + TABLE_STATIONS;
    /**
     * Query string for all main stations in main stations table
     **/
    public static final String QUERY_FOR_MAIN_STATIONS = "SELECT * FROM " + TABLE_MAIN_STATIONS;
    /**
     * Query string for all hub stations in hub stations table
     **/
    public static final String QUERY_FOR_HUB_STATIONS = "SELECT * FROM " + TABLE_HUB_STATIONS;

    /**
     * Query for station by id from stations table
     **/
    public static final String QUERY_STATION_BY_ID = "SELECT * FROM " + TABLE_STATIONS +
            " WHERE " + COLUMN_STATIONS_ID + " == ? ";
    /**
     * Query for main station from main stations table
     **/
    public static final String QUERY_MAIN_BY_ID = "SELECT * FROM " + TABLE_MAIN_STATIONS +
            " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ? ";

    /**
     * Query for hub station by id from hub stations table
     **/
    public static final String QUERY_HUB_BY_ID = "SELECT * FROM " + TABLE_HUB_STATIONS +
            " WHERE " + COLUMN_HUB_STATIONS_ID + " == ? ";

    /**
     * Query for station by station name from stations table
     **/
    public static final String QUERY_STATION_BY_NAME = "SELECT * FROM " + TABLE_STATIONS
            + " WHERE " + COLUMN_STATIONS_NAME + " == ?";

    /**
     * Query for main station by name from main stations table
     **/
    public static final String QUERY_MAIN_BY_NAME = "SELECT * FROM " + TABLE_MAIN_STATIONS
            + " WHERE " + COLUMN_MAIN_STATIONS_NAME + " == ?";

    /**
     * Query for hub station by name from hub stations table
     **/
    public static final String QUERY_HUB_BY_NAME = "SELECT * FROM " + TABLE_HUB_STATIONS
            + " WHERE " + COLUMN_HUB_STATIONS_NAME + " == ?";

    /**
     * Update String for main station fuel level by Id
     **/
    public static final String UPDATE_MAIN_FUEL_LEVELS = "UPDATE " + TABLE_MAIN_STATIONS
            + " SET "+ COLUMN_MAIN_STATIONS_FUEL + " = ? "
            + " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ?";

    /**
     * Update String for hub station fuel level by Id
     **/
    public static final String UPDATE_HUB_FUEL_LEVELS = "UPDATE " + TABLE_HUB_STATIONS
            + " SET " + COLUMN_HUB_STATIONS_FUEL + " = ? "
            + " WHERE " + COLUMN_HUB_STATIONS_ID + " == ?";

    /**
     * Update string for main station water level by Id
     **/
    public static final String UPDATE_MAIN_WATER_LEVELS = "UPDATE " + TABLE_MAIN_STATIONS
            + " SET "+ COLUMN_MAIN_STATIONS_WATER + " = ? "
            + " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ?";

    /**
     * Update String for hub station water level by Id
     **/
    public static final String UPDATE_HUB_WATER_LEVELS = "UPDATE " + TABLE_HUB_STATIONS
            + " SET " + COLUMN_HUB_STATIONS_WATER + " = ? "
            + " WHERE " + COLUMN_HUB_STATIONS_ID + " == ?";

    /**
     * Update string for station status by Id
     **/
    public static final String UPDATE_STATION_STATUS = "UPDATE " + TABLE_STATIONS
            + " SET " + COLUMN_STATIONS_STATUS + " = ? "
            + " WHERE " + COLUMN_STATIONS_ID + " == ?";

    /**
     * Update String for main station status by Id
     **/
    public static final String UPDATE_MAIN_STATUS = "UPDATE " + TABLE_MAIN_STATIONS
            + " SET " + COLUMN_MAIN_STATIONS_STATUS + " = ? "
            + " WHERE " + COLUMN_MAIN_STATIONS_ID + " == ?";

    /**
     * Update String for hub station status by Id
     **/
    public static final String UPDATE_HUB_STATUS = "UPDATE " + TABLE_HUB_STATIONS
            + " SET " + COLUMN_HUB_STATIONS_STATUS + " = ? "
            + " WHERE " + COLUMN_HUB_STATIONS_ID + " == ?";

    /**
     * Connection object for connection to database
     **/
    private Connection conn;

    /**
     * Prepared statement for query Station by Id
     **/
    private PreparedStatement queryStationID;
    /**
     * Prepared statement for query Main Station by Id
     **/
    private PreparedStatement queryMainStationID;
    /**
     * Prepared statement for query Hub Station Id
     **/
    private PreparedStatement queryHubStationID;
    /**
     * Prepared statement for query Station by name
     **/
    private PreparedStatement queryStationName;
    /**
     * Prepared statement for query Main Station by name
     **/
    private PreparedStatement queryMainStationName;
    /**
     * Prepared statement for query Hub Station by name
     **/
    private PreparedStatement queryHubStationName;
    /**
     * Prepared statement for Update Main Station fuel level
     **/
    private PreparedStatement updateMainFuelLevels;
    /**
     * Prepared statement for Update Hub Station fuel level
     **/
    private PreparedStatement updateHubFuelLevels;
    /**
     * Prepared statement for Update Main Water level
     **/
    private PreparedStatement updateMainWaterLevels;
    /**
     * Prepared statement for Update Hub Water level
     **/
    private PreparedStatement updateHubWaterLevels;

    /**
     * Returns true if database connection can be established and prepared statements can be precompiled
     * @return boolean
     * @exception SQL Exception
     **/
    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryStationID = conn.prepareStatement(QUERY_STATION_BY_ID);
            queryMainStationID = conn.prepareStatement(QUERY_MAIN_BY_ID);
            queryHubStationID = conn.prepareStatement(QUERY_HUB_BY_ID);
            queryStationName = conn.prepareStatement(QUERY_STATION_BY_NAME);
            queryMainStationName = conn.prepareStatement(QUERY_MAIN_BY_NAME);
            queryHubStationName = conn.prepareStatement(QUERY_HUB_BY_NAME);
            updateMainFuelLevels = conn.prepareStatement(UPDATE_MAIN_FUEL_LEVELS);
            updateHubFuelLevels = conn.prepareStatement(UPDATE_HUB_FUEL_LEVELS);
            updateMainWaterLevels = conn.prepareStatement(UPDATE_MAIN_WATER_LEVELS);
            updateHubWaterLevels = conn.prepareStatement(UPDATE_HUB_WATER_LEVELS);

            return true;
        } catch(SQLException se){
            System.out.println(se.getMessage());
        }

        return false;
    }

    /**
     * Returns true if all prepared statements and the connection can be closed
     * @return boolean
     * @exception SQL Exception
     **/
    public boolean close(){
        try{
            if(updateHubWaterLevels != null){
                updateHubWaterLevels.close();
            }
            if(updateMainWaterLevels != null){
                updateMainWaterLevels.close();
            }
            if(updateHubFuelLevels != null){
                updateHubFuelLevels.close();
            }
            if(updateMainFuelLevels != null){
                updateMainFuelLevels.close();
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

    /**
     * Returns Station ID if station exists, else returns -1
     * @param  String name
     * @return int
     **/
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

    /**
     * Returns Station name if station exists, else returns null
     * @param id
     * @return String name
     **/
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

    /**
     * Returns Station status "OPEN" or "CLOSED" by station ID
     * @param id
     * @return String
     */
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

    /**
     * Returns Station status "OPEN" or "CLOSED" by station name
     * @param name
     * @return
     */
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

    /**
     * Returns Main Station ID by name
     * @param name
     * @return int
     */
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

    /**
     * Return Main Station name by ID if exists, else returns "NONE"
     * @param id
     * @return String
     */
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

    /**
     * Return Main Station status "OPEN" or "CLOSED" by ID
     * @param id
     * @return String
     */
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

    /**
     * Returns Main Station status "OPEN" or "CLOSED" by name
     * @param name
     * @return String
     */
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

    /**
     * Returns Main station fuel level by id if exists, otherwise -1
     * @param id
     * @return double
     */
    public double getMainStationFuelLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.getFuelLevel();
            }
        } catch(Exception e){
            System.out.println("Error retrieving main station fuel level: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Returns Main Station water level if station exists, otherwise returns -1
     * @param id
     * @return double
     */
    public double getMainStationWaterLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                return mainStation.getWaterLevel();
            }
        } catch(Exception e){
            System.out.println("Error retrieving main station fuel level: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Returns Hub station ID by name if exists, else returns -1
     * @param name
     * @return int
     */
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

    /**
     * Returns hub station name by id if exists, else returns "NONE"
     * @param id
     * @return String
     */
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

    /**
     * Returns Hub Station status by ID if station exists, else returns null
     * @param id
     * @return String
     */
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

    /**
     * Returns Hub Station status by name if station exists, else returns null
     * @param name
     * @return String
     **/
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

    /**
     * Returns Hub station fuel level by ID if station exists, else returns -1
     * @param id
     * @return double
     **/
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

    /**
     * Returns Hub station water level by ID if station exists, else returns -1
     * @param id
     * @return double
     **/
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

    /**
     * Returns true if able to lower fuel amount for Main Station if exists, else returns false
     * @param id
     * @param amount
     * @return boolean
     **/
    public boolean lowerMainFuelLevel(int id, double amount){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                if(mainStation.lowerFuelLevel(amount)){
                    updateMainFuelLevels.setDouble(1, mainStation.getFuelLevel());
                    updateMainFuelLevels.setInt(2, id);
                    int rows = updateMainFuelLevels.executeUpdate();
                    return rows == 1;
                }
            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to lower Main station water level, else returns false
     * @param id
     * @param amount
     * @return boolean
     */
    public boolean lowerMainWaterLevel(int id, double amount){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                if(mainStation.lowerWaterLevel(amount)){
                    updateMainWaterLevels.setDouble(1, mainStation.getWaterLevel());
                    updateMainWaterLevels.setInt(2, id);
                    int rows = updateMainWaterLevels.executeUpdate();
                    return rows == 1;
                }
            }
        } catch(SQLException se){
            System.out.println("Error Updating main Water level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main Water level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to lower Hub station fuel level, else returns false
     * @param id
     * @param amount
     * @return boolean
     */
    public boolean lowerHubFuelLevel(int id, double amount){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                if(hubStation.lowerFuelLevel(amount)){
                    updateHubFuelLevels.setDouble(1, hubStation.getFuelLevel());
                    updateHubFuelLevels.setInt(2, id);
                    int temp = updateHubFuelLevels.executeUpdate();
                    return temp == 1;
                }
            }
        } catch(SQLException se){
            System.out.println("Error Updating hub fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering hub fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to lower Hub station water level, else returns false
     * @param id
     * @param amount
     * @return boolean
     */
    public boolean lowerHubWaterLevel(int id, double amount){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                if(hubStation.lowerWaterLevel(amount)){
                    updateHubWaterLevels.setDouble(1, hubStation.getWaterLevel());
                    updateHubWaterLevels.setInt(2, id);
                    int temp = updateHubWaterLevels.executeUpdate();
                    return temp == 1;
                }
            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to reset Main station fuel level by id, else returns false
     * @param id
     * @return boolean
     **/
    public boolean resetMainFuelLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                mainStation.resetFuel();
                updateMainFuelLevels.setDouble(1, mainStation.getFuelLevel());
                updateMainFuelLevels.setInt(2, id);
                int temp = updateMainFuelLevels.executeUpdate();
                return temp == 1;

            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to reset Main station water level by Id, else returns false
     * @param id
     * @return boolean
     **/
    public boolean resetMainWaterLevel(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                mainStation.resetWater();
                updateMainWaterLevels.setDouble(1, mainStation.getWaterLevel());
                updateMainWaterLevels.setInt(2, id);
                int temp = updateMainWaterLevels.executeUpdate();
                return temp == 1;

            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to reset Hub station fuel level by Id, else returns false
     * @param id
     * @return boolean
     */
    public boolean resetHubFuelLevel(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                hubStation.resetFuel();
                updateHubFuelLevels.setDouble(1, hubStation.getFuelLevel());
                updateHubFuelLevels.setInt(2, id);
                int temp = updateHubFuelLevels.executeUpdate();
                return temp == 1;

            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns true if able to reset Hub station water level by Id, else returns false
     * @param id
     * @return boolean
     */
    public boolean resetHubWaterLevel(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                hubStation.resetWater();
                updateHubWaterLevels.setDouble(1, hubStation.getWaterLevel());
                updateHubWaterLevels.setInt(2, id);
                int temp = updateHubWaterLevels.executeUpdate();
                return temp == 1;

            }
        } catch(SQLException se){
            System.out.println("Error Updating main fuel level: " + se.getMessage());
        }catch(Exception e){
            System.out.println("Error lowering main fuel level: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns string indicator for fuel status "EMPTY", "MEDIUM", or "FULL" by Id, else returns null
     * @param id
     * @return String
     **/
    public String getMainStationFuelStatus(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                if(mainStation.needsFuel()){
                    return STATION_LEVEL_STATUS.EMPTY.toString();
                } else {
                    if(mainStation.getFuelLevel() >= MainStation.FUEL_AMOUNT/2){
                        return STATION_LEVEL_STATUS.FULL.toString();
                    } else {
                        return STATION_LEVEL_STATUS.MEDIUM.toString();
                    }
                }
            }
        } catch(Exception e){
            System.out.println("Failed to ger Main Station Fuel Status: " + e.getMessage());
        }

        return null;
    }

    /**
     * Returns string indicating main station water level "EMPTY", "MEDIUM", "FULL" by Id, else return null
     * @param id
     * @return String
     **/
    public String getMainStationWaterStatus(int id){
        try{
            MainStation mainStation = getMainStation(id);
            if(mainStation != null){
                if(mainStation.needsWater()){
                    return STATION_LEVEL_STATUS.EMPTY.toString();
                } else {
                    if(mainStation.getWaterLevel() >= MainStation.WATER_AMOUNT/2){
                        return STATION_LEVEL_STATUS.FULL.toString();
                    } else {
                        return STATION_LEVEL_STATUS.MEDIUM.toString();
                    }
                }
            }
        } catch(Exception e){
            System.out.println("Failed to ger Main Station Fuel Status: " + e.getMessage());
        }

        return null;
    }

    public String getHubStationFuelStatus( int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                if(hubStation.needsFuel()){
                    return STATION_LEVEL_STATUS.EMPTY.toString();
                } else {
                    if(hubStation.getFuelLevel() >= HubStation.FUEL_AMOUNT /2){
                        return STATION_LEVEL_STATUS.FULL.toString();
                    } else {
                        return STATION_LEVEL_STATUS.MEDIUM.toString();
                    }
                }
            }
        } catch(Exception e){
            System.out.println("Failed to ger Main Station Fuel Status: " + e.getMessage());
        }

        return null;
    }

    public String getHubStationWaterStatus(int id){
        try{
            HubStation hubStation = getHubStation(id);
            if(hubStation != null){
                if(hubStation.needsWater()){
                    return STATION_LEVEL_STATUS.EMPTY.toString();
                } else {
                    if(hubStation.getWaterLevel() >= HubStation.WATER_AMOUNT /2){
                        return STATION_LEVEL_STATUS.FULL.toString();
                    } else {
                        return STATION_LEVEL_STATUS.MEDIUM.toString();
                    }
                }
            }
        } catch(Exception e){
            System.out.println("Failed to ger Main Station Fuel Status: " + e.getMessage());
        }

        return null;
    }

    public boolean updateStationStatus(int id, String newStatus){
        //TODO: Method gets Station and updates the status.
        return false;
    }

    public boolean updateMainStationStatus(int id, String newStatus){
        //TODO: Method gets Main Station and updates the status.
        return false;
    }

    public boolean updateHubStationStatus(int id, String newStatus){
        //TODO: Method gets Hub Station and updates the status.
        return false;
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

//    private StringBuilder setOrderBy(int orderBy, String query){
//        StringBuilder stringBuilder = new StringBuilder(query);
//
//        switch(orderBy){
//            case ORDER_BY_ASC:  stringBuilder.append(" ORDER BY ");
//                stringBuilder.append(COLUMN_STATIONS_NAME);
//                stringBuilder.append(" ASC");
//                break;
//            case ORDER_BY_DESC: stringBuilder.append(" ORDER BY ");
//                stringBuilder.append(COLUMN_STATIONS_NAME);
//                stringBuilder.append(" DESC");
//                break;
//            default:  stringBuilder.append(" ORDER BY ");
//                stringBuilder.append(COLUMN_STATIONS_ID);
//        }
//
//        return stringBuilder;
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
                for(HubStation station: hubStations){
                    System.out.printf("%d: %s \t %s\n",
                            station.getStationID(),
                            station.toString(),
                            station.getStationType());
                }
            }


        System.out.println("=========================================================================");
            stationManager.close();
    }

//    private Station createStation(String stationName){
//        // used to create a station
//        //TODO: creates new station in database
//    }
}
