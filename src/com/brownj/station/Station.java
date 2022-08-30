/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.station;

import java.util.ArrayList;
import java.util.function.Function;

public class Station {
    enum STATION_STATUS {OPEN, CLOSED};

    private final String stationName;
    private boolean status;

    protected Station(String stationName){
        this.stationName = stationName;
        this.status = false;
    }

    protected void openStation(){
         this.status = true;
    }

    protected void closeStation(){
         this.status = false;
    }
    
    protected String getStationStatus(){
       if(status) return STATION_STATUS.OPEN.toString();

       return STATION_STATUS.CLOSED.toString();
    }

    protected boolean isHubStation(){
        return false;
    }

    @Override
    public String toString(){
        return new String(stationName.toUpperCase());
    }

}//End class