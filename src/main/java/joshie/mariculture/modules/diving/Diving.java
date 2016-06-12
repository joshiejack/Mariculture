package joshie.mariculture.modules.diving;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.diving.item.ItemBuoyancyAid;
import joshie.mariculture.modules.diving.render.ModelBuoyancyAid;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.helpers.ConfigHelper.getDouble;
import static joshie.mariculture.helpers.RecipeHelper.addShaped;
import static net.minecraft.init.Items.STRING;

/** The Diving Module contains things that will help you operate underwater
 *  It will see the return of the diving gear and air pump at least */
@Module(name = "diving")
public class Diving {
    public static final ArmorMaterial ARMOR_SNORKEL = EnumHelper.addArmorMaterial("SNORKEL", "SNORKEL", 10, new int[] { 0, 0, 0, 0 }, 0, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.wood.hit")), 0F);
    public static final ItemBuoyancyAid BUOYANCY_AID = new ItemBuoyancyAid().setUnlocalizedName("buoyancyaid").register();

    public static void preInit() {}

    public static void init() {
        addShaped(BUOYANCY_AID.getStack(), new Object[] { "WSW", "WWW", "WWW", 'S', STRING, 'W', "logWood" });
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        BUOYANCY_AID.setArmorModel(new ModelBuoyancyAid());
    }

    //Configuration options
    public static double BUOYANCY_AID_ASCEND_SPEED;

    public static void configure() {
        BUOYANCY_AID_ASCEND_SPEED = getDouble("Buoyuancy Aid > Ascend Speed", 0.25D);
    }
}
