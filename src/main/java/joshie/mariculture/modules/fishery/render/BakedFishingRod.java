package joshie.mariculture.modules.fishery.render;

import com.google.common.base.Function;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.rod.FishingRod;
import joshie.mariculture.core.util.render.BakedMCEmpty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// the dynamic bucket is based on the empty bucket
public final class BakedFishingRod extends BakedMCEmpty implements IPerspectiveAwareModel {
    protected final ModelFishingRod parent;
    private final ImmutableMap<TransformType, TRSRTransformation> transforms;
    private final ImmutableList<BakedQuad> quads;
    private final VertexFormat format;

    public BakedFishingRod(ModelFishingRod parent, ImmutableList<BakedQuad> quads, VertexFormat format, ImmutableMap<TransformType, TRSRTransformation> transforms) {
        this.quads = quads;
        this.format = format;
        this.parent = parent;
        this.transforms = transforms;
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides() {
        return BakedFishingRodOverride.INSTANCE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        return MapWrapper.handlePerspective(this, transforms, cameraTransformType);
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side == null) return quads;
        return ImmutableList.of();
    }

    public static final class BakedFishingRodOverride extends ItemOverrideList {
        public static final BakedFishingRodOverride INSTANCE = new BakedFishingRodOverride();
        private final Function<ResourceLocation, TextureAtlasSprite> getter = (location) -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        private final Cache<FishingRod, IBakedModel> cache = CacheBuilder.newBuilder().maximumSize(256).build();

        private BakedFishingRodOverride() {
            super(ImmutableList.of());
        }

        @Override
        @SuppressWarnings("ConstantConditions")
        @Nonnull
        public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entityy) {
            FishingRod rod = MaricultureAPI.fishing.getFishingRodFromStack(stack);
            if (rod == null) {
                return originalModel;
            }

            BakedFishingRod model = (BakedFishingRod) originalModel;
            try {
                return cache.get(rod, () -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("pole", rod.getPole().getResource().toString());
                    map.put("reel", rod.getReel().getResource().toString());
                    map.put("string", rod.getString().getResource().toString());
                    map.put("hook", rod.getHook().getResource().toString());
                    map.put("cast", Boolean.toString(rod.isCast()));
                    IModel parent = model.parent.process(ImmutableMap.copyOf(map));
                    return parent.bake(ModelFishingRod.state, model.format, getter);
                });
            } catch (ExecutionException ex) { return originalModel; }
        }
    }
}
