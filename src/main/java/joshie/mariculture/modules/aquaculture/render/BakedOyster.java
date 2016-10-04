package joshie.mariculture.modules.aquaculture.render;

import joshie.mariculture.core.util.render.BakedMCParent;
import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.modules.aquaculture.block.BlockOyster;
import joshie.mariculture.modules.aquaculture.block.BlockOyster.Oyster;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

public class BakedOyster extends BakedMCParent {
    public BakedOyster(IBakedModel oyster) {
        super(oyster); //Master Oyster
    }

    protected List<BakedQuad> addQuadsAndReplaceTexture(List<BakedQuad> quads, IBakedModel model, @Nullable IBlockState state, @Nullable EnumFacing side, long rand, TextureAtlasSprite sprite) {
        List<BakedQuad> ret = model.getQuads(state, side, rand);
        if (ret.size() >= 1) {
            ret.stream().map(quad -> new BakedQuadRetextured(quad, sprite)).forEachOrdered(quads::add);
        }

        return quads;
    }

    protected TextureAtlasSprite getSpriteFromState(IBlockState state) {
        String texture = "minecraft:blocks/sand";
        if(state instanceof IExtendedBlockState) texture = ((IExtendedBlockState)state).getValue(BlockOyster.TEXTURE);
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture);
    }

    public static class Empty extends BakedOyster {
        public Empty(IBakedModel oyster) {
            super(oyster);
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return new ArrayList<>(super.getQuads(state, side, rand));
        }
    }

    public static class Sand extends Empty {
        private final IBakedModel sand;

        public Sand(IBakedModel oyster, IBakedModel sand) {
            super(oyster);
            this.sand = sand;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            List<BakedQuad> oysterQuads = super.getQuads(state, side, rand);
            return addQuadsAndReplaceTexture(oysterQuads, sand, state, side, rand, getSpriteFromState(state));
        }
    }

    public static class Pearl extends Empty {
        private final IBakedModel pearl;

        public Pearl(IBakedModel oyster, IBakedModel pearl) {
            super(oyster);
            this.pearl = pearl;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            List<BakedQuad> oysterQuads = super.getQuads(state, side, rand);
            return addQuadsAndReplaceTexture(oysterQuads, pearl, state, side, rand, getSpriteFromState(state));
        }
    }

    @MCEvents(Side.CLIENT)
    public static class Register { /** Register the oyster model **/
        private static final ResourceLocation OYSTER = new ResourceLocation(MODID, "oyster");

        @SubscribeEvent
        public void onBaking(ModelBakeEvent event) {
            IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
            for (EnumFacing facing: EnumFacing.HORIZONTALS) {
                try {
                    ModelResourceLocation emptyLocation = getModelLocation(OYSTER, facing, Oyster.EMPTY);
                    ModelResourceLocation sandLocation = getModelLocation(OYSTER, facing, Oyster.SAND);
                    ModelResourceLocation pearlLocation = getModelLocation(OYSTER, facing, Oyster.PEARL);

                    IBakedModel empty = registry.getObject(emptyLocation);
                    IBakedModel sand = registry.getObject(sandLocation);
                    IBakedModel pearl = registry.getObject(pearlLocation);

                    registry.putObject(emptyLocation, new BakedOyster.Empty(empty));
                    registry.putObject(sandLocation,  new BakedOyster.Sand(empty, sand));
                    registry.putObject(pearlLocation, new BakedOyster.Pearl(empty, pearl));
                } catch (Exception e) { }
            }
        }

        private ModelResourceLocation getModelLocation(ResourceLocation resource, EnumFacing facing, Oyster oyster) {
            return new ModelResourceLocation(resource, String.format("facing=%s,oyster=%s", facing.getName(), oyster.getName()));
        }
    }
}
