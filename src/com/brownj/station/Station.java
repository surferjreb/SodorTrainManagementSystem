/**
 *  @author James R. Brown
 *  Station class for building object from table
 */

package com.brownj.station;

import java.util.ArrayList;
import java.util.function.Function;

public class Station {
    /**
     * Enum for station status
     **/
    enum STATION_STATUS {OPEN, CLOSED};

    /**
     * Enum for station type
     **/
    enum STATION_TYPE {STATION, MAIN_STATION, HUB_STATION};

    /**
     * Station id number
     **/
    private int stationID;
    /**
     * Station Name
     **/
    private String stationName;
    /**
     * Station status i.e- OPEN, CLOSED
     **/
    private String status;
    /**
     * Station Type
     **/
    protected String stationType;

    /**
     * Constructor is used to establish type of station
     */
    public Station(){
        this.stationType = STATION_TYPE.STATION.toString();
    }

    /**
     * Returns station id
     * @return int
     **/
    protected int getStationID(){
        return this.stationID;
    }

    /**
     * Returns station name
     * @return String
     **/
    protected String getStationStatus(){
        return this.status;
    }

    protected String getStationType() {
        return this.stationType;
    }

    /**
     * Sets the station id number
     * @param id
     **/
    protected void setStationID(int id){
        this.stationID = id;
    }

    /**
     * Sets station name
     * @param name
     */
    protected void setStationName(String name){
        this.stationName = name;
    }

    /**
     * Sets station status
     * @param status
     */
    protected void setStationStatus(String status){
        if(status.equals("OPEN")){
            this.status = STATION_STATUS.OPEN.toString();
        } else {
            this.status = STATION_STATUS.CLOSED.toString();
        }
    }

    /**
     * Returns station name
     * @return String
     */
    @Override
    public String toString(){
        String s = "EMPTY";
        if(this.stationName != null) {
            s = new String(this.stationName);
        }

        return s;
    }

}//End class