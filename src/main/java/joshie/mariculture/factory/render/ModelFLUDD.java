package joshie.mariculture.factory.render;

import joshie.mariculture.core.lib.ArmorSlot;
import joshie.mariculture.core.render.IModelMariculture;
import joshie.mariculture.factory.items.ItemArmorFLUDD;
import joshie.mariculture.factory.tile.TileFLUDDStand;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelFLUDD extends ModelBiped implements IModelMariculture {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer Mouth;
    private ModelRenderer Handle2;
    private ModelRenderer Neck;
    private ModelRenderer NeckBump;
    private ModelRenderer NeckTop;
    private ModelRenderer Head;
    private ModelRenderer Nose;
    private ModelRenderer Base;
    private ModelRenderer Arm2;
    private ModelRenderer Shoulder2;
    private ModelRenderer Forearm2;
    private ModelRenderer Handle1;
    private ModelRenderer Forearm1;
    private ModelRenderer Shoulder1;
    private ModelRenderer Arm1;

    /** Hover pieces **/
    private ModelRenderer Top1;
    private ModelRenderer Top2;
    private ModelRenderer Head1;
    private ModelRenderer Nose1;
    private ModelRenderer Mouth1;
    private ModelRenderer Head2;
    private ModelRenderer Nose2;
    private ModelRenderer Mouth2;
    /** Turbo **/
    private ModelRenderer TurboRocketHead;
    private ModelRenderer TurboRocketNose;
    private ModelRenderer TurboMouth;
    /** Rocket **/
    private ModelRenderer RocketBody1;
    private ModelRenderer RocketBody2;
    private ModelRenderer RocketBody3;
    private ModelRenderer RocketBody4;
    private ModelRenderer RocketBody5;

    /** Upside Rocket **/
    private ModelRenderer RocketBody1Reverse;
    private ModelRenderer RocketBody2Reverse;
    private ModelRenderer RocketBody3Reverse;
    private ModelRenderer RocketBody4Reverse;
    private ModelRenderer RocketBody5Reverse;

    public ModelFLUDD() {
        super();
        textureWidth = 64;
        textureHeight = 64;

        Handle2 = new ModelRenderer(this, 24, 0);
        Handle2.addBox(-6F, 2F, -6F, 2, 2, 6);
        Handle2.setRotationPoint(0F, 0F, 0F);
        Handle2.setTextureSize(64, 64);
        Handle2.mirror = true;
        setRotation(Handle2, 0F, 0F, 0F);
        Neck = new ModelRenderer(this, 0, 10);
        Neck.addBox(-1F, -2F, -1F, 2, 2, 2);
        Neck.setRotationPoint(0F, 0F, 0F);
        Neck.setTextureSize(64, 64);
        Neck.mirror = true;
        setRotation(Neck, 0F, 0F, 0F);
        NeckBump = new ModelRenderer(this, 0, 31);
        NeckBump.addBox(-2F, -5F, -2F, 4, 3, 4);
        NeckBump.setRotationPoint(0F, 0F, 0F);
        NeckBump.setTextureSize(64, 64);
        NeckBump.mirror = true;
        setRotation(NeckBump, 0F, 0F, 0F);
        NeckTop = new ModelRenderer(this, 0, 40);
        NeckTop.addBox(-1F, -7F, -1F, 2, 2, 2);
        NeckTop.setRotationPoint(0F, 0F, 0F);
        NeckTop.setTextureSize(64, 64);
        NeckTop.mirror = true;
        setRotation(NeckTop, 0F, 0F, 0F);
        /** Squirt Head **/
        Head = new ModelRenderer(this, 0, 47);
        Head.addBox(-2F, -10F, -2F, 4, 3, 4);
        Head.setRotationPoint(0F, 0F, 0F);
        Head.setTextureSize(64, 64);
        Head.mirror = true;
        setRotation(Head, 0F, 0F, 0F);
        Nose = new ModelRenderer(this, 0, 62);
        Nose.addBox(-1F, -9F, -3F, 2, 1, 1);
        Nose.setRotationPoint(0F, 0F, 0F);
        Nose.setTextureSize(64, 64);
        Nose.mirror = true;
        setRotation(Nose, 0F, 0F, 0F);
        Mouth = new ModelRenderer(this, 46, 0);
        Mouth.addBox(-3F, -11F, -4F, 6, 5, 1);
        Mouth.setRotationPoint(0F, 0F, 0F);
        Mouth.setTextureSize(64, 64);
        Mouth.mirror = true;
        setRotation(Mouth, 0F, 0F, 0F);
        /** End Squirt Head **/
        Base = new ModelRenderer(this, 0, 16);
        Base.addBox(-3F, 0F, -3F, 6, 6, 6);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Arm2 = new ModelRenderer(this, 0, 0);
        Arm2.addBox(-4F, -2F, -1F, 3, 1, 2);
        Arm2.setRotationPoint(0F, 0F, 0F);
        Arm2.setTextureSize(64, 64);
        Arm2.mirror = true;
        setRotation(Arm2, 0F, 0F, 0F);
        Shoulder2 = new ModelRenderer(this, 13, 6);
        Shoulder2.addBox(-5F, -3F, -2F, 1, 3, 4);
        Shoulder2.setRotationPoint(0F, 0F, 0F);
        Shoulder2.setTextureSize(64, 64);
        Shoulder2.mirror = true;
        setRotation(Shoulder2, 0F, 0F, 0F);
        Forearm2 = new ModelRenderer(this, 17, 0);
        Forearm2.addBox(-5F, 0F, -1F, 1, 2, 2);
        Forearm2.setRotationPoint(0F, 0F, 0F);
        Forearm2.setTextureSize(64, 64);
        Forearm2.mirror = true;
        setRotation(Forearm2, 0F, 0F, 0F);
        Handle1 = new ModelRenderer(this, 24, 0);
        Handle1.addBox(4F, 2F, -6F, 2, 2, 6);
        Handle1.setRotationPoint(0F, 0F, 0F);
        Handle1.setTextureSize(64, 64);
        Handle1.mirror = true;
        setRotation(Handle1, 0F, 0F, 0F);
        Forearm1 = new ModelRenderer(this, 17, 0);
        Forearm1.addBox(4F, 0F, -1F, 1, 2, 2);
        Forearm1.setRotationPoint(0F, 0F, 0F);
        Forearm1.setTextureSize(64, 64);
        Forearm1.mirror = true;
        setRotation(Forearm1, 0F, 0F, 0F);
        Shoulder1 = new ModelRenderer(this, 13, 6);
        Shoulder1.addBox(4F, -3F, -2F, 1, 3, 4);
        Shoulder1.setRotationPoint(0F, 0F, 0F);
        Shoulder1.setTextureSize(64, 64);
        Shoulder1.mirror = true;
        setRotation(Shoulder1, 0F, 0F, 0F);
        Arm1 = new ModelRenderer(this, 0, 0);
        Arm1.addBox(1F, -2F, -1F, 3, 1, 2);
        Arm1.setRotationPoint(0F, 0F, 0F);
        Arm1.setTextureSize(64, 64);
        Arm1.mirror = true;
        setRotation(Arm1, 0F, 0F, 0F);
        /** Hover Pieces **/
        Top1 = new ModelRenderer(this, 32, 10);
        Top1.addBox(2F, -8F, -1F, 5, 1, 2);
        Top1.setRotationPoint(0F, 1F, 0F);
        Top1.setTextureSize(64, 64);
        Top1.mirror = true;
        setRotation(Top1, 0F, 0F, -0.1745329F);
        Top2 = new ModelRenderer(this, 32, 10);
        Top2.addBox(-7F, -8F, -1F, 5, 1, 2);
        Top2.setRotationPoint(0F, 1F, 0F);
        Top2.setTextureSize(64, 64);
        Top2.mirror = true;
        setRotation(Top2, 0F, 0F, 0.1745329F);
        Head1 = new ModelRenderer(this, 0, 47);
        Head1.addBox(-2F, -10F, 3F, 4, 3, 4);
        Head1.setRotationPoint(0F, 0F, 0F);
        Head1.setTextureSize(64, 64);
        Head1.mirror = true;
        setRotation(Head1, 1.308997F, -1.570796F, 0F);
        Nose1 = new ModelRenderer(this, 0, 62);
        Nose1.addBox(-1F, -9F, 2F, 2, 1, 1);
        Nose1.setRotationPoint(0F, 0F, 0F);
        Nose1.setTextureSize(64, 64);
        Nose1.mirror = true;
        setRotation(Nose1, 1.308997F, -1.570796F, 0F);
        Mouth1 = new ModelRenderer(this, 46, 0);
        Mouth1.addBox(-3F, -11F, 1F, 6, 5, 1);
        Mouth1.setRotationPoint(0F, 0F, 0F);
        Mouth1.setTextureSize(64, 64);
        Mouth1.mirror = true;
        setRotation(Mouth1, 1.308997F, -1.570796F, 0F);
        Head2 = new ModelRenderer(this, 0, 47);
        Head2.addBox(-2F, -10F, 3F, 4, 3, 4);
        Head2.setRotationPoint(0F, 0F, 0F);
        Head2.setTextureSize(64, 64);
        Head2.mirror = true;
        setRotation(Head2, 1.308997F, 1.570796F, 0F);
        Nose2 = new ModelRenderer(this, 0, 62);
        Nose2.addBox(-1F, -9F, 2F, 2, 1, 1);
        Nose2.setRotationPoint(0F, 0F, 0F);
        Nose2.setTextureSize(64, 64);
        Nose2.mirror = true;
        setRotation(Nose2, 1.308997F, 1.570796F, 0F);
        Mouth2 = new ModelRenderer(this, 46, 0);
        Mouth2.addBox(-3F, -11F, 1F, 6, 5, 1);
        Mouth2.setRotationPoint(0F, 0F, 0F);
        Mouth2.setTextureSize(64, 64);
        Mouth2.mirror = true;
        setRotation(Mouth2, 1.308997F, 1.570796F, 0F);
        /** End Hover Pieces **/
        /** Turbo **/
        TurboMouth = new ModelRenderer(this, 45, 18);
        TurboMouth.addBox(-4F, -12F, 3F, 8, 7, 1);
        TurboMouth.setRotationPoint(0F, 0F, 0F);
        TurboMouth.setTextureSize(64, 64);
        TurboMouth.mirror = true;
        setRotation(TurboMouth, 0F, 0F, 0F);
        TurboRocketHead = new ModelRenderer(this, 16, 47);
        TurboRocketHead.addBox(-2F, -10F, -2F, 4, 3, 4);
        TurboRocketHead.setRotationPoint(0F, 0F, 0F);
        TurboRocketHead.setTextureSize(64, 64);
        TurboRocketHead.mirror = true;
        setRotation(TurboRocketHead, 0F, 3.141593F, 0F);
        TurboRocketNose = new ModelRenderer(this, 0, 62);
        TurboRocketNose.addBox(-1F, -9F, 2F, 2, 1, 1);
        TurboRocketNose.setRotationPoint(0F, 0F, 0F);
        TurboRocketNose.setTextureSize(64, 64);
        TurboRocketNose.mirror = true;
        setRotation(TurboRocketNose, 0F, 0F, 0F);
        /** Rocket **/
        RocketBody1 = new ModelRenderer(this, 27, 28);
        RocketBody1.addBox(-3F, -12F, 3F, 6, 7, 6);
        RocketBody1.setRotationPoint(0F, 0F, 0F);
        RocketBody1.setTextureSize(64, 64);
        RocketBody1.mirror = true;
        setRotation(RocketBody1, 0F, 0F, 0F);
        RocketBody2 = new ModelRenderer(this, 27, 58);
        RocketBody2.addBox(-2F, -14F, 4F, 4, 2, 4);
        RocketBody2.setRotationPoint(0F, 0F, 0F);
        RocketBody2.setTextureSize(64, 64);
        RocketBody2.mirror = true;
        setRotation(RocketBody2, 0F, 0F, 0F);
        RocketBody3 = new ModelRenderer(this, 34, 49);
        RocketBody3.addBox(-1F, -14F, 5F, 2, 2, 2);
        RocketBody3.setRotationPoint(0F, -2F, 0F);
        RocketBody3.setTextureSize(64, 64);
        RocketBody3.mirror = true;
        setRotation(RocketBody3, 0F, 0F, 0F);
        RocketBody4 = new ModelRenderer(this, 54, 41);
        RocketBody4.addBox(-1F, -3F, 5F, 2, 2, 2);
        RocketBody4.setRotationPoint(0F, -2F, 0F);
        RocketBody4.setTextureSize(64, 64);
        RocketBody4.mirror = true;
        setRotation(RocketBody4, 0F, 0F, 0F);
        RocketBody5 = new ModelRenderer(this, 40, 49);
        RocketBody5.addBox(-3F, -3F, 3F, 6, 1, 6);
        RocketBody5.setRotationPoint(0F, 0F, 0F);
        RocketBody5.setTextureSize(64, 64);
        RocketBody5.mirror = true;
        setRotation(RocketBody5, 0F, 0F, 0F);
        /* Reverse Rocket Body * */
        RocketBody1Reverse = new ModelRenderer(this, 27, 28);
        RocketBody1Reverse.addBox(-3F, 5F, 3F, 6, 7, 6);
        RocketBody1Reverse.setRotationPoint(0F, 0F, 0F);
        RocketBody1Reverse.setTextureSize(64, 64);
        RocketBody1Reverse.mirror = true;
        setRotation(RocketBody1Reverse, 0F, 0F, 3.141593F);
        RocketBody2Reverse = new ModelRenderer(this, 27, 58);
        RocketBody2Reverse.addBox(-2F, 3F, 4F, 4, 2, 4);
        RocketBody2Reverse.setRotationPoint(0F, 0F, 0F);
        RocketBody2Reverse.setTextureSize(64, 64);
        RocketBody2Reverse.mirror = true;
        setRotation(RocketBody2Reverse, 0F, 0F, 3.141593F);
        RocketBody3Reverse = new ModelRenderer(this, 34, 49);
        RocketBody3Reverse.addBox(-1F, 3F, 5F, 2, 2, 2);
        RocketBody3Reverse.setRotationPoint(0F, 2F, 0F);
        RocketBody3Reverse.setTextureSize(64, 64);
        RocketBody3Reverse.mirror = true;
        setRotation(RocketBody3Reverse, 0F, 0F, 3.141593F);
        RocketBody4Reverse = new ModelRenderer(this, 54, 41);
        RocketBody4Reverse.addBox(-1F, 10F, 5F, 2, 2, 2);
        RocketBody4Reverse.setRotationPoint(0F, -2F, 0F);
        RocketBody4Reverse.setTextureSize(64, 64);
        RocketBody4Reverse.mirror = true;
        setRotation(RocketBody4Reverse, 0F, 0F, 3.141593F);
        RocketBody5Reverse = new ModelRenderer(this, 40, 49);
        RocketBody5Reverse.addBox(-3F, 14F, 3F, 6, 1, 6);
        RocketBody5Reverse.setRotationPoint(0F, 0F, 0F);
        RocketBody5Reverse.setTextureSize(64, 64);
        RocketBody5Reverse.mirror = true;
        setRotation(RocketBody5Reverse, 0F, 0F, 3.141593F);
    }

    @Override
    public void render(TileEntity tile, double x, double y, double z) {
        render((TileFLUDDStand) tile, x, y, z);
    }

    public void render(TileFLUDDStand tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x + 0.5F, y + 0.34F, z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glScalef(1.2F, 1.2F, 1.2F);

        if (tile.orientation == ForgeDirection.EAST) {
            GL11.glRotatef(90, 0F, 1F, 0F);
        } else if (tile.orientation == ForgeDirection.SOUTH) {
            GL11.glRotatef(180, 0F, 1F, 0F);
        } else if (tile.orientation == ForgeDirection.WEST) {
            GL11.glRotatef(270, 0F, 1F, 0F);
        }

        if (tile.orientation == ForgeDirection.UP) {
            TurboRocketHead.render(scale);
            TurboRocketNose.render(scale);
            RocketBody1Reverse.render(scale);
            RocketBody2Reverse.render(scale);
            RocketBody3Reverse.render(scale);
            RocketBody4Reverse.render(scale);
            RocketBody5Reverse.render(scale);
        } else if (tile.orientation == ForgeDirection.DOWN) {
            TurboRocketHead.render(scale);
            TurboRocketNose.render(scale);
            RocketBody1.render(scale);
            RocketBody2.render(scale);
            RocketBody3.render(scale);
            RocketBody4.render(scale);
            RocketBody5.render(scale);
        } else {
            Mouth.render(scale);
            Head.render(scale);
            Nose.render(scale);
        }

        Handle2.render(scale);
        Neck.render(scale);
        NeckBump.render(scale);
        NeckTop.render(scale);
        Base.render(scale);
        Arm2.render(scale);
        Shoulder2.render(scale);
        Forearm2.render(scale);
        Handle1.render(scale);
        Forearm1.render(scale);
        Shoulder1.render(scale);
        Arm1.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.inventory.armorInventory[ArmorSlot.TOP] != null) if (player.inventory.armorInventory[ArmorSlot.TOP].hasTagCompound()) {
                int mode = player.inventory.armorInventory[ArmorSlot.TOP].stackTagCompound.getInteger("mode");

                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                GL11.glTranslatef(0F, 0.1F, 0.2F);

                if (mode == ItemArmorFLUDD.HOVER) {
                    Top1.render(scale);
                    Top2.render(scale);
                    Head1.render(scale);
                    Head2.render(scale);
                    Nose1.render(scale);
                    Nose2.render(scale);
                    Mouth1.render(scale);
                    Mouth2.render(scale);

                } else if (mode == ItemArmorFLUDD.ROCKET) {
                    TurboRocketHead.render(scale);
                    TurboRocketNose.render(scale);
                    RocketBody1.render(scale);
                    RocketBody2.render(scale);
                    RocketBody3.render(scale);
                    RocketBody4.render(scale);
                    RocketBody5.render(scale);
                } else if (mode == ItemArmorFLUDD.TURBO) {
                    TurboRocketHead.render(scale);
                    TurboRocketNose.render(scale);
                    TurboMouth.render(scale);
                } else if (mode == ItemArmorFLUDD.SQUIRT) {
                    Mouth.render(scale);
                    Head.render(scale);
                    Nose.render(scale);
                }

                Handle2.render(scale);
                Neck.render(scale);
                NeckBump.render(scale);
                NeckTop.render(scale);
                Base.render(scale);
                Arm2.render(scale);
                Shoulder2.render(scale);
                Forearm2.render(scale);
                Handle1.render(scale);
                Forearm1.render(scale);
                Shoulder1.render(scale);
                Arm1.render(scale);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderInventory(ItemRenderType type) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        switch (type) {
            case INVENTORY:
                GL11.glScalef(16F, 16F, 16F);
                GL11.glTranslatef(0.5F, 0.6F, 0F);
                break;
            case ENTITY:
                GL11.glRotatef(180, 0, 0, 1);
                break;
            default:
                GL11.glRotatef(35, 1, 0, 0);
                GL11.glRotatef(45, 0, -5, 0);
                GL11.glRotatef(180, 1, 0, 0);
                break;
        }
        Mouth.render(scale);
        Head.render(scale);
        Nose.render(scale);
        Handle2.render(scale);
        Neck.render(scale);
        NeckBump.render(scale);
        NeckTop.render(scale);
        Base.render(scale);
        Arm2.render(scale);
        Shoulder2.render(scale);
        Forearm2.render(scale);
        Handle1.render(scale);
        Forearm1.render(scale);
        Shoulder1.render(scale);
        Arm1.render(scale);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
