package joshie.mariculture.modules.diving;

import joshie.mariculture.api.diving.CapabilityWaterBreathing.WaterBreathingStorage;
import joshie.mariculture.api.diving.CapabilityWaterBreathing.IDisableHardcoreDiving;
import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.diving.item.ItemBuoyancyAid;
import joshie.mariculture.modules.diving.item.ItemDivingComponent;
import joshie.mariculture.modules.diving.item.ItemSnorkel;
import joshie.mariculture.modules.diving.render.ModelBuoyancyAid;
import joshie.mariculture.modules.diving.render.ModelSnorkel;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.helpers.ConfigHelper.getDouble;
import static joshie.mariculture.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.modules.diving.item.ItemDivingComponent.DivingComponent.LENS;
import static joshie.mariculture.util.MCTab.getTab;
import static net.minecraft.init.Items.REEDS;
import static net.minecraft.init.Items.STRING;

/** The Diving Module contains things that will help you operate underwater
 *  It will see the return of the diving gear and air pump at least */
@Module(name = "diving")
public class Diving {
    public static final ArmorMaterial ARMOR_SNORKEL = EnumHelper.addArmorMaterial("SNORKEL", "SNORKEL", 10, new int[] { 0, 0, 1, 0 }, 20, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.wood.hit")), 0F);
    public static final ItemSnorkel SNORKEL = new ItemSnorkel().register("snorkel");
    public static final ItemBuoyancyAid BUOYANCY_AID = new ItemBuoyancyAid().register("buoyancyaid");
    public static final ItemDivingComponent COMPONENT = new ItemDivingComponent().register("diving_component");

    public static void preInit() {
        getTab("exploration").setStack(SNORKEL.getStack());
    }

    public static void init() {
        CapabilityManager.INSTANCE.register(IDisableHardcoreDiving.class, new WaterBreathingStorage(), new IDisableHardcoreDiving(){}.getClass());
        addShaped(SNORKEL.getStack(), "LR", 'R', REEDS, 'L', COMPONENT.getStackFromEnum(LENS));
        addShaped(BUOYANCY_AID.getStack(), "WSW", "WWW", "WWW", 'S', STRING, 'W', "logWood");
        addShaped(COMPONENT.getStackFromEnum(LENS), " W ", "WGW", " W ", 'W', "plankWood", 'G', "blockGlass");
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        BUOYANCY_AID.setArmorModel(new ModelBuoyancyAid());
        SNORKEL.setArmorModel(new ModelSnorkel());
    }

    //Configuration options
    public static double BUOYANCY_AID_ASCEND_SPEED;

    public static void configure() {
        BUOYANCY_AID_ASCEND_SPEED = getDouble("Buoyuancy Aid > Ascend Speed", 0.25D);
    }
}
