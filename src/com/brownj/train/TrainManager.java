package com.brownj.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainManager {
    public List<Train> trains;

    public TrainManager(){
        trains = new ArrayList<>();
    }

    public void setSchedule(){
        //TODO: set the train schedule for program start
    }

    public void setTrainRoutes(){
        //TODO: set the train routes
    }

    public void getTrain(){

    }

    public void getTrainRoute(){

    }

    public void getTrainSchedule(){

    }

    public void getTrainLocation(){
        //TODO: return the trains location
        // This information will have to be calculated for simulation
        // distanceTraveled = rate of speed(20 mph)r * (currentTime - timeLeft)
        // position = distanceOfNextStop - distanceTraveled

    }

}
