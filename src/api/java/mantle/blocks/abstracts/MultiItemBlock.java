package mantle.blocks.abstracts;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * 
 * @author progwml6
 * base class for itemBlocks with different unlocalized names based on metadata
 */

public class MultiItemBlock extends ItemBlock
{
    private String blockType[];
    private String unlocalizedName;
    private String append;
    private int specialIndex[] = { -1, -1 };

    public MultiItemBlock(Block b, String itemBlockUnlocalizedName, String[] blockTypes)
    {
        super(b);
        if (itemBlockUnlocalizedName.isEmpty())
            this.unlocalizedName = super.getUnlocalizedName();
        else
            this.unlocalizedName = itemBlockUnlocalizedName;
        this.blockType = blockTypes;
        this.append = "";
    }

    public MultiItemBlock(Block b, String itemBlockUnlocalizedName, String appendToEnd, String[] blockTypes)
    {
        super(b);
        this.unlocalizedName = itemBlockUnlocalizedName;
        this.blockType = blockTypes;
        this.append = "." + appendToEnd;
    }

    public void setSpecialIndex (int clampIndex, int stringBuilderIndex)
    {
        specialIndex[0] = clampIndex;
        specialIndex[1] = stringBuilderIndex;
    }

    public int getMetadata (int meta)
    {
        return meta;
    }


}
