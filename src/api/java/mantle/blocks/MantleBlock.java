package mantle.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Root class for inheriting the Minecraft Block.
 *
 * @author Sunstrike <sun@sunstrike.io>
 */
public abstract class MantleBlock extends Block
{

    public MantleBlock(Material material)
    {
        super(material);
    }

    // IDebuggable support - Uses a stick for debug purposes.
    @Override
    public void onBlockClicked (World world, int x, int y, int z, EntityPlayer player)
    {

        super.onBlockClicked(world, x, y, z, player);
    }

}
