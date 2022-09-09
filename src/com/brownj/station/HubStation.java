/**
 *  @author James R. Brown
 *  This class represents the Transfer stations.
 *  They carry a larger water and fuel depot.
 *
 */

package com.brownj.station;

public class HubStation extends MainStation{

    /**
     * Overrides Main Station FUEL_AMOUNT.
     */
    protected static final double FUEL_AMOUNT = 50000d;
    /**
     *  Overrides Main Station WATER_AMOUNT.
     **/
    protected static final double WATER_AMOUNT = 100000d;

    public HubStation(){
        this.stationType = STATION_TYPE.HUB_STATION.toString();
    }
    //Possibly expand to include connecting lines

}
