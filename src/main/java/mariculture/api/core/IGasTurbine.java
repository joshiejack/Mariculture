package mariculture.api.core;

public interface IGasTurbine {
    /** Add a liquid to be accepted by the gas turbine
     * 
     * @param Fluid Name e.g. water
     */
    public void add(String name);
}
