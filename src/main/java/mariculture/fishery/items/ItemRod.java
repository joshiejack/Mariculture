package mariculture.fishery.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Modules;
import mariculture.lib.util.Text;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRod extends ItemFishingRod {
    protected String mod;
    protected String path;
    private ItemStack repair;
    private int enchant;

    public ItemRod() {
        this("mariculture", 127, 1);
        setCreativeTab(MaricultureTab.tabFishery);
    }

    public ItemRod(String mod, int max, int enchant) {
        this.enchant = enchant;
        this.mod = mod;
        setMaxStackSize(1);
        setCreativeTab(MaricultureTab.tabFishery);
        if (max > 0) {
            setMaxDamage(max);
        }
    }

    @Override
    public Item setCreativeTab(CreativeTabs tab) {
        if (tab == null) {
            MaricultureTab.tabCore = new MaricultureTab("mariculture.core");
            MaricultureTab.tabFactory = new MaricultureTab("mariculture.machines");
            MaricultureTab.tabFishery = new MaricultureTab("mariculture.fishing");
            MaricultureTab.tabWorld = new MaricultureTab("mariculture.world");
            MaricultureTab.tabMagic = new MaricultureTab("mariculture.magic");
            tab = MaricultureTab.tabFishery;
        }

        super.setCreativeTab(tab);
        return this;
    }

    public void setRepairMaterial(ItemStack stack) {
        this.repair = stack;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() instanceof IEnergyContainerItem) return false;
        ItemStack toRepairWith = repair != null ? repair : new ItemStack(Items.stick);
        return OreDicHelper.convert(stack2).equals(OreDicHelper.convert(toRepairWith));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!Modules.isActive(Modules.fishery)) return super.onItemRightClick(stack, world, player);
        return Fishing.fishing.handleRightClick(stack, world, player);
    }

    @Override
    public int getItemEnchantability() {
        return enchant;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public Item setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        if (!(this instanceof ItemVanillaRod)) {
            GameRegistry.registerItem(this, name.replace(".", "_"));
        }
        
        return this;
    }

    @Override
    public String getUnlocalizedName() {
        return mod + "." + super.getUnlocalizedName().replace("item.", "").replace("_", ".");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return Text.localize(getUnlocalizedName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String path = this.path != null ? this.path : mod + ":";
        String name = super.getUnlocalizedName().replace("item.", "").toLowerCase();
        itemIcon = iconRegister.registerIcon(path + Text.removeDecimals(name));
    }
}
