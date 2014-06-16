package enchiridion;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enchiridion.CustomBooks.BookInfo;
import enchiridion.api.Formatting;

public class ItemEnchiridion extends Item {
	public IIcon pages;
	public IIcon[] icons;
	public static final int COUNT = 2;
	public static final int GUIDE = 0;
	public static final int BINDER = 1;

	public ItemEnchiridion() {
		setHasSubtypes(true);
		setCreativeTab(CreativeTab.books);
	}
	
	public static boolean isBookBinder(ItemStack stack) {
		if(!Config.binder_enabled) return false;
		return stack != null && stack.getItem() instanceof ItemEnchiridion && stack.getItemDamage() == BINDER;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		int meta = stack.getItemDamage();
		if(meta == GUIDE && stack.hasTagCompound()) {
			return CustomBooks.getBookInfo(stack).displayName;
		} else return Formatting.translate("item." + getName(meta) + ".name");
	}
	
	public String getName(int meta) {
		switch(meta) {
			case GUIDE: 	return "guide";
			case BINDER:	return "binder";
			default:		return "guide";
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean var) {
		if (stack.getItemDamage() == GUIDE) {
			if (stack.hasTagCompound()) {
				list.add(Formatting.translate("enchiridion.by") + " " + CustomBooks.getBookInfo(stack).author);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(isBookBinder(stack)) {
			if(stack.stackSize == 1) player.openGui(Enchiridion.instance, 0, player.worldObj, 0, 0, 0);
		} else player.openGui(Enchiridion.instance, 0, player.worldObj, 0, 0, 0);

		return stack;
	}
	
	//Only called on binders
	public int addToStorage(World world, ItemStack theBinder, ItemStack book) {
		int size = book.stackSize;
		int placed = 0;
		InventoryBinder binder = new InventoryBinder(world, theBinder);
		for(int i = 0; i < binder.getSizeInventory(); i++) {
			ItemStack item = binder.getStackInSlot(i);
			if(item == null) {
				ItemStack clone = book.copy();
				clone.stackSize = 1;
				binder.setInventorySlotContents(i, clone);
				placed++;
			}

			if(placed == size) break;
		}

		return placed;
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if(stack.getItemDamage() != GUIDE) return 16777215;
        else if(pass == 0) {
        	if (stack.hasTagCompound()) {
        		BookInfo info = CustomBooks.getBookInfo(stack);
        		return info.bookColor;
        	}
        } 
        
        return 16777215;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
	
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int meta) {
		return meta == GUIDE? 2: 1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	 public IIcon getIcon(ItemStack stack, int pass) {
        if(pass == 0) {
        	return icons[stack.getItemDamage()];
        } else return pages;
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		if(CustomBooks.bookInfo.size() > 0) {
			for (Entry<String, BookInfo> books : CustomBooks.bookInfo.entrySet()) {
				list.add(CustomBooks.create(books.getKey()));
			}
		}
		
		list.add(new ItemStack(item, 1, BINDER));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[COUNT];

		for (int i = 0; i < icons.length; i++) {
			String name = getName(i);
			if(name != null) {
				icons[i] = iconRegister.registerIcon("books" + ":" + name);
			}
		}
		
		pages = iconRegister.registerIcon("books:pages");
	}
}
