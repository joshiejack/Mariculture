package mariculture.fishery.items;

import mariculture.api.fishery.EnumRodQuality;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVanillaRod extends ItemRod {
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;
	
	public ItemVanillaRod() {
		super(64, 1);
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("fishingRod");
		setTextureName("fishing_rod");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(getIconString() + "_uncast");
        theIcon = iconRegister.registerIcon(getIconString() + "_cast");
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon func_94597_g() {
        return theIcon;
    }
}
