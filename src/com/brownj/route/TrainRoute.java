//package com.brownj.route;
//
//
//import com.brownj.station.Station;
//
//import java.util.*;
//
//public class TrainRoute {
//    private ArrayList<Route> trainRoutes;
//    private String routeName;
//    protected double routeDistance;
//
//    public TrainRoute(){
//        TrainRoutes = new ArrayList<>();
//
//    }//constructor
//
//    public ArrayList<String> getTrainRoutes() {
//        return TrainRoutes;
//    }
//
//    public int getLength(){
//        return TrainRoutes.size();
//    }
//
//    public List<String> routeStationStatus(String routeName, String stationName){
//        List<String> temp = new ArrayList<>();
//
//        try{
//
//
//        } catch(Exception e) {
//            System.out.println("Route does not exist: " + e.getMessage());
//        }
//    }
//
//    private String getRouteStationStatus(Route route, String stationName){
//        try {
//            for(Station station: route.getStops()){
//                if(station.toString().equalsIgnoreCase(stationName)){
//                    return station.getStationStatus(stationName);
//                }
//            }
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }
//
//    private List<Station> getRouteStations(String routeName){
//        try {
//            for(Route route: trainRoutes){
//                if(route.toString().equalsIgnoreCase(routeName)){
//                    return route.getStops();
//                }
//            }
//
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }
//
//    private Route getRoute(String routeName){
//        try {
//            for (Route route : trainRoutes) {
//                if (route.toString().equalsIgnoreCase(routeName)) {
//                    return route;
//                }
//            }
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }
//
//}//End of Class
