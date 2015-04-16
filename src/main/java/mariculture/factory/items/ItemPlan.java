package mariculture.factory.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.items.ItemMCDamageable;
import mariculture.core.lib.PlansMeta;
import mariculture.lib.util.Text;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPlan extends ItemMCDamageable {
    private IIcon[] icons;
    
    public ItemPlan() {
        super(64);
        setCreativeTab(MaricultureTab.tabFactory);
    }

    public int getStackSize(ItemStack stack) {
        switch (PlansMeta.getType(stack)) {
            case PlansMeta.FLOOR:
                return 9;
            case PlansMeta.BLOCK:
                return 6;
            case PlansMeta.STAIRS:
                return 4;
            case PlansMeta.SLABS:
                return 12;
            case PlansMeta.FENCE:
                return 6;
            case PlansMeta.GATE:
                return 2;
            case PlansMeta.WALL:
                return 6;
            case PlansMeta.LIGHT:
                return 6;
            case PlansMeta.RF:
                return 1;
            case PlansMeta.RF_WALL:
                return 1;
            default:
                return 1;
        }
    }

    public String getName(ItemStack stack) {
        if (stack.hasTagCompound()) {
            switch (PlansMeta.getType(stack)) {
                case PlansMeta.FLOOR:
                    return "plan_floor";
                case PlansMeta.BLOCK:
                    return "plan_block";
                case PlansMeta.STAIRS:
                    return "plan_stairs";
                case PlansMeta.SLABS:
                    return "plan_slabs";
                case PlansMeta.FENCE:
                    return "plan_fence";
                case PlansMeta.GATE:
                    return "plan_gate";
                case PlansMeta.WALL:
                    return "plan_wall";
                case PlansMeta.LIGHT:
                    return "plan_light";
                case PlansMeta.RF:
                    return "plan_redstone";
                case PlansMeta.RF_WALL:
                    return "plan_redstonewall";
                default:
                    return "unnamed";
            }
        } else return "unnamed";
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return icons[0];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (stack.hasTagCompound()) return icons[PlansMeta.getType(stack)];

        return icons[0];
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(getUnlocalizedName() + "." + name.replace("plan_", ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        for (int i = 0; i < PlansMeta.COUNT; ++i) {
            list.add(PlansMeta.setType(new ItemStack(item, 1, 0), i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[PlansMeta.COUNT];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(PlansMeta.setType(new ItemStack(this, 1, 0), i)));
        }
    }
}
