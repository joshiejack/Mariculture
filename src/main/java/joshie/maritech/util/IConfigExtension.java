package joshie.maritech.util;

import net.minecraftforge.common.config.Configuration;

public interface IConfigExtension {
    public String getName();
    public void init(Configuration config);
}
