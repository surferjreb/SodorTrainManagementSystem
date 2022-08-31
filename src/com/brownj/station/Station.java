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
    private boolean hubStation;
    private boolean mainStation;

    protected int getStationID(){
        return this.stationID;
    }

    protected String getStationStatus(){
        return this.status;
    }

    protected boolean isHubStation(){
        return hubStation;
    }

    protected boolean isMainStation(){
        return mainStation;
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

    protected void setHubStation(String value){
        this.hubStation = value.equals("true");
    }
    protected void setMainStation(String value){
        this.mainStation = value.equals("true");
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