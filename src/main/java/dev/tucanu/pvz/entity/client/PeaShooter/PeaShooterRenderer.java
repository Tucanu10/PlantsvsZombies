package dev.tucanu.pvz.entity.client.PeaShooter;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.PeaShooterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PeaShooterRenderer extends MobRenderer<PeaShooterEntity, PeaShooterModel<PeaShooterEntity>>
{
    public PeaShooterRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PeaShooterModel<>(context.bakeLayer(PeaShooterModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PeaShooterEntity entity)
    {
        return ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "textures/entity/pea_shooter.png");
    }

    @Override
    public void render(PeaShooterEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }


}
