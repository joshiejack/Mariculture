package mariculture.magic;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.items.ItemMCStorage;
import mariculture.magic.gui.ContainerMirror;
import mariculture.magic.gui.GuiMirror;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMirror extends ItemMCStorage {
    public final String name;

    public ItemMirror(String img) {
        super(4, "mirror");
        setMaxDamage(100);
        name = img;
        setCreativeTab(MaricultureTab.tabMagic);
    }

    @Override
    public Slot getSlot(InventoryStorage storage, int i) {
        switch (i) {
            case 0:
                return new SlotJewelry(storage, i, 8, 10, JewelryType.RING);
            case 1:
                return new SlotJewelry(storage, i, 8, 32, JewelryType.BRACELET);
            case 2:
                return new SlotJewelry(storage, i, 8, 54, JewelryType.NECKLACE);
            case 3:
                return new SlotSingle(storage, i, 35, 47);
        }

        return new Slot(storage, i, 100, 100);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack != null) {
            if (!player.isSneaking()) {
                world.playSoundAtEntity(player, Mariculture.modid + ":mirror", 1.0F, 1.0F);
                if (!player.capabilities.isCreativeMode && stack.attemptDamageItem(1, world.rand)) {
                    stack.stackSize--;
                } else {
                    player.openGui(Mariculture.instance, ItemMCStorage.GUI_ID, world, 0, 0, 0);
                }
            }

            return stack;
        }

        return null;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return OreDicHelper.getDictionaryName(stack2).equals("ingotAluminum");
    }

    @Override
    public Object getGUIContainer(EntityPlayer player) {
        return new ContainerMirror(player.inventory, new InventoryMirror(player), player.worldObj, player.getCurrentEquippedItem());
    }

    @Override
    public Object getGUIElement(EntityPlayer player) {
        return new GuiMirror(player.inventory, new InventoryMirror(player), player.worldObj, gui, player.getCurrentEquippedItem());
    }

    private class SlotSingle extends Slot {
        public SlotSingle(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

    private class SlotJewelry extends Slot {
        private ResourceLocation ring = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_ring.png");
        private ResourceLocation bracelet = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_bracelet.png");
        private ResourceLocation necklace = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_necklace.png");
        private JewelryType type;
        private IIcon[] bgIcons;

        public SlotJewelry(IInventory inv, int id, int x, int y, JewelryType type) {
            super(inv, id, x, y);
            this.type = type;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack.getItem() instanceof ItemJewelry) if (((ItemJewelry) stack.getItem()).getType() == type) return true;
            return false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ResourceLocation getBackgroundIconTexture() {
            return type == JewelryType.RING ? ring : type == JewelryType.BRACELET ? bracelet : type == JewelryType.NECKLACE ? necklace : texture == null ? TextureMap.locationItemsTexture : texture;
        }
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + name);
    }
}