package dev.tucanu.pvz.entity.client.CherryBomb;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.CherryBombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CherryBombRenderer extends MobRenderer<CherryBombEntity, CherryBombModel<CherryBombEntity>>
{
    public CherryBombRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CherryBombModel<>(context.bakeLayer(CherryBombModel.LAYER_LOCATION)),
                0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CherryBombEntity cherryBombEntity)
    {
        return ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "textures/entity/cherry_bomb.png");
    }

    @Override
    public void render (CherryBombEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
