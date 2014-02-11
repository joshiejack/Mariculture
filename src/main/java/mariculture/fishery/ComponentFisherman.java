package mariculture.fishery;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentFisherman extends ComponentVillage
{
    private int averageGroundLevel = -1;
    
    public ComponentFisherman() {}

    public ComponentFisherman(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }

    public static ComponentFisherman buildComponent (ComponentVillageStartPiece villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 7, 6, 7, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentFisherman(villagePiece, p5, random,
                structureboundingbox, p4) : null;
    }

    public boolean addComponentParts (World world, Random random, StructureBoundingBox sbb)
    {
        if (this.averageGroundLevel < 0)
        {
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);

            if (this.averageGroundLevel < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 4, 0);
        }

        /**
         * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
         * maxZ, int placeBlockId, int replaceBlockId, boolean alwaysreplace)
         */
        
        this.placeBlockAtCurrentPosition(world, Blocks.stairsCobblestone.blockID, this.getMetadataWithOffset(Blocks.stairsCobblestone.blockID, 0), 2, 1, 0, sbb);
        this.fillWithBlocks(world, sbb, 1, 1, 1, 4, 1, 1, Blocks.cobblestone.blockID, Blocks.cobblestone.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 2, 1, 1, 4, 1, Blocks.cobblestone.blockID, Blocks.cobblestone.blockID, false);
        this.placeDoorAtCurrentPosition(world, sbb, random, 2, 2, 1, this.getMetadataWithOffset(Blocks.doorWood.blockID, 1));
        this.placeBlockAtCurrentPosition(world, Blocks.wood.blockID, 0, 2, 4, 1, sbb);
        this.fillWithBlocks(world, sbb, 3, 2, 1, 3, 4, 1, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 4, 2, 1, 4, 4, 1, Blocks.cobblestone.blockID, Blocks.cobblestone.blockID, false);

        
        /*
        this.fillWithBlocks(world, sbb, 0, 0, 0, 6, 0, 6, Blocks.cobblestone.blockID, Blocks.cobblestone.blockID, false); //Base
        this.fillWithBlocks(world, sbb, 0, 5, 0, 6, 5, 6, Blocks.fence.blockID, Blocks.fence.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 0, 1, 5, 0, 5, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 2, 0, 2, 4, 0, 4, Blocks.cloth.blockID, Blocks.cloth.blockID, false);

        //this.fillWithBlocks(world, sbb, 0, 5, 0, 6, 5, 6, Blocks.wood.blockID, Blocks.wood.blockID, false);

        this.fillWithBlocks(world, sbb, 0, 1, 0, 0, 4, 0, Blocks.wood.blockID, Blocks.wood.blockID, false); //Edges
        this.fillWithBlocks(world, sbb, 0, 1, 6, 0, 4, 6, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 6, 1, 0, 6, 4, 0, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 6, 1, 6, 6, 4, 6, Blocks.wood.blockID, Blocks.wood.blockID, false);

        this.fillWithBlocks(world, sbb, 0, 1, 1, 0, 1, 5, Blocks.planks.blockID, Blocks.planks.blockID, false); //Walls
        this.fillWithBlocks(world, sbb, 1, 1, 0, 5, 1, 0, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 6, 1, 1, 6, 1, 5, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 1, 6, 5, 1, 6, Blocks.planks.blockID, Blocks.planks.blockID, false);

        this.fillWithBlocks(world, sbb, 0, 3, 1, 0, 3, 5, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 3, 0, 5, 3, 0, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 6, 3, 1, 6, 3, 5, Blocks.planks.blockID, Blocks.planks.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 3, 6, 5, 3, 6, Blocks.planks.blockID, Blocks.planks.blockID, false);

        this.fillWithBlocks(world, sbb, 0, 4, 1, 0, 4, 5, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 4, 0, 5, 4, 0, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 6, 4, 1, 6, 4, 5, Blocks.wood.blockID, Blocks.wood.blockID, false);
        this.fillWithBlocks(world, sbb, 1, 4, 6, 5, 4, 6, Blocks.wood.blockID, Blocks.wood.blockID, false);

        this.fillWithBlocks(world, sbb, 1, 1, 1, 5, 5, 5, 0, 0, false);
        this.fillWithBlocks(world, sbb, 1, 4, 1, 5, 4, 5, Blocks.planks.blockID, Blocks.planks.blockID, false);

        //world, blockID, metadata, x, y, z, bounds
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 1, 2, 0, sbb);//Glass and door
        this.placeBlockAtCurrentPosition(world, Blocks.planks.blockID, 0, 2, 2, 0, sbb);
        this.placeDoorAtCurrentPosition(world, sbb, random, 3, 1, 0, this.getMetadataWithOffset(Blocks.doorWood.blockID, 1));
        this.placeBlockAtCurrentPosition(world, Blocks.planks.blockID, 0, 4, 2, 0, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 5, 2, 0, sbb);

        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 1, 2, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 2, 2, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks.blockID, 0, 3, 2, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 4, 2, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 5, 2, 6, sbb);

        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 0, 2, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 0, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks.blockID, 0, 0, 2, 3, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 0, 2, 4, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 0, 2, 5, sbb);

        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 6, 2, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 6, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks.blockID, 0, 6, 2, 3, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 6, 2, 4, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.thinGlass.blockID, 0, 6, 2, 5, sbb);

        int i = this.getMetadataWithOffset(Blocks.ladder.blockID, 3); //Ladders
        this.placeBlockAtCurrentPosition(world, Blocks.ladder.blockID, i, 3, 1, 5, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.ladder.blockID, i, 3, 2, 5, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.ladder.blockID, i, 3, 3, 5, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.ladder.blockID, i, 3, 4, 5, sbb);

        //this.placeBlockAtCurrentPosition(world, TContent.toolStationWood.blockID, 0, 1, 1, 1, sbb); //Inside
        //this.generateStructurePatternChestContents(world, sbb, random, 1, 1, 2, TContent.tinkerHousePatterns.getItems(random), TContent.tinkerHousePatterns.getCount(random));
        //this.placeBlockAtCurrentPosition(world, TContent.toolStationWood.blockID, 5, 1, 1, 2, sbb);
        //this.placeBlockAtCurrentPosition(world, TContent.toolStationWood.blockID, 1, 1, 1, 3, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.workbench.blockID, 0, 1, 1, 4, sbb);
       // this.placeBlockAtCurrentPosition(world, TContent.toolStationWood.blockID, 10, 1, 1, 5, sbb);

        //ChestGenHooks info = ChestGenHooks.getInfo("TinkerHouse");

       // this.generateStructureChestContents(world, sbb, random, 4, 1, 5, TContent.tinkerHouseChest.getItems(random), TContent.tinkerHouseChest.getCount(random));
        //this.placeBlockAtCurrentPosition(world, Blocks.chest.blockID, i, 4, 1, 5, sbb);
        i = this.getMetadataWithOffset(Blocks.pistonBase.blockID, 3);
        this.placeBlockAtCurrentPosition(world, Blocks.pistonBase.blockID, i, 5, 1, 5, sbb);

        for (int l = 0; l < 6; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
                this.fillCurrentPositionBlocksDownwards(world, Blocks.cobblestone.blockID, 0, i1, -1, l, sbb);
            }
        }
        
        */
        
        this.spawnVillagers(world, sbb, 3, 1, 3, 1);

        return true;
    }

    protected boolean generateStructurePatternChestContents (World world, StructureBoundingBox par2StructureBoundingBox, Random random, int x, int y, int z, WeightedRandomChestContent[] content,
            int par8)
    {
        int posX = this.getXWithOffset(x, z);
        int posY = this.getYWithOffset(y);
        int posZ = this.getZWithOffset(x, z);

        if (par2StructureBoundingBox.isVecInside(posX, posY, posZ) && world.getBlockId(posX, posY, posZ) != Blocks.chest.blockID)
        {
           // world.setBlock(posX, posY, posZ, TContent.toolStationWood.blockID, 5, 2);
            //PatternChestLogic logic = (PatternChestLogic) world.getTileEntity(posX, posY, posZ);

           /* if (logic != null)
            {
                WeightedRandomChestContent.generateChestContents(random, content, logic, par8);
            } */

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int getVillagerType (int par1)
    {
        return 78943;
    }
}