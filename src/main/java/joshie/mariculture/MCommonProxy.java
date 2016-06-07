package joshie.mariculture;

import joshie.mariculture.modules.ModuleManager;

public class MCommonProxy {
    public void load(String stage) {
        //Continue
        for (Class c : ModuleManager.enabled.values()) {
            try { //Attempt to load, and display errors if it fails
                c.getMethod(stage).invoke(null);
            } catch (NoSuchMethodException nsme) {}
            catch (Exception e) {
                e.printStackTrace();
            }

            //Attempt to load client side only
            if (isClient()) {
                try { //Attempt to load client, and display errors if it fails
                    c.getMethod(stage + "Client").invoke(null);
                } catch (NoSuchMethodException nsme) {}
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isClient() {
        return false;
    }
}