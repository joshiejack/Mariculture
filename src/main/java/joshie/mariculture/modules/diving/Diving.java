package joshie.mariculture.modules.diving;

import joshie.mariculture.api.diving.CapabilityWaterBreathing.IDisableHardcoreDiving;
import joshie.mariculture.api.diving.CapabilityWaterBreathing.WaterBreathingStorage;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.core.util.item.ItemComponent;
import joshie.mariculture.modules.diving.block.BlockDiving;
import joshie.mariculture.modules.diving.item.*;
import joshie.mariculture.modules.diving.lib.DivingComponent;
import joshie.mariculture.modules.diving.render.ModelBuoyancyAid;
import joshie.mariculture.modules.diving.render.ModelSnorkel;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.core.helpers.ConfigHelper.getDouble;
import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.lib.CreativeOrder.DIVING;
import static joshie.mariculture.modules.diving.lib.DivingComponent.LENS;
import static net.minecraft.init.Items.*;

/** The Diving Module contains things that will help you operate underwater
 *  It will see the return of the diving gear and air pump at least */
@MCLoader
public class Diving {
    public static final ArmorMaterial ARMOR_SNORKEL = EnumHelper.addArmorMaterial("SNORKEL", "SNORKEL", 10, new int[] { 0, 0, 1, 0 }, 20, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.wood.hit")), 0F);
    public static final ArmorMaterial ARMOR_DIVING = EnumHelper.addArmorMaterial("DIVING", "DIVING", 20, new int[] { 3, 0, 1, 2 }, 15, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.wood.hit")), 0F);
    public static final ItemSnorkel SNORKEL = new ItemSnorkel().register("snorkel");
    public static final ItemBuoyancyAid BUOYANCY_AID = new ItemBuoyancyAid().register("buoyancyaid");
    public static final ItemHelmet HELMET = new ItemHelmet().register("helmet");
    public static final ItemAirSupply AIR_SUPPLY = new ItemAirSupply().register("airsupply");
    public static final ItemBoots BOOTS = new ItemBoots().register("boots");
    public static final ItemSuit SUIT = new ItemSuit().register("suit");
    public static final ItemComponent COMPONENT = new ItemComponent<>(DIVING, DivingComponent.class).register("diving_component");
    public static final BlockDiving DIVING_BLOCK = new BlockDiving().register("diving_block");

    public static void preInit() {
        MCTab.getExploration().setStack(SNORKEL.getStack());
    }

    @SuppressWarnings("unchecked")
    public static void init() {
        CapabilityManager.INSTANCE.register(IDisableHardcoreDiving.class, new WaterBreathingStorage(), new IDisableHardcoreDiving(){}.getClass());
        addShaped(SNORKEL.getStack(), "LR", 'R', REEDS, 'L', COMPONENT.getStackFromEnum(LENS));
        addShaped(BUOYANCY_AID.getStack(), "WSW", "WWW", "WWW", 'S', STRING, 'W', "logWood");
        addShaped(COMPONENT.getStackFromEnum(LENS), " W ", "WGW", " W ", 'W', "plankWood", 'G', "blockGlass");
        addShaped(HELMET.getStack(), "CCC", "CGC", 'C', "ingotCopper", 'G', "blockGlass");
        addShaped(AIR_SUPPLY.getStack(), " L ", "L L ", " L ", 'L', LEATHER);
        addShaped(SUIT.getStack(), "L L", "LLL ", "L L", 'L', LEATHER);
        addShaped(BOOTS.getStack(), "C C", "L L", 'C', "ingotCopper", 'L', "ingotLead");
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        BUOYANCY_AID.setArmorModel(new ModelBuoyancyAid());
        SNORKEL.setArmorModel(new ModelSnorkel());
    }

    //Configuration options
    public static double BUOYANCY_AID_ASCEND_SPEED;
    public static double DIVING_BOOTS_SINK_SPEED;

    public static void configure() {
        BUOYANCY_AID_ASCEND_SPEED = getDouble("Buoyuancy Aid > Ascend Speed", 0.25D);
        DIVING_BOOTS_SINK_SPEED = getDouble("Diving Boots > Ascend Speed", -0.25D);
    }
}
