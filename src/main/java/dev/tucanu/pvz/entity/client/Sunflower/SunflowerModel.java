package dev.tucanu.pvz.entity.client.Sunflower;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.tucanu.pvz.PlantsvsZombies;
import dev.tucanu.pvz.entity.custom.PlantEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SunflowerModel<T extends PlantEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(PlantsvsZombies.MODID, "sunflower"), "main");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public SunflowerModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 0).addBox(-0.9191F, -6.1667F, -1.0404F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0809F, 21.1667F, 0.0404F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-8.5F, -3.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0809F, 1.8333F, -3.0404F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 24).addBox(-5.5F, -3.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0809F, 1.8333F, 0.9596F, 0.0F, -0.7854F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.7675F, -0.0946F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-6.0F, -5.5F, 0.0F, 12.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.2466F, -0.0845F, 2.7489F, 0.0F, 3.1416F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2294F, -0.0967F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);

		// Fixed: Use the entity's specific AnimationState
		this.animate(entity.generatingSunAnimationState, SunflowerAnimation.SUNFLOWER_GENERATE_SUN, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		// FIXED: We pass 15728880 (Max Light) instead of 'packedLight'.
		// This forces the "plane" parts to be bright and ignores shadows.
		root.render(poseStack, vertexConsumer, 15728880, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}