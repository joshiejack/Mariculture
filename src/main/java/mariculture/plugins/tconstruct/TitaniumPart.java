package mariculture.plugins.tconstruct;

import mariculture.Mariculture;
import mariculture.plugins.PluginTConstruct;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.util.IToolPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TitaniumPart extends Item implements IToolPart {
	public TitaniumPart(int id) {
		super(id);
		setCreativeTab(TConstructRegistry.materialTab);
		setMaxStackSize(64);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		this.itemIcon = icon.registerIcon(Mariculture.modid + ":parts/" + ((this.getUnlocalizedName()).replace(".", "_")).substring(5));
	}

	public int getMaterialID(ItemStack itemStack) {
		return PluginTConstruct.titanium_id;
	}
}