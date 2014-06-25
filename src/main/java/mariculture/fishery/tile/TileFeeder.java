package mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.api.util.Text;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMachineTank;
import mariculture.core.util.Fluids;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import mariculture.fishery.Fish;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.FishyHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import cofh.api.energy.IEnergyConnection;

public class TileFeeder extends TileMachineTank implements IHasNotification, IEnergyConnection {
    private static final int fluid = 3;
    public static final int male = 5;
    public static final int female = 6;

    private ArrayList<CachedCoords> coords = new ArrayList<CachedCoords>();
    private boolean swap = false;
    private int foodTick;
    private int tankSize;

    public TileFeeder() {
        max = MachineSpeeds.getFeederSpeed();
        inventory = new ItemStack[13];
        output = new int[] { 7, 8, 9, 10, 11, 12 };
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
        if (slot == male && Fishing.fishHelper.isMale(stack)) {
            updateTankSize();
        }
    }

    //Updates the size of the tank
    public void updateTankSize() {
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
                ItemStack product = species.getProduct(Rand.rand);
                if (product != null) {
                    helper.insertStack(product, output);
                }
                int gender = Fish.gender.getDNA(fish);

                if (MaricultureHandlers.upgrades.hasUpgrade("female", this)) {
                    int fertility = Math.max(1, (5500 - Fish.fertility.getDNA(fish)) / 50);
                    if (Rand.nextInt(fertility)) {
                        generateEgg();
                    }
                }

                if (MaricultureHandlers.upgrades.hasUpgrade("male", this)) {
                    product = species.getProduct(Rand.rand);
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
            ItemStack raw = new ItemStack(Items.fish, 1, species.getID());
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

        drain(ForgeDirection.DOWN, FluidRegistry.getFluidStack(Fluids.fish_food, usage), true);
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
                int fill = fill(ForgeDirection.UP, FluidRegistry.getFluidStack(Fluids.fish_food, increase), false);
                if (fill > 0) {
                    fill(ForgeDirection.UP, FluidRegistry.getFluidStack(Fluids.fish_food, increase), true);
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
            else if (tank.getFluid() == null || tank.getFluid() != null && tank.getFluid().fluidID != FluidRegistry.getFluidID(Fluids.fish_food)) return false;

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
        tooltip.add(Text.RED + text);
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
                if (!MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) && !species.isWorldCorrect(worldObj)) {
                    noBad = addToolTip(tooltip, Text.translate("badWorld"));
                }

                int temperature = MaricultureHandlers.environment.getTemperature(worldObj, xCoord, yCoord, zCoord) + heat;
                if (temperature < species.temperature[0]) {
                    int required = species.temperature[0] - temperature;
                    noBad = addToolTip(tooltip, Text.translate("tooCold"));
                    noBad = addToolTip(tooltip, "  +" + required + Text.DEGREES);
                } else if (temperature > species.temperature[1]) {
                    int required = temperature - species.temperature[1];
                    noBad = addToolTip(tooltip, Text.translate("tooHot"));
                    noBad = addToolTip(tooltip, "  -" + required + Text.DEGREES);
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
                        noBad = addToolTip(tooltip, Text.translate("salinity.prefers") + " " + Text.translate("salinity." + salinity.toString().toLowerCase()));
                    }
                }

                int size = Fish.tankSize.getDNA(fish);
                if (tankSize < size) {
                    noBad = addToolTip(tooltip, Text.translate("notAdvanced"));
                    String text = worldObj.provider.isHellWorld ? Text.translate("blocks.lava") : Text.translate("blocks.water");
                    noBad = addToolTip(tooltip, "  +" + (size - tankSize) + " " + text);
                }

                if (!species.canWork(worldObj)) {
                    noBad = addToolTip(tooltip, Text.translate("badTime"));
                }

                if (!hasMale() || !hasFemale()) {
                    noBad = addToolTip(tooltip, Text.translate("missingMate"));
                }

                if (tank.getFluidAmount() < 1 || tank.getFluid().fluidID != Fluids.getFluid(Fluids.fish_food).getID()) {
                    noBad = addToolTip(tooltip, Text.translate("noFood"));
                }

                if (noBad) {
                    tooltip.add(Text.DARK_GREEN + currentLife + " HP");
                }
            } else if (hasMale() && hasFemale()) {
                tooltip.add(Text.DARK_GREEN + currentLife + " HP");
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
    public void getGUINetworkData(int id, int value) {
        if (id == 6) tankSize = value;
        else if (id == 7) canWork = value == 1;
        else super.getGUINetworkData(id, value);
    }

    @Override
    public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
        super.sendGUINetworkData(container, crafting);
        crafting.sendProgressBarUpdate(container, 6, tankSize);
        crafting.sendProgressBarUpdate(container, 7, canWork ? 1 : 0);
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        switch (type) {
            case NO_FOOD:
                return tank.getFluid() == null || tank.getFluid() != null && tank.getFluid().fluidID != FluidRegistry.getFluidID(Fluids.fish_food);
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
