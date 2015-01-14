package mariculture.magic.jewelry;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.items.ItemMCDamageable;
import mariculture.core.util.MCTranslate;
import mariculture.lib.util.Text;
import mariculture.magic.JewelryHandler;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemJewelry extends ItemMCDamageable {
    public static final HashMap<String, IIcon> specials = new HashMap();

    public static enum JewelryType {
        RING(1), BRACELET(3), NECKLACE(7), NONE(0);

        public final int max;

        private JewelryType(int max) {
            this.max = max;
        }

        public int getMaximumEnchantments() {
            return max;
        }
    }

    public ItemJewelry() {
        super(100);
        setNoRepair();
        setCreativeTab(MaricultureTab.tabMagic);
    }

    //The 'code' 0, 1, 2 for the item types
    public abstract JewelryType getType();

    //This is the utter maximum durability upgrades this piece can have
    public abstract int getMaxDurability();

    //Whether to render the binding
    public abstract boolean renderBinding();

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int meta) {
        return 3;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String format = MCTranslate.translate(getType().name().toLowerCase() + ".format");
        if(stack.hasTagCompound()) {
            JewelryMaterial material = JewelryHandler.getMaterial(stack);
            if (material == Magic.dummyMaterial) return MCTranslate.translate("oneRing");
            format = format.replace("%M", material.getCraftingItem(getType()).getDisplayName());
            format = format.replace("%T", MCTranslate.translate(getType().name().toLowerCase()));
            return material.getColor() + format;
        }

        return Text.localize(getUnlocalizedName(stack));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.hasTagCompound()) {
            JewelryType type = getType();
            JewelryMaterial material = JewelryHandler.getMaterial(stack);
            JewelryBinding binding = JewelryHandler.getBinding(stack);
            list.add(binding.getColor() + MCTranslate.translate("with") + " " + binding.getCraftingItem(getType()).getDisplayName());
            if (player.worldObj.isRemote && material != Magic.dummyMaterial) if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                list.add(mariculture.lib.util.Text.INDIGO + MCTranslate.translate("stats.jewelry"));
                list.add(MCTranslate.translate("elemental") + ": " + MCTranslate.translate("elemental." + material.getIdentifier().toLowerCase()));
                list.add(MCTranslate.translate("max.enchants") + ": " + (type.getMaximumEnchantments() + material.getExtraEnchantments(type)));
                list.add(MCTranslate.translate("max.level") + ": " + Math.min(binding.getMaxEnchantmentLevel(type), material.getMaximumEnchantmentLevel(type)));
                list.add(MCTranslate.translate("max.damage") + ": " + getMaxDamage(stack));
            } else {
                list.add(MCTranslate.getShiftText("jewelry"));
            }
        }

        //Add the one ring lore
        if (EnchantHelper.getLevel(Magic.oneRing, stack) > 0) {
            list.add(" ");
            list.add(StatCollector.translateToLocal("enchantment.oneRing.line1"));
            list.add(StatCollector.translateToLocal("enchantment.oneRing.line2"));
            list.add(StatCollector.translateToLocal("enchantment.oneRing.line3"));
            list.add(StatCollector.translateToLocal("enchantment.oneRing.line4"));
            list.add(" ");
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (stack.hasTagCompound()) return (int) (JewelryHandler.getBinding(stack).getDurabilityBase(getType()) * JewelryHandler.getMaterial(stack).getDurabilityModifier(getType()));
        else return this.getMaxDamage();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (stack.hasTagCompound()) if (pass == 0) {
            if (renderBinding()) {
                JewelryBinding binding = JewelryHandler.getBinding(stack);
                if (binding != null && !binding.ignore) return binding.getIcon(getType());
            }
        } else if (pass == 1) {
            JewelryMaterial material = JewelryHandler.getMaterial(stack);
            if (material != null && !material.ignore) return material.getIcon(getType());
        } else if (stack.stackTagCompound.hasKey("Specials")) return specials.get(stack.stackTagCompound.getString("Specials"));

        return itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        //Register the binding icons for this type
        if (renderBinding()) {
            for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
                if (binding.getValue().ignore) {
                    continue;
                }
                binding.getValue().registerIcons(iconRegister, getType());
            }
        }

        //Register the material icons for this type
        for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
            if (material.getValue().ignore) {
                continue;
            }
            material.getValue().registerIcons(iconRegister, getType());
        }

        //Register the invisible icon
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":jewelry/blank");

        //Register the onering
        specials.put(mariculture.lib.util.Text.YELLOW + "One Ring", iconRegister.registerIcon(Mariculture.modid + ":jewelry/ring/oneRing"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        //Add all item variants to the list
        for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
            if (binding.getValue().ignore) {
                continue;
            }
            for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
                if (material.getValue().ignore) {
                    continue;
                }

                list.add(JewelryHandler.createJewelry((ItemJewelry) item, binding.getValue(), material.getValue()));
            }
        }

        //Add the one ring to the list
        ItemStack stack = JewelryHandler.createSpecial((ItemJewelry) item, JewelryType.RING, mariculture.lib.util.Text.YELLOW + "One Ring");
        if (stack != null) {
            if (EnchantHelper.exists(Magic.oneRing)) {
                stack.addEnchantment(Magic.oneRing, 1);
                list.add(stack);
            }
        }
    }
}
