package com.brownj.trainschedule;
import com.brownj.station.Station;

import java.time.LocalTime;
import java.util.*;

public class TrainSchedule {
    private Map<Station, LocalTime> stops;

    public TrainSchedule(){
          stops = new HashMap<>();
    }

    public void setTrainSchedule(Station station, LocalTime time){
        if(station != null && time != null){
            stops.put(station, time);
        }
    }
    
    public Map<Station, LocalTime> getTrainSchedule(){
        if(!stops.isEmpty()){
            return stops;
        }

        return null;
    }

}
