package dev.tucanu.pvz.entity.client.Sunflower;

import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.SunflowerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SunflowerRenderer extends MobRenderer<SunflowerEntity, SunflowerModel<SunflowerEntity>> {
    public SunflowerRenderer(EntityRendererProvider.Context context) {
        super(context, new SunflowerModel<>(context.bakeLayer(SunflowerModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SunflowerEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "textures/entity/sunflower.png");
    }
}