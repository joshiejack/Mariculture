package joshie.mariculture.fishery.tile;

import static joshie.mariculture.core.util.Fluids.getFluidID;
import static joshie.mariculture.core.util.Fluids.getFluidStack;

import java.util.ArrayList;
import java.util.List;

import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.api.util.CachedCoords;
import joshie.mariculture.core.config.Machines.Ticks;
import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;
import joshie.mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import joshie.mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import joshie.mariculture.core.helpers.BlockHelper;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.core.lib.MachineSpeeds;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.core.network.PacketSyncFeeder;
import joshie.mariculture.core.tile.base.TileMachineTank;
import joshie.mariculture.core.util.IHasNotification;
import joshie.mariculture.core.util.Translate;
import joshie.mariculture.fishery.Fish;
import joshie.mariculture.fishery.FishFoodHandler;
import joshie.mariculture.fishery.FishyHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFeeder extends TileMachineTank implements IHasNotification, IEnergyConnection {
    private static final int fluid = 3;
    public static final int male = 5;
    public static final int female = 6;

    public ArrayList<CachedCoords> coords = new ArrayList<CachedCoords>();
    private int isInit = 50;
    private boolean isDay;
    private boolean swap = false;
    private int foodTick;
    private int tankSize;

    //Fish Locations and animations
    public int mPos = 0;
    public int fPos = 0;
    public int mTicker = 0;
    public int fTicker = 0;
    public double mRot = 0F;
    public double fRot = 0F;

    public TileFeeder() {
        max = MachineSpeeds.getFeederSpeed();
        inventory = new ItemStack[13];
        output = new int[] { 7, 8, 9, 10, 11, 12 };
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().expand(5D, 5D, 5D);
    }

    @Override
    public int getTankCapacity(int storage) {
        return 2 * tankSize * (storage + 1);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return new int[] { male, female, 3, 7, 8, 9, 10, 11, 12 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == male) return inventory[male] == null && Fishing.fishHelper.isMale(stack);
        if (slot == female) return inventory[female] == null && Fishing.fishHelper.isFemale(stack);
        if (slot == fluid) return FluidHelper.isFluidOrEmpty(stack);
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot > female;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        super.setInventorySlotContents(slot, stack);

        if (!worldObj.isRemote) {
            if (slot == male && Fishing.fishHelper.isMale(stack)) {
                updateTankSize();
            } else if (slot == female) {
                PacketHandler.syncInventory(this, inventory);
            }
        }
    }

    //Updates the size of the tank
    private void updateTankSize() {
        int xP = 0, xN = 0, yP = 0, yN = 0, zP = 0, zN = 0;
        ItemStack male = inventory[this.male];
        if (male != null) {
            xP = Fish.east.getDNA(male);
            xN = Fish.west.getDNA(male);
            yP = Fish.up.getDNA(male);
            yN = Fish.down.getDNA(male);
            zP = Fish.south.getDNA(male);
            zN = Fish.north.getDNA(male);
        }

        coords = new ArrayList<CachedCoords>();
        for (int x = -5 - xN; x <= 5 + xP; x++) {
            for (int z = -5 - zN; z <= 5 + zP; z++) {
                for (int y = -5 - yN; y <= 5 + yP; y++) {
                    if (BlockHelper.isFishLiveable(worldObj, xCoord + x, yCoord + y, zCoord + z)) {
                        coords.add(new CachedCoords(xCoord + x, yCoord + y, zCoord + z));
                    }
                }
            }
        }

        tankSize = coords.size();
        updateUpgrades();

        if (!worldObj.isRemote) {
            PacketHandler.syncInventory(this, inventory);
            PacketHandler.sendAround(new PacketSyncFeeder(xCoord, yCoord, zCoord, coords), this);
        }
    }

    //Processes fish
    public void process() {
        if (swap) {
            makeProduct(inventory[female]);
        } else {
            makeProduct(inventory[male]);
        }

        damageFish(inventory[female], true);
        damageFish(inventory[male], true);
    }

    //Damages Fish
    private void damageFish(ItemStack fish, boolean giveProduct) {
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null) {
            int gender = Fish.gender.getDNA(fish);
            if (gender == FishyHelper.FEMALE && MaricultureHandlers.upgrades.hasUpgrade("female", this)) return;
            if (gender == FishyHelper.MALE && MaricultureHandlers.upgrades.hasUpgrade("male", this)) return;
            int reduce = max - purity * 15;
            fish.stackTagCompound.setInteger("CurrentLife", fish.stackTagCompound.getInteger("CurrentLife") - reduce);
            if (fish.stackTagCompound.getInteger("CurrentLife") <= 0 || MaricultureHandlers.upgrades.hasUpgrade("debugKill", this)) {
                killFish(species, gender, giveProduct);
            }
        }
    }

    //Makes a Fish Product
    private void makeProduct(ItemStack fish) {
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null) {
            for (int i = 0; i < Fish.production.getDNA(fish); i++) {
                ItemStack product = species.getProduct(worldObj.rand);
                if (product != null) {
                    helper.insertStack(product, output);
                }
                int gender = Fish.gender.getDNA(fish);

                if (MaricultureHandlers.upgrades.hasUpgrade("female", this)) {
                    int fertility = Math.max(1, (5500 - Fish.fertility.getDNA(fish)) / 50);
                    if (worldObj.rand.nextInt(fertility) == 0) {
                        generateEgg();
                    }
                }

                if (MaricultureHandlers.upgrades.hasUpgrade("male", this)) {
                    product = species.getProduct(worldObj.rand);
                    if (product != null) {
                        helper.insertStack(product, output);
                    }
                }
            }
        }
    }

    //Kills a Fish
    private void killFish(FishSpecies species, int gender, boolean giveProduct) {
        if (giveProduct) {
            ItemStack raw = species.getRawForm(1);
            if (raw != null) {
                helper.insertStack(raw, output);
            }

            if (gender == FishyHelper.FEMALE) {
                generateEgg();
            }
        }

        if (gender == FishyHelper.FEMALE) {
            decrStackSize(female, 1);
        } else if (gender == FishyHelper.MALE) {
            decrStackSize(male, 1);
        }
    }

    //Generates an egg
    private void generateEgg() {
        if (Fishing.fishHelper.getSpecies(inventory[male]) != null) {
            helper.insertStack(Fishing.fishHelper.generateEgg(inventory[male], inventory[female]), output);
        }
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (isInit <= 0 && isInit > -1000) {
                isInit = -1000;
                updateTankSize();
            } else isInit--;
        }

        super.updateEntity();
    }

    @Override
    public void update() {
        super.update();

        if (canWork) {
            foodTick++;

            if (onTick(Ticks.EFFECT_TICK)) {
                if (swap) {
                    doEffect(inventory[male]);
                    swap = false;
                } else {
                    doEffect(inventory[female]);
                    swap = true;
                }
            }

            //Fish will eat every 25 seconds by default
            if (foodTick % (Ticks.FISH_FOOD_TICK * 20) == 0) {
                if (swap) {
                    useFood(inventory[male]);
                } else {
                    useFood(inventory[female]);
                }
            }

            if (Ticks.PICKUP_TICK >= 0 && onTick(Ticks.PICKUP_TICK)) {
                pickupFood();
            }
        }
    }

    //Performs an effect on the world
    private void doEffect(ItemStack fish) {
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null) {
            if (!worldObj.isRemote) {
                species.affectWorld(worldObj, xCoord, yCoord, zCoord, coords);

                for (CachedCoords cord : coords) {
                    List list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, Blocks.stone.getCollisionBoundingBoxFromPool(worldObj, cord.x, cord.y, cord.z));
                    if (!list.isEmpty()) {
                        for (Object i : list) {
                            species.affectLiving((EntityLivingBase) i);
                        }
                    }
                }
            }
        }
    }

    //Uses fish food
    private void useFood(ItemStack fish) {
        int usage = 0;
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null) {
            usage = Fish.foodUsage.getDNA(fish);
            usage = usage == 0 && species.requiresFood() ? 1 : usage;
        }

        drain(ForgeDirection.DOWN, getFluidStack("fish_food", usage), true);
    }

    private void pickupFood() {
        for (CachedCoords coord : coords) {
            List list = worldObj.getEntitiesWithinAABB(EntityItem.class, Blocks.stone.getCollisionBoundingBoxFromPool(worldObj, coord.x, coord.y, coord.z));
            if (!list.isEmpty()) {
                for (Object i : list) {
                    EntityItem entity = (EntityItem) i;
                    ItemStack item = entity.getEntityItem();
                    if (((entity.handleWaterMovement()) || entity.worldObj.provider.isHellWorld && entity.handleLavaMovement())) {
                        item = addFishFood(item);

                        if (item == null) {
                            entity.setDead();
                        }
                    }
                }
            }
        }
    }

    private ItemStack addFishFood(ItemStack stack) {
        if (FishFoodHandler.isFishFood(stack)) {
            int increase = FishFoodHandler.getValue(stack);
            int loop = stack.stackSize;

            for (int i = 0; i < loop; i++) {
                int fill = fill(ForgeDirection.UP, getFluidStack("fish_food", increase), false);
                if (fill > 0) {
                    fill(ForgeDirection.UP, getFluidStack("fish_food", increase), true);
                    stack.stackSize--;
                }
            }
        }

        if (stack.stackSize <= 0) {
            return null;
        }

        return stack;
    }

    @Override
    public boolean canWork() {
        return RedstoneMode.canWork(this, mode) && hasMale() && hasFemale() && fishCanLive(inventory[male]) && fishCanLive(inventory[female]) && hasRoom(null);
    }

    //Returns whether we have a male fish
    private boolean hasMale() {
        return inventory[male] != null && Fishing.fishHelper.isMale(inventory[male]);
    }

    //Returns where there is a female fish
    private boolean hasFemale() {
        return inventory[female] != null && Fishing.fishHelper.isFemale(inventory[female]);
    }

    private boolean fishCanLive(ItemStack fish) {
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null) {
            if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) return true;
            else if (tank.getFluid() == null || tank.getFluid() != null && tank.getFluid().fluidID != getFluidID("fish_food")) return false;

            return tankSize >= Fish.tankSize.getDNA(fish) && Fishing.fishHelper.canLive(worldObj, xCoord, yCoord, zCoord, fish);
        } else return false;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        for (int slot = 0; slot < 2; slot++) {
            if (inventory[slot] == null) {
                continue;
            }
            FishSpecies species = Fishing.fishHelper.getSpecies(inventory[slot]);
            if (species != null) return species.canConnectEnergy(from);
        }

        return false;
    }

    public int getLightValue() {
        int lM = 0, lF = 0;
        FishSpecies sMale = Fishing.fishHelper.getSpecies(inventory[male]);
        FishSpecies sFemale = Fishing.fishHelper.getSpecies(inventory[female]);
        if (sMale != null && sMale.getLightValue() > 0) {
            lM = sMale.getLightValue();
        }
        if (sFemale != null && sFemale.getLightValue() > 0) {
            lF = sFemale.getLightValue();
        }
        if (lM == 0) return lF;
        else if (lF == 0) return lM;
        else return (lM + lF) / 2;
    }

    //Gui Stuffs
    private boolean addToolTip(ArrayList<String> tooltip, String text) {
        tooltip.add(joshie.lib.util.Text.RED + text);
        return false;
    }

    public Salinity getSalinity() {
        Salinity salt = MaricultureHandlers.environment.getSalinity(worldObj, xCoord, zCoord);
        int salinity = salt.ordinal() + MaricultureHandlers.upgrades.getData("salinity", this);
        if (salinity <= 0) {
            salinity = 0;
        }
        if (salinity > 2) {
            salinity = 2;
        }
        salt = Salinity.values()[salinity];
        return salt;
    }

    public ArrayList<String> getTooltip(int slot, ArrayList<String> tooltip) {
        boolean noBad = true;
        ItemStack fish = inventory[slot];
        if (fish != null && fish.hasTagCompound() && !Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
            int currentLife = fish.stackTagCompound.getInteger("CurrentLife") / 20;
            if (!MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
                FishSpecies species = FishSpecies.species.get(Fish.species.getDNA(fish));
                if (!MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) && !species.isValidDimensionForWork(worldObj)) {
                    noBad = addToolTip(tooltip, Translate.translate("badWorld"));
                }

                int temperature = MaricultureHandlers.environment.getTemperature(worldObj, xCoord, yCoord, zCoord) + heat;
                if (temperature < species.temperature[0]) {
                    int required = species.temperature[0] - temperature;
                    noBad = addToolTip(tooltip, Translate.translate("tooCold"));
                    noBad = addToolTip(tooltip, "  +" + required + joshie.lib.util.Text.DEGREES);
                } else if (temperature > species.temperature[1]) {
                    int required = temperature - species.temperature[1];
                    noBad = addToolTip(tooltip, Translate.translate("tooHot"));
                    noBad = addToolTip(tooltip, "  -" + required + joshie.lib.util.Text.DEGREES);
                }

                boolean match = false;
                Salinity salt = getSalinity();
                for (Salinity salinity : species.salinity)
                    if (salt == salinity) {
                        match = true;
                        break;
                    }

                if (!match) {
                    for (Salinity salinity : species.salinity) {
                        noBad = addToolTip(tooltip, Translate.translate("salinity.prefers") + " " + Translate.translate("salinity." + salinity.toString().toLowerCase()));
                    }
                }

                int size = Fish.tankSize.getDNA(fish);
                if (tankSize < size) {
                    noBad = addToolTip(tooltip, Translate.translate("notAdvanced"));
                    String text = worldObj.provider.isHellWorld ? Translate.translate("blocks.lava") : Translate.translate("blocks.water");
                    noBad = addToolTip(tooltip, "  +" + (size - tankSize) + " " + text);
                }

                if (!species.canWorkAtThisTime(isDay)) {
                    noBad = addToolTip(tooltip, Translate.translate("badTime"));
                }

                if (!hasMale() || !hasFemale()) {
                    noBad = addToolTip(tooltip, Translate.translate("missingMate"));
                }

                if (tank.getFluidAmount() < 1 || tank.getFluid().fluidID != getFluidID("fish_food")) {
                    noBad = addToolTip(tooltip, Translate.translate("noFood"));
                }

                if (noBad) {
                    tooltip.add(joshie.lib.util.Text.DARK_GREEN + currentLife + " HP");
                }
            } else if (hasMale() && hasFemale()) {
                tooltip.add(joshie.lib.util.Text.DARK_GREEN + currentLife + " HP");
            }
        }

        return tooltip;
    }

    public int getFishLifeScaled(ItemStack fish, int scale) {
        FishSpecies species = Fishing.fishHelper.getSpecies(fish);
        if (species != null && fish.hasTagCompound()) {
            int gender = Fish.gender.getDNA(fish);
            int maxLife = fish.stackTagCompound.getInteger("Lifespan");
            int currentLife = fish.stackTagCompound.getInteger("CurrentLife");
            if (gender == FishyHelper.MALE && !hasFemale() || gender == FishyHelper.FEMALE && !hasMale()) return -1;
            if (fishCanLive(fish)) return currentLife * scale / maxLife;
            else return -1;
        } else return 0;
    }

    @Override
    public void setGUIData(int id, int value) {
        if (id == 6) tankSize = value;
        else if (id == 7) canWork = value == 1;
        else if (id == 8) isDay = value == 1;
        else super.setGUIData(id, value);
    }
    
    @Override
    public ArrayList<Integer> getGUIData() {
        ArrayList<Integer> list = super.getGUIData();
        list.add(tankSize);
        list.add(canWork ? 1 : 0);
        list.add(worldObj.isDaytime() ? 1 : 0);
        return list;
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        switch (type) {
            case NO_FOOD:
                return tank.getFluid() == null || tank.getFluid() != null && tank.getFluid().fluidID != getFluidID("fish_food");
            case NO_MALE:
                return !hasMale();
            case NO_FEMALE:
                return !hasFemale();
            case BAD_ENV:
                return (hasFemale() || hasMale()) && !canWork;
            default:
                return false;
        }
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tankSize = nbt.getInteger("TankSize");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("TankSize", tankSize);
    }
}
