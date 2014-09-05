package joshie.maritech.extensions.items;

import joshie.maritech.util.IItemExtension;

public class ExtensionItemsBase implements IItemExtension {
    @Override
    public String getName(int meta, String name) {
        return name;
    }

    @Override
    public String getMod(int meta, String mod) {
        return mod;
    }
}
