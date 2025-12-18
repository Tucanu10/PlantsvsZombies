package dev.tucanu.pvz.entity.client.PotatoMine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.PotatoMineEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PotatoMineModel<T extends PotatoMineEntity> extends EntityModel<T> {
	// Ensure this matches your registration in ModModelLayers
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "potato_mine"), "main");
	private final ModelPart stick;
	private final ModelPart body;

	public PotatoMineModel(ModelPart root) {
		this.stick = root.getChild("stick");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stick = partdefinition.addOrReplaceChild("stick", CubeListBuilder
				.create().texOffs(8, 19).addBox(-2.5F, -18.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PotatoMineEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// If the mine is NOT primed, wobble the stick
		if (!entity.isPrimed()) {
			// Speed of the wobble (0.6 is moderate speed)
			float speed = 2F;
			// How far it wobbles in radians (0.25 is about 15 degrees)
			float intensity = 0.2F;

			// Z-axis wobble (side to side)
			this.stick.zRot = Mth.cos(ageInTicks * speed) * intensity;
			// X-axis wobble (front to back) - offset by PI/2 to make it circular if you want
			// this.stick.xRot = Mth.sin(ageInTicks * speed) * (intensity * 0.5F);
		} else {
			// Once primed, reset the stick to be perfectly straight
			this.stick.zRot = 0.0F;
			this.stick.xRot = 0.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		stick.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}