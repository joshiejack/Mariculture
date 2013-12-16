package mariculture.core;

import mariculture.core.blocks.TileBookshelf;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileSettler;
import mariculture.core.gui.ContainerBookshelf;
import mariculture.core.gui.ContainerLiquifier;
import mariculture.core.gui.ContainerOyster;
import mariculture.core.gui.ContainerSettler;
import mariculture.core.gui.ContainerSift;
import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.GuiBookshelf;
import mariculture.core.gui.GuiLiquifier;
import mariculture.core.gui.GuiOyster;
import mariculture.core.gui.GuiSettler;
import mariculture.core.gui.GuiStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemStorage;
import mariculture.core.lib.GuiIds;
import mariculture.factory.blocks.TileDictionary;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.factory.blocks.TilePressureVessel;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.blocks.TileSluice;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.gui.ContainerDictionary;
import mariculture.factory.gui.ContainerFLUDDStand;
import mariculture.factory.gui.ContainerFishSorter;
import mariculture.factory.gui.ContainerPressureVessel;
import mariculture.factory.gui.ContainerSawmill;
import mariculture.factory.gui.ContainerSluice;
import mariculture.factory.gui.ContainerTurbineGas;
import mariculture.factory.gui.ContainerTurbineWater;
import mariculture.factory.gui.GuiDictionary;
import mariculture.factory.gui.GuiFLUDDStand;
import mariculture.factory.gui.GuiFishSorter;
import mariculture.factory.gui.GuiPressureVessel;
import mariculture.factory.gui.GuiSawmill;
import mariculture.factory.gui.GuiSluice;
import mariculture.factory.gui.GuiTurbineGas;
import mariculture.factory.gui.GuiTurbineWater;
import mariculture.fishery.blocks.TileAutofisher;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.gui.ContainerAutofisher;
import mariculture.fishery.gui.ContainerFeeder;
import mariculture.fishery.gui.ContainerIncubator;
import mariculture.fishery.gui.GuiAutofisher;
import mariculture.fishery.gui.GuiFeeder;
import mariculture.fishery.gui.GuiIncubator;
import mariculture.fishery.gui.GuiSift;
import mariculture.magic.ItemMirror;
import mariculture.magic.gui.ContainerMirror;
import mariculture.magic.gui.GuiMirror;
import mariculture.magic.gui.InventoryMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id == GuiIds.MIRROR) {
			return new ContainerMirror(player.inventory, new InventoryMirror(ItemMirror.itemStack, player, world), world);
		} 
		
		if(id == GuiIds.FILTER && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
			ItemStorage storage = (ItemStorage) player.getCurrentEquippedItem().getItem();
			return new ContainerStorage(player.inventory, new InventoryStorage(player, storage.size), world);
		}
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			if(tile instanceof TileOyster) {
				return new ContainerOyster((TileOyster) tile, player.inventory);
			}
			
			if(tile instanceof TileAutofisher) {
				return new ContainerAutofisher((TileAutofisher) tile, player.inventory);
			}
			
			if(tile instanceof TileLiquifier) {
				return new ContainerLiquifier((TileLiquifier) tile, player.inventory);
			}
			
			if(tile instanceof TileSettler) {
				return new ContainerSettler((TileSettler) tile, player.inventory);
			}
			
			if(tile instanceof TileIncubator) {
				return new ContainerIncubator((TileIncubator) tile, player.inventory);
			}
			
			if(tile instanceof TileSift) {
				return new ContainerSift((TileSift) tile, player.inventory);
			}
			
			if(tile instanceof TileFeeder) {
				return new ContainerFeeder((TileFeeder) tile, player.inventory);
			}
			
			if(tile instanceof TileBookshelf) {
				return new ContainerBookshelf((TileBookshelf) tile, player.inventory);
			}
			
			if(tile instanceof TileSawmill) {
				return new ContainerSawmill((TileSawmill) tile, player.inventory);
			}
			
			if(tile instanceof TileTurbineWater) {
				return new ContainerTurbineWater((TileTurbineWater) tile, player.inventory);
			}
			
			if(tile instanceof TileTurbineGas) {
				return new ContainerTurbineGas((TileTurbineGas) tile, player.inventory);
			}
			
			if(tile instanceof TileFLUDDStand) {
				return new ContainerFLUDDStand((TileFLUDDStand) tile, player.inventory);
			}
			
			if(tile instanceof TilePressureVessel) {
				return new ContainerPressureVessel((TilePressureVessel) tile, player.inventory);
			}
			
			if(tile instanceof TileDictionary) {
				return new ContainerDictionary((TileDictionary) tile, player.inventory);
			}
			
			if(tile instanceof TileSluice) {
				return new ContainerSluice((TileSluice) tile, player.inventory);
			}
			
			if(tile instanceof TileFishSorter) {
				return new ContainerFishSorter((TileFishSorter)tile, player.inventory);
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == GuiIds.MIRROR) {
			return new GuiMirror(player.inventory, new InventoryMirror(ItemMirror.itemStack, player, world), world);
		} 
		
		if(id == GuiIds.FILTER && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
			ItemStorage storage = (ItemStorage) player.getCurrentEquippedItem().getItem();
			return new GuiStorage(player.inventory, new InventoryStorage(player, storage.size), world, storage.gui);
		}
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			if(tile instanceof TileOyster) {
				return new GuiOyster(player.inventory, (TileOyster) tile);
			}
			
			if(tile instanceof TileAutofisher) {
				return new GuiAutofisher(player.inventory, (TileAutofisher) tile);
			}
			
			if(tile instanceof TileLiquifier) {
				return new GuiLiquifier(player.inventory, (TileLiquifier) tile);
			}
			
			if(tile instanceof TileSettler) {
				return new GuiSettler(player.inventory, (TileSettler) tile);
			}
			
			if(tile instanceof TileIncubator) {
				return new GuiIncubator(player.inventory, (TileIncubator) tile);
			}
			
			if(tile instanceof TileSift) {
				return new GuiSift(player.inventory, (TileSift) tile);
			}
			
			if(tile instanceof TileFeeder) {
				return new GuiFeeder(player.inventory, (TileFeeder) tile);
			}
			
			if(tile instanceof TileBookshelf) {
				return new GuiBookshelf(player.inventory, (TileBookshelf) tile);
			}
			
			if(tile instanceof TileSawmill) {
				return new GuiSawmill(player.inventory, (TileSawmill) tile);
			}
			
			if(tile instanceof TileTurbineWater) {
				return new GuiTurbineWater(player.inventory, (TileTurbineWater) tile);
			}
			
			if(tile instanceof TileTurbineGas) {
				return new GuiTurbineGas(player.inventory, (TileTurbineGas) tile);
			}
			
			if(tile instanceof TileFLUDDStand) {
				return new GuiFLUDDStand(player.inventory, (TileFLUDDStand) tile);
			}
			
			if(tile instanceof TilePressureVessel) {
				return new GuiPressureVessel(player.inventory, (TilePressureVessel) tile);
			}
			
			if(tile instanceof TileDictionary) {
				return new GuiDictionary(player.inventory, (TileDictionary) tile);
			}
			
			if(tile instanceof TileSluice) {
				return new GuiSluice(player.inventory, (TileSluice) tile);
			}
			
			if(tile instanceof TileFishSorter) {
				return new GuiFishSorter(player.inventory, (TileFishSorter) tile);
			}
		}

		return null;
	}

	public void initClient() {
	}
}