/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package joshie.mariculture.modules.fishery.render;


import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Vector3f;
import java.util.Collection;

public final class ModelFishingRod implements IModel, IModelCustomData {
    static final SimpleModelState state = getModelState();
    static final ModelFishingRod MODEL = new ModelFishingRod();

    private final ResourceLocation poleLocation;
    private final ResourceLocation reelLocation;
    private final ResourceLocation stringLocation;
    private final ResourceLocation hookLocation;

    private ModelFishingRod() {
        this.poleLocation = null;
        this.reelLocation = null;
        this.stringLocation = null;
        this.hookLocation = null;
    }

    private ModelFishingRod(ResourceLocation poleLocation, ResourceLocation reelLocation, ResourceLocation stringLocation, ResourceLocation hookLocation, boolean cast) {
        this.poleLocation = new ResourceLocation("mariculture", "items/fishing/pole_" + poleLocation.getResourcePath());
        this.reelLocation = new ResourceLocation("mariculture", "items/fishing/reel_" + reelLocation.getResourcePath());
        if (cast) {
            this.stringLocation = new ResourceLocation("mariculture", "items/fishing/string_" + stringLocation.getResourcePath() + "_cast");
            this.hookLocation = null;
        } else {
            this.stringLocation = new ResourceLocation("mariculture", "items/fishing/string_" + stringLocation.getResourcePath() + "_uncast");
            this.hookLocation = new ResourceLocation("mariculture", "items/fishing/hook_" + hookLocation.getResourcePath());
        }
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
        if (poleLocation != null)
            builder.add(poleLocation);
        if (reelLocation != null)
            builder.add(reelLocation);
        if (stringLocation != null)
            builder.add(stringLocation);
        if (hookLocation != null)
            builder.add(hookLocation);
        return builder.build();
    }

    private void addResourceToBuilder(ImmutableList.Builder<BakedQuad> builder, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, ResourceLocation... resources) {
        for (ResourceLocation resource: resources) {
            if (resource != null) {
                IBakedModel model = (new ItemLayerModel(ImmutableList.of(resource))).bake(state, format, bakedTextureGetter);
                builder.addAll(model.getQuads(null, null, 0));
            }
        }
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap<TransformType, TRSRTransformation> transformMap = IPerspectiveAwareModel.MapWrapper.getTransforms(state);
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        addResourceToBuilder(builder, format, bakedTextureGetter, poleLocation, reelLocation, stringLocation, hookLocation);
        return new BakedFishingRod(this, builder.build(), format, transformMap);
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

    @Override
    public ModelFishingRod process(ImmutableMap<String, String> customData) {
        ResourceLocation poleLocation = new ResourceLocation(customData.get("pole"));
        ResourceLocation reelLocation = new ResourceLocation(customData.get("reel"));
        ResourceLocation stringLocation = new ResourceLocation(customData.get("string"));
        ResourceLocation hookLocation = new ResourceLocation(customData.get("hook"));
        boolean isCast = customData.get("cast").equals("true");
        return new ModelFishingRod(poleLocation, reelLocation, stringLocation, hookLocation, isCast);
    }

    private static SimpleModelState getModelState() {
        ImmutableMap.Builder<TransformType, TRSRTransformation> builder = ImmutableMap.builder();
        builder.put(TransformType.GROUND, getTransformation(0, 2, 0, 0, 0, 0, 0.5f));
        builder.put(TransformType.HEAD, getTransformation(0, 13, 7, 0, 180, 0, 1));
        builder.put(TransformType.FIRST_PERSON_RIGHT_HAND, getTransformation(0F, 1.6F, 0.8F, 0F, 90F, 25F, 0.68F));
        builder.put(TransformType.FIRST_PERSON_LEFT_HAND, getTransformation(0F, 1.6F, 0.8F, 0F, -90F, -25F, 0.68F));
        builder.put(TransformType.THIRD_PERSON_RIGHT_HAND, getTransformation(0F, 4F, 2.5F, 0F, 90F, 55F, 0.85F));
        builder.put(TransformType.THIRD_PERSON_LEFT_HAND, getTransformation(0F, 4F, 2.5F, 0F, -90F, -55F, 0.85F));
        return new SimpleModelState(builder.build());
    }

    private static TRSRTransformation getTransformation(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(tx / 16, ty / 16, tz / 16), TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null));
    }
}