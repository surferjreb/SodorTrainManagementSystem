/**
 *  @author James R. Brown
 *  Stations class
 */

package com.brownj.station;

public class Station {
    /**
     * Enum for Station Type
     **/
    enum STATION_TYPE {STATION, MAIN_STATION, HUB_STATION}

    /**
     * Enum for Station status
     */
    enum STATION_STATUS {OPEN, CLOSED};
    /**
     * Integer value for station id
     */
    private int stationID;
    /**
     * String station name
     */
    private String stationName;
    /**
     * Boolean status for station status
     **/
    private boolean status;
    /**
     * String for station type
     */
    protected String stationType;

    /**
     * Default Constructor used for setting station type
     */
    public Station(){
        this.stationType = STATION_TYPE.STATION.toString();
    }

    /**
     * Returns station ID
     * @return int stationID
     */
    public int getStationID() {
        return stationID;
    }

    /**
     * Sets stationID
     * @param stationID
     */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /**
     *
     * @param stationName
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStatus() {
        if(status){
            return STATION_STATUS.OPEN.toString();
        }

        return STATION_STATUS.CLOSED.toString();
    }

    public void setStatus(String status) {
        this.status = status.equalsIgnoreCase(STATION_STATUS.OPEN.toString());
    }

    public String getStationType(){
        return this.stationType;
    }

    @Override
    public String toString(){
        return new String(this.stationName);
    }
}
