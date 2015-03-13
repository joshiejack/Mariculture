package mariculture.fishery.items;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.ICaughtAliveModifier;
import mariculture.api.fishery.IHelmetFishManipulator;
import mariculture.core.items.ItemMCBaseArmor;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.MCTranslate;
import mariculture.fishery.Fishery;
import mariculture.fishery.FishyHelper;
import mariculture.fishery.render.ModelFishingHat;
import mariculture.lib.helpers.ClientHelper;
import mariculture.magic.MirrorEnchantHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorFishingHat extends ItemMCBaseArmor implements ICaughtAliveModifier, IHelmetFishManipulator {
    private IIcon greyscale;

    private Random rand = new Random();

    public ItemArmorFishingHat(ArmorMaterial material, int j, int k) {
        super(material, j, k);
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public double getModifier() {
        return 5D;
    }
    
    @Override
    public double getModifier(ItemStack stack) {
        return getColor(stack) < 0? 15D: 5D;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.hasTagCompound()) {
            String texture = stack.getTagCompound().getString("Texture");
            return "mariculture:" + "textures/armor/fishinghat_greyscale.png";
        }

        return "mariculture:" + "textures/armor/fishinghat.png";
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int damage) {
        return 1;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (!stack.hasTagCompound()) {
            return super.getIcon(stack, pass);
        } else {
            return greyscale;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String translate = MCTranslate.translate("pearl.color." + PearlColor.get(stack.getItemDamage()) + ".hat");
        if (!translate.equals("mariculture.pearl.color." + PearlColor.get(stack.getItemDamage()) + ".hat")) {
            return translate;
        }

        String format = MCTranslate.translate("pearl.format");
        format = format.replace("%C", MCTranslate.translate("pearl.color." + PearlColor.get(stack.getItemDamage())));
        format = format.replace("%P", MCTranslate.translate("pearl"));
        return format.replace("%B", super.getItemStackDisplayName(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        int color = getColor(stack);
        switch (color) {
            case PearlColor.WHITE:
                return 0xEEEEEE;
            case PearlColor.GREEN:
                return 0x9AB091;
            case PearlColor.YELLOW:
                return 0xE4EC8C;
            case PearlColor.ORANGE:
                return 0xDD8B55;
            case PearlColor.RED:
                return 0xB03D39;
            case PearlColor.GOLD:
                return 0xDEDE00;
            case PearlColor.BROWN:
                return 0x806953;
            case PearlColor.PURPLE:
                return 0xD5AAFF;
            case PearlColor.BLUE:
                return 0x8FA6D6;
            case PearlColor.BLACK:
                return 0x232323;
            case PearlColor.PINK:
                return 0xCC7B92;
            case PearlColor.SILVER:
                return 0xD7D7D7;
            default:
                return super.getColorFromItemStack(stack, pass);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        return new ModelFishingHat(stack);
    }

    public int getColor(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return -1;
        }

        return stack.getTagCompound().getInteger("Color");
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        greyscale = register.registerIcon("mariculture:fishinghat_greyscale");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        int color = getColor(stack);
        if (color >= 0) {
            list.add(PearlColor.getPrefix(color) + MCTranslate.translate("hat." + PearlColor.get(color) + ".title"));
            if (!ClientHelper.isShiftPressed()) {
                list.add(MCTranslate.translate("hat." + PearlColor.get(color) + ".description.abstract"));
            } else list.add(MCTranslate.translate("hat." + PearlColor.get(color) + ".description.actual"));
        }
    }

    public static ItemStack getDyed(int color) {
        ItemStack stack = new ItemStack(Fishery.fishinghat);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("Color", color);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item));
        for (int i = 0; i < PearlColor.COUNT; i++) {
            list.add(getDyed(i));
        }
    }

    /* Special Features */

    @Override
    public Integer getGender(ItemStack stack) {
        int color = getColor(stack);
        if (color == PearlColor.BLUE) {
            return FishyHelper.MALE;
        } else if (color == PearlColor.PINK) {
            return FishyHelper.FEMALE;
        }

        return rand.nextInt(2);
    }

    @Override
    public Salinity getSalt(ItemStack stack, Salinity salt) {
        int color = getColor(stack);
        if (color == PearlColor.WHITE) {
            return Salinity.FRESH;
        } else if (color == PearlColor.SILVER) {
            return Salinity.SALINE;
        } else if (color == PearlColor.YELLOW) {
            return Salinity.BRACKISH;
        } else if (color == PearlColor.GREEN) {
            return Salinity.FRESH; //The salinity of jungle
        } else if (color == PearlColor.RED) {
            return Salinity.FRESH; //The salinity of the nether
        }

        return salt;
    }

    @Override
    public int getTemperature(ItemStack stack, int temperature) {
        int color = getColor(stack);
        if (color == PearlColor.GREEN) {
            return 24; //The default temperature of tetra/damselfish
        } else if (color == PearlColor.RED) {
            return 90; //Nether temperature
        }

        return temperature;
    }

    @Override
    public boolean forceLoot(ItemStack stack) {
        return getColor(stack) == PearlColor.GOLD;
    }

    @Override
    public boolean forceFish(ItemStack stack) {
        return getColor(stack) == PearlColor.ORANGE;
    }

    @Override
    public int getYHeight(ItemStack stack, int yHeight) {
        return getColor(stack) == PearlColor.BROWN ? 10 : yHeight;
    }

    @Override
    public World getWorld(ItemStack stack, World fakeWorld) {
        return getColor(stack) == PearlColor.RED ? DimensionManager.getWorld(-1) : fakeWorld;
    }

    @Override
    public boolean alwaysDead(ItemStack stack) {
        return getColor(stack) == PearlColor.BLACK;
    }

    @Override
    public ItemStack getLoot(ItemStack helmet, ItemStack loot) {
        if (getColor(helmet) == PearlColor.PURPLE) {
            if (!loot.isItemEnchanted()) {
                List list = MirrorEnchantHelper.buildEnchantmentList(rand, loot, 10, 10);
                if (list != null) {
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        EnchantmentData enchantmentdata = (EnchantmentData) iterator.next();
                        loot.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                    }
                }
            }
        }

        return loot;
    }
}