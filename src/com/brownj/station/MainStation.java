/**
 *  @author James R. Brown
 *  The MainStation class extends Station class.  This adds fuel and water parameters for refueling trains.
 */

package com.brownj.station;

public class MainStation extends Station {
    /**
     *  Default fuel level a Main Station will have
     */
    protected static final double FUEL_AMOUNT = 20000d;
    /**
     * Default water level for a main station
     */
    protected static final double WATER_AMOUNT = 50000d;
    /**
     * Variable for fuel level.
     */
    private double fuelLevel;
    /**
     * Variable for water level.
     */
    private double waterLevel;

    public MainStation(){
        this.stationType = STATION_TYPE.MAIN_STATION.toString();
    }

    /**
     * Returns fuel level
     * @return double
     */
    protected double getFuelLevel() {
        return fuelLevel;
    }

    /**
     * Returns water level
     * @return double
     */
    protected double getWaterLevel() {
        return waterLevel;
    }

    /**
     * Sets the fuel level for station
     * @param double fuelLevel
     */
    protected void setFuelLevel(double fuelLevel){
        this.fuelLevel = fuelLevel;
    }

    /**
     * Sets the water level for a station
     * @param double waterLevel
     */
    protected void setWaterLevel(double waterLevel){
        this.waterLevel = waterLevel;
    }

    /**
     * Lowers the fuel level at station if parameter is > 0.
     * @param double fuelUsed
     * @return boolean
     */
    protected boolean lowerFuelLevel(double fuelUsed) {
        if (fuelUsed < fuelLevel) {
            this.fuelLevel -= fuelUsed;
            return true;
        }

        return false;
    }

    /**
     * Lowers the water level of station if parameter is > 0.
     * @param double waterUsed
     * @return boolean
     */
    protected boolean lowerWaterLevel(double waterUsed) {
        if (waterUsed < waterLevel) {
            this.waterLevel -= waterUsed;
            return true;
        }

        return false;
    }

    /**
     *  Sets water level to WATER_AMOUNT
     **/
    protected void resetWater() {
        this.waterLevel = WATER_AMOUNT;
    }

    /**
     *  Sets fuel level to FUEL_AMOUNT
     **/
    protected void resetFuel() {
        this.fuelLevel = FUEL_AMOUNT;
    }

    /**
     * Returns a boolean if fuel level is less than 25% of FUEL_AMOUNT
     * @return boolean
     **/
    protected boolean needsFuel() {
        return fuelLevel <= (FUEL_AMOUNT / 4);
    }

    /**
     * Returns boolean if water level is less than 25% of WATER_AMOUNT
     * @return boolean
     **/
    protected boolean needsWater() {
        return waterLevel <= (WATER_AMOUNT / 4);
    }

}