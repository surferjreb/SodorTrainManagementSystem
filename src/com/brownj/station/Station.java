/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.station;

import java.util.ArrayList;
import java.util.function.Function;

public class Station {
    enum STATION_STATUS {OPEN, CLOSED};

    private int stationID;
    private String stationName;
    private String status;

    protected int getStationID(){
        return this.stationID;
    }

    protected String getStationStatus(){
        return this.status;
    }

    protected void setStationID(int id){
        this.stationID = id;
    }

    protected void setStationName(String name){
        this.stationName = name;
    }

    protected void setStationStatus(String status){
        if(status.equals("OPEN")){
            this.status = STATION_STATUS.OPEN.toString();
        } else {
            this.status = STATION_STATUS.CLOSED.toString();
        }
    }

    @Override
    public String toString(){
        String s = "EMPTY";
        if(this.stationName != null) {
            s = new String(this.stationName);
        }

        return s;
    }

}//End class