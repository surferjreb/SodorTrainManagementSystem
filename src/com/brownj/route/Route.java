/**
 *  Created by: James R. Brown
 *  Sodor train project
 */

package com.brownj.route;

import com.brownj.station.Station;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final String name;
    private List<Station> stops;

    public Route(String name){
        this.name = name;
        this.stops = new ArrayList<>();
    }

    public Route(String name, List<Station> stops){
        this(name);
        this.stops = new ArrayList<>(stops);
    }

    public String getName() {
        return name;
    }

    public List<Station> getStops() {
        return new ArrayList<>(stops);
    }

    public void setStops(List<Station> stops) {
        this.stops = stops;
    }

}
