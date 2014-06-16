package enchiridion;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {
	public static CreativeTab books = new CreativeTab("enchiridion");
    public ItemStack icon = new ItemStack(Items.writable_book);
    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack () {
        return icon;
    }

	@Override
	public Item getTabIconItem() {
		return icon.getItem();
	}
}
