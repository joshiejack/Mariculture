package mariculture.core.gui;

import java.util.List;

import mariculture.Mariculture;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Text;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class GuiItemToolTip {
	@ForgeSubscribe
	public void addToolTip(ItemTooltipEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.itemStack;
		List list = event.toolTip;
		Container container = player.openContainer;
		
		if(container instanceof ContainerMachine) {
			TileEntity tile = (TileEntity) ((ContainerMachine) container).tile;
			Gui gui = (Gui) Mariculture.proxy.getClientGuiElement(-1, player, tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
			if(gui instanceof GuiMariculture)
				((GuiMariculture) gui).addItemToolTip(stack, list);
		} 
		
		if(Extra.DEBUG_ON) {
			String name = stack.getItem().getUnlocalizedName();
			if(name.length() > 5) {
				list.add(stack.getItem().getUnlocalizedName().substring(5));
			}
		}
		
		if(stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("OreDictionaryDisplay"))  {
                NBTTagCompound nbttagcompound = stack.stackTagCompound.getCompoundTag("OreDictionaryDisplay");
                if (nbttagcompound.hasKey("Lore")) {
                    NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore");
                    if (nbttaglist1.tagCount() > 0) {
                        for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
                        	String color = j == 0? Text.ORANGE: Text.GREY;
                            list.add(color + ((NBTTagString)nbttaglist1.tagAt(j)).data);
                        }
                    }
                }
            }
		}
	}
}
