package mariculture.fishery.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.fishery.Fishing;
import mariculture.api.util.Text;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEgg extends Item implements IItemRegistry {
    public ItemEgg() {
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (Fishing.fishHelper.isEgg(stack)) if (stack.stackTagCompound.getInteger("currentFertility") > 0) {
            list.add(stack.stackTagCompound.getInteger("currentFertility") + " " + Text.translate("eggsRemaining"));
        } else {
            list.add(Text.translate("undetermined") + " " + Text.translate("eggsRemaining"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + "fish/egg");
    }

    @Override
    public void register(Item item) {
        MaricultureRegistry.register(getName(null), new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public int getMetaCount() {
        return 0;
    }

    @Override
    public String getName(ItemStack stack) {
        return "fishEggs";
    }
}
