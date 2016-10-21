package joshie.mariculture.api.diving;

public interface Diving {
    /** Call this to register a new listener for waterbreathing, this is used to
     *  check for waterbreathing items in specific slots
     * @param listener */
    void registerWaterbreathingListener(WaterBreathingChecker listener);

    /** Call this to register a new listerner for the air pump
     *  @param ticker   the ticker itself **/
    void registerAirPumpTicker(AirPumpTicker ticker);
}
