package dev.tucanu.pvz.entity.client.Wallnut;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.WallnutEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WallnutRenderer extends MobRenderer<WallnutEntity, WallnutModel<WallnutEntity>>
{

    public WallnutRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WallnutModel<>(context.bakeLayer(WallnutModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(WallnutEntity entity)
    {
        return ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "textures/entity/wallnut.png");
    }

    @Override
    public void render(WallnutEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
