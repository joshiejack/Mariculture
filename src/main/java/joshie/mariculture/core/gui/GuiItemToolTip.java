package joshie.mariculture.core.gui;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.util.MCTranslate;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiItemToolTip {
    @SubscribeEvent
    public void addToolTip(ItemTooltipEvent event) {
        EntityPlayer player = event.entityPlayer;
        if (player != null) {
            ItemStack stack = event.itemStack;
            List list = event.toolTip;
            Container container = player.openContainer;
            if (container instanceof ContainerMachine) {
                TileEntity tile = (TileEntity) ((ContainerMachine) container).tile;
                Gui gui = (Gui) Mariculture.proxy.getClientGuiElement(-1, player, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
                if (gui instanceof GuiMariculture) {
                    ((GuiMariculture) gui).addItemToolTip(stack, list);
                }
            }

            if (stack.hasTagCompound()) if (stack.stackTagCompound.hasKey("OreDictionaryDisplay")) {
                NBTTagCompound nbttagcompound = stack.stackTagCompound.getCompoundTag("OreDictionaryDisplay");
                if (nbttagcompound.hasKey("Lore")) {
                    NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
                    if (nbttaglist1.tagCount() > 0) {
                        boolean labeled = false;
                        for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
                            String color = j == 0 ? joshie.lib.util.Text.ORANGE : joshie.lib.util.Text.GREY;
                            String name = nbttaglist1.getStringTagAt(j);
                            if (name.equals("")) {
                                if (!labeled) {
                                    labeled = true;
                                    list.add(joshie.lib.util.Text.RED + MCTranslate.translate("blacklisted"));
                                }
                            } else {
                                list.add(color + name);
                            }
                        }
                    }
                }
            }
        }
    }
}
