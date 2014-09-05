package joshie.maritech.extensions.modules;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.lib.ItemLib.goldPlastic;
import static joshie.mariculture.core.lib.ItemLib.life;
import static joshie.mariculture.core.lib.ItemLib.plasticLens;
import static joshie.mariculture.core.lib.ItemLib.tank;
import static joshie.mariculture.core.lib.ItemLib.transparent;
import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.core.lib.EntityIds;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.blocks.BlockCustomPower;
import joshie.maritech.entity.EntityFLUDDSquirt;
import joshie.maritech.items.ItemFLUDD;
import joshie.maritech.lib.MTRenderIds;
import joshie.maritech.network.PacketFLUDD;
import joshie.maritech.network.PacketRotorSpin;
import joshie.maritech.network.PacketSponge;
import joshie.maritech.tile.TileCustomPowered;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileGenerator;
import joshie.maritech.tile.TilePressureVessel;
import joshie.maritech.tile.TileRotor;
import joshie.maritech.tile.TileRotorAluminum;
import joshie.maritech.tile.TileRotorCopper;
import joshie.maritech.tile.TileRotorTitanium;
import joshie.maritech.tile.TileSluice;
import joshie.maritech.tile.TileSluiceAdvanced;
import joshie.maritech.tile.TileSponge;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

public class ExtensionFactory implements IModuleExtension {
    private static ArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

    public static Block customRFBlock;

    public static Item fludd;

    @Override
    public void preInit() {
        customRFBlock = new BlockCustomPower().setStepSound(Block.soundTypePiston).setBlockName("customRFBlock");
        fludd = new ItemFLUDD(armorFLUDD, MTRenderIds.FLUDD, 1).setUnlocalizedName("fludd");
        RegistryHelper.registerTiles("Mariculture", TileCustomPowered.class, TileSluice.class, TileFLUDDStand.class, TilePressureVessel.class, TileSponge.class, TileSluiceAdvanced.class, TileGenerator.class, TileRotor.class, TileRotorCopper.class, TileRotorAluminum.class, TileRotorTitanium.class);
        EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
    }

    @Override
    public void init() {
        addShaped(((ItemFLUDD) fludd).build(0), new Object[] { " E ", "PGP", "LUL", 'E', plasticLens, 'P', goldPlastic, 'G', transparent, 'L', tank, 'U', life });
    }

    @Override
    public void postInit() {
        PacketHandler.registerPacket(PacketSponge.class, Side.CLIENT);
        PacketHandler.registerPacket(PacketSponge.class, Side.SERVER);
        PacketHandler.registerPacket(PacketFLUDD.class, Side.CLIENT);
        PacketHandler.registerPacket(PacketFLUDD.class, Side.SERVER);
        PacketHandler.registerPacket(PacketRotorSpin.class, Side.CLIENT);
    }
}
