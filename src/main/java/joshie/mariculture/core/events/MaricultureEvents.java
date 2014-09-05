package joshie.mariculture.core.events;

import joshie.mariculture.core.events.BlockEvent.BlockBroken;
import joshie.mariculture.core.events.BlockEvent.GetHardness;
import joshie.mariculture.core.events.BlockEvent.GetInventoryIIcon;
import joshie.mariculture.core.events.BlockEvent.GetIsActive;
import joshie.mariculture.core.events.BlockEvent.GetIsValidTab;
import joshie.mariculture.core.events.BlockEvent.GetTileEntity;
import joshie.mariculture.core.events.BlockEvent.GetToolLevel;
import joshie.mariculture.core.events.BlockEvent.GetToolType;
import joshie.mariculture.core.events.BlockEvent.GetWorldIIcon;
import joshie.mariculture.core.events.BlockEvent.TilePlaced;
import joshie.mariculture.core.events.ItemEvent.GetItemName;
import joshie.mariculture.core.events.ItemEvent.GetModName;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import joshie.mariculture.plugins.Plugins.Plugin.Stage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaricultureEvents {
    public static boolean isActive(Block block, int meta, boolean isActive) {
        GetIsActive event = new GetIsActive(block, meta, isActive);
        MinecraftForge.EVENT_BUS.post(event);
        return event.isActive;
    }

    public static boolean isValidTab(Block block, CreativeTabs tab, int meta, boolean isValid) {
        GetIsValidTab event = new GetIsValidTab(block, meta, tab, isValid);
        MinecraftForge.EVENT_BUS.post(event);
        return event.isValid;
    }

    public static String getToolType(Block block, int meta, String tooltype) {
        GetToolType event = new GetToolType(block, meta, tooltype);
        MinecraftForge.EVENT_BUS.post(event);
        return event.tooltype;
    }

    public static int getToolLevel(Block block, int meta, int level) {
        GetToolLevel event = new GetToolLevel(block, meta, level);
        MinecraftForge.EVENT_BUS.post(event);
        return event.level;
    }

    public static float getBlockHardness(Block block, int meta, float hardness) {
        GetHardness event = new GetHardness(block, meta, hardness);
        MinecraftForge.EVENT_BUS.post(event);
        return event.hardness;
    }

    public static TileEntity getTileEntity(Block block, int meta, TileEntity tile) {
        GetTileEntity event = new GetTileEntity(block, meta, tile);
        MinecraftForge.EVENT_BUS.post(event);
        return event.tile;
    }

    public static void onBlockPlaced(ItemStack stack, Block block, World world, int x, int y, int z, EntityLivingBase entity, TileEntity tile) {
        int direction = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        TilePlaced event = new TilePlaced(stack, block, entity, tile, direction);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static boolean onBlockBroken(Block block, int meta, World world, int x, int y, int z) {
        BlockBroken event = new BlockBroken(block, world, x, y, z, meta);
        return MinecraftForge.EVENT_BUS.post(event);
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getInventoryIcon(Block block, int meta, int side, IIcon icon) {
        GetInventoryIIcon event = new GetInventoryIIcon(block, meta, side, icon);
        MinecraftForge.EVENT_BUS.post(event);
        return event.icon;
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getWorldIcon(Block block, int side, IIcon icon, IBlockAccess world, int x, int y, int z) {
        GetWorldIIcon event = new GetWorldIIcon(block, side, icon, world, x, y, z);
        MinecraftForge.EVENT_BUS.post(event);
        return event.icon;
    }

    public static void onConfigure(String clazz, Configuration config) {
        ConfigEvent event = new ConfigEvent(clazz, config);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onRegistration(RegistrationModule module, Stage stage) {
        RegistryEvent event = new RegistryEvent(module, stage);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @SideOnly(Side.CLIENT)
    public static void render(ItemStack item, ItemRenderType type) {
        RenderEvent event = new RenderEvent(item, type);
        MinecraftForge.EVENT_BUS.post(event);
    }

    /** Localisation based helpers **/
    public static String getMod(Block block, int meta, String mod) {
        return getMod(Item.getItemFromBlock(block), meta, mod);
    }

    public static String getMod(Item item, int meta, String mod) {
        GetModName event = new GetModName(item, meta, mod);
        MinecraftForge.EVENT_BUS.post(event);
        return event.mod;
    }
    
    public static String getItemName(Block block, int meta, String name) {
        return getItemName(Item.getItemFromBlock(block), meta, name);
    }
    
    public static String getItemName(Item item, int meta, String name) {
        GetItemName event = new GetItemName(item, meta, name);
        MinecraftForge.EVENT_BUS.post(event);
        return event.name;
    }
}
