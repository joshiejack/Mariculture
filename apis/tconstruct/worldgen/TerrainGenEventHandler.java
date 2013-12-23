package tconstruct.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import tconstruct.common.TContent;
import tconstruct.util.config.PHConstruct;

public class TerrainGenEventHandler
{
    private final SurfaceOreGen ironSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 0, 12, true);
    private final SurfaceOreGen goldSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 1, 20, true);
    private final SurfaceOreGen copperSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 2, 12, true);
    private final SurfaceOreGen tinSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 3, 12, true);
    private final SurfaceOreGen aluminumSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 4, 12, true);
    private final SurfaceOreGen cobaltSurface = new SurfaceOreGen(TContent.oreGravel.blockID, 5, 30, true);

    @ForgeSubscribe
    public void onDecorateEvent (Decorate e)
    {

    }

    private void generateSurfaceOres (Random random, int xChunk, int zChunk, World world)
    {
        int xPos, yPos, zPos;
        if (PHConstruct.generateIronSurface && random.nextInt(PHConstruct.ironsRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            ironSurface.generate(world, random, xPos, yPos, zPos);
        }
        if (PHConstruct.generateGoldSurface && random.nextInt(PHConstruct.goldsRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            goldSurface.generate(world, random, xPos, yPos, zPos);
        }
        if (PHConstruct.generateCopperSurface && random.nextInt(PHConstruct.coppersRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            copperSurface.generate(world, random, xPos, yPos, zPos);
        }
        if (PHConstruct.generateTinSurface && random.nextInt(PHConstruct.tinsRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            tinSurface.generate(world, random, xPos, yPos, zPos);
        }
        if (PHConstruct.generateAluminumSurface && random.nextInt(PHConstruct.aluminumsRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            aluminumSurface.generate(world, random, xPos, yPos, zPos);
        }
        if (PHConstruct.generateCobaltSurface && random.nextInt(PHConstruct.cobaltsRarity) == 0)
        {
            xPos = xChunk + random.nextInt(16);
            yPos = 64 + PHConstruct.seaLevel;
            zPos = zChunk + random.nextInt(16);
            cobaltSurface.generate(world, random, xPos, yPos, zPos);
        }
    }
}
