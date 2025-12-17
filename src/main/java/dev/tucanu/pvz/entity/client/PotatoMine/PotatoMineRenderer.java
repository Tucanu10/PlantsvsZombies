package dev.tucanu.pvz.entity.client.PotatoMine;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.PotatoMineEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PotatoMineRenderer extends MobRenderer<PotatoMineEntity, PotatoMineModel<PotatoMineEntity>>
{
    public PotatoMineRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PotatoMineModel<>(context.bakeLayer(PotatoMineModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PotatoMineEntity entity)
    {
        return ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "textures/entity/potato_mine.png");
    }

    @Override
    public void render(PotatoMineEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
