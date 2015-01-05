package mariculture.api.core;


public interface IUpgradeHandler {
    /** Returns the Storage, Purity or Heat value total of the upgradable tile, 
     * Use "storage" or "purity" or "temp", or "speed", or "rf", or "salinity" for the string type
     */
    public int getData(String type, IUpgradable tile);

    /** Whether this upgradable block has at least one of this type of upgrade or not **/
    public boolean hasUpgrade(String type, IUpgradable tile);
}
