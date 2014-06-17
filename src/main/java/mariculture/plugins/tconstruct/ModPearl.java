package mariculture.plugins.tconstruct;

import java.util.Arrays;
import java.util.List;

import mariculture.api.util.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;

public class ModPearl extends ToolMod {
    String tooltipName;
    int increase;
    int max;

    public ModPearl(ItemStack[] items, int effect, int inc) {
        super(items, effect, "Pearls");
        tooltipName = Text.AQUA + "Aquatic";
        increase = inc;
        max = 50;
    }

    @Override
    protected boolean canModify(ItemStack tool, ItemStack[] input) {
        if (tool.getItem() instanceof ToolCore) {
            ToolCore toolItem = (ToolCore) tool.getItem();
            if (!validType(toolItem)) return false;
            NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
            if (!tags.hasKey(key)) return tags.getInteger("Modifiers") > 0;
            int keyPair[] = tags.getIntArray(key);
            if (keyPair[0] + increase <= keyPair[1]) return true;
            else if (keyPair[0] == keyPair[1]) return tags.getInteger("Modifiers") > 0;
            else return false;
        } else return false;
    }

    @Override
    public void modify(ItemStack[] input, ItemStack tool) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        int[] keyPair;
        if (tags.hasKey(key)) {
            keyPair = tags.getIntArray(key);
            if (keyPair[0] % max == 0) {
                keyPair[0] += increase;
                keyPair[1] += max;
                tags.setIntArray(key, keyPair);

                int modifiers = tags.getInteger("Modifiers");
                modifiers -= 1;
                tags.setInteger("Modifiers", modifiers);
            } else {
                keyPair[0] += increase;
                tags.setIntArray(key, keyPair);
            }
            updateModTag(tool, keyPair);
        } else {
            int modifiers = tags.getInteger("Modifiers");
            modifiers -= 1;
            tags.setInteger("Modifiers", modifiers);
            String modName = Text.AQUA + "Pearls (" + increase + "/" + max + ")";
            int tooltipIndex = addToolTip(tool, tooltipName, modName);
            keyPair = new int[] { increase, max, tooltipIndex };
            tags.setIntArray(key, keyPair);
        }
    }

    void updateModTag(ItemStack tool, int[] keys) {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        String tip = "ModifierTip" + keys[2];
        String modName = Text.AQUA + "Pearls (" + keys[0] + "/" + keys[1] + ")";
        tags.setString(tip, modName);
    }

    @Override
    public boolean validType(ToolCore tool) {
        List list = Arrays.asList(tool.toolCategories());
        return list.contains("harvest");
    }
}